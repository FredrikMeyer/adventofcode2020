(ns adventofcode2020.day9
  (:require [clojure.string :as str]))

(def test-data "35
20
15
25
47
40
62
55
65
95
102
117
150
182
127
219
299
277
309
576")

(defn parse-data [data]
  (->> data
       (str/split-lines)
       (map read-string)))

(defn is-sum-of [coll n]
  (first
   (filter (fn [s] (= s n))
           (for [x coll
                 y coll
                 :when (not= x y)]
             (+ x y)))))

(defn first-non-sum [steps coll]
  (loop [numbers coll]
    (let [last-five (take steps numbers)
          rests (drop steps numbers)
          current (first rests)]
      (if (not (is-sum-of last-five current))
        (first rests)
        (recur (rest numbers))))))

(defn summing-to [coll s]
  (let [[sum c] (reduce (fn [[current-s current-seq] curr-val]
                    (let [new-s (+ current-s curr-val)
                          new-seq (conj current-seq curr-val)]
                      (cond (= new-s s) (reduced [new-s new-seq])
                            (> new-s s) (reduced [new-s new-seq])
                            :else [new-s new-seq])
                      )
                    )
                  [0 []]
                  coll)]
    (if (= s sum)
      c nil)))

(defn find-summing-to [coll n]
  (loop [current-col coll]
    (let [c (summing-to current-col n)]
      (if c c (recur (rest current-col))))))

(defn encryption-weakness [coll]
  (let [minimum (apply min coll)
        maximum (apply max coll)]
    (+ minimum maximum)))

(defn part-1 [data]
  (->> data
       (parse-data)
       (first-non-sum 25)))

(defn part-2 [data]
  (let [parsed (parse-data data)
        part-1-answer (part-1 data)
        cont-seq (find-summing-to parsed part-1-answer)]
    (encryption-weakness cont-seq)))


;; Demonstrating laziness
;;(take 5 (for [x (cycle [1])] x))
