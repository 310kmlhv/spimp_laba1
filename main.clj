(ns main
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io]))


(defn load-json [filename]
  (try
    (with-open [reader (io/reader filename)] 
      (json/read reader :key-fn keyword))
    (catch Exception e
      (println "ошибка чтения файла")
      nil)))

;; достаю ключи с первой строки
(defn get-keys [data]
  (keys (first data)))

;; нумерую поля
(defn print-keys [keys-list]
  (doseq [[idx k] (map-indexed vector keys-list)]
    (println (str (+ idx 1) " - " (name k)))))

;; сортировка
(defn sort-data [data key order]
  (let [sorted (sort-by key data)]
    (if (= order "desc")
      (reverse sorted)
      sorted)))

;; вывод
(defn print-data [data]
  (doseq [item data]
    (println item)))

;; main
(defn run []
  (println "=== Работа с JSON ===")

  (println "Введите имя файла:")
  (let [filename (read-line)
        data (load-json filename)]

    (if (nil? data)
      (println "Ошибка загрузки")
      (let [keys-list (vec (get-keys data))]

        (println "\nДоступные поля:")
        (print-keys keys-list)

        (println "Выберите номер поля:")
        (let [index (dec (Integer/parseInt (read-line))) ;; уменьшаю на 1 тк индксация с нуля
              selected-key (nth keys-list index)

              _ (println "Порядок (asc/desc):")
              order (read-line)

              result (sort-data data selected-key order)]

          (println "\nРезультат:")
          (print-data result))))))

(run)