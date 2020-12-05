(ns adventofcode2020.day5
  (:require [clojure.string :as str]
            [clojure.math.numeric-tower :as math]))

(def test-data "BFFFBBFRRR
FFFBBBFRRR
BBFFBBFRLL")

(defn to-number [row-string high-char]
  (loop [r row-string
         exp (dec (count row-string))
         c 0]
    (if (empty? r) c
        (let [current-char (first r)
              a (if (= high-char current-char) 1 0)]
          (recur (rest r)
                 (dec exp)
                 (+ c (* a (math/expt 2 exp))))))))

(defn row-and-seat-number [[row-string seat-string]]
  [(to-number row-string \B) (to-number seat-string \R)])

(defn to-seat-id [[row seat]]
  (+ seat (* 8 row)))

(defn parse-data [data]
  (->> data
      (str/split-lines)
      (map (fn [d]
               (let [row-string (subs d 0 7)
                     seat-string (subs d 7)]
                 [row-string seat-string]
                 )))
      (map row-and-seat-number)
      (map to-seat-id)))

(defn find-first-gap [sorted-list]
  (loop [curr sorted-list]
    (if (empty? curr) nil
        (let [[a b] (take 2 curr)]
          (if b
            (if (> (- b a) 1) a
                (recur (rest curr)))
            nil)))))

(defn day5-part-1 [data]
  (->> data
       (parse-data)
       (apply max)))

(defn day5-part-2 [data]
  (->> data
       (parse-data)
       (sort)
       (find-first-gap)
       (inc)))
