(ns adventofcode2020.day1
  (:require [clojure.string :as str]))

(defn data->numbers [data]
  (->> data
       ((fn [s] (str/split s #"\n")))
       (map read-string)))

(def test-data [1721 979 366 299 675 1456])

(defn find-product [n numbers]
  (->>
   (for [x numbers
         y numbers]
     [x y])
   (filter (fn [p] (= n (apply + p))))
   (map (fn [a] (apply * a)))))

(defn find-product-2 [n numbers]
  (->>
   (for [x numbers
         y numbers
         z numbers]
     [x y z])
   (filter (fn [p] (= n (apply + p))))
   (map (fn [a] (apply * a)))))

(defn day-1-part-1 [data n]
  (->> data
       data->numbers
       (find-product n)))

(defn day-1-part-2 [data n]
  (->> data
       data->numbers
       (find-product-2 n)))
