(ns adventofcode2020.core
  (:require [adventofcode2020.day1 :as day1]
            [adventofcode2020.day2 :as day2]
            [adventofcode2020.day3 :as day3]
            [adventofcode2020.day4 :as day4]
            [clojure.java.io :as io]))


(def day1-data (slurp (io/resource "day1.txt")))
(def day2-data (slurp (io/resource "day2.txt")))
(def day3-data (slurp (io/resource "day3.txt")))
(def day4-data (slurp (io/resource "day4.txt")))


(defn day1-1-answer []
  (first (day1/day-1-part-1 day1-data 2020)))

(defn day1-2-answer []
  (first (day1/day-1-part-2 day1-data 2020)))

(defn day2-1-answer []
  (day2/day2-part-1 day2-data))

(defn day2-2-answer []
  (day2/day2-part-2 day2-data))

(defn day3-1-answer []
  (day3/day3-part-1 day3-data))

(defn day3-2-answer []
  (day3/day3-part-2 day3-data))

(defn day4-1-answer []
  (day4/day4-part-1 day4-data))

(defn day4-2-answer []
  (day4/day4-part-2 day4-data))
