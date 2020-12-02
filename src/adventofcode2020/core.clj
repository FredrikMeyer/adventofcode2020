(ns adventofcode2020.core
  (:require [adventofcode2020.day1 :as day1]
            [adventofcode2020.day2 :as day2]
            [clojure.java.io :as io]))


(def day1-data (slurp (io/resource "day1.txt")))
(def day2-data (slurp (io/resource "day2.txt")))



(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))



(defn day1-1-answer []
  (first (day1/day-1-part-1 day1-data 2020)))

(defn day1-2-answer []
  (first (day1/day-1-part-2 day1-data 2020)))

(defn day2-1-answer []
  (day2/day2-part-1 day2-data))

(defn day2-2-answer []
  (day2/day2-part-2 day2-data))
