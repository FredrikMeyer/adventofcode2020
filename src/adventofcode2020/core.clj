(ns adventofcode2020.core
  (:require [adventofcode2020.day1 :as day1]
            [adventofcode2020.day2 :as day2]
            [adventofcode2020.day3 :as day3]
            [adventofcode2020.day4 :as day4]
            [adventofcode2020.day5 :as day5]
            [adventofcode2020.day6 :as day6]
            [adventofcode2020.day7 :as day7]
            [adventofcode2020.day8 :as day8]
            [adventofcode2020.day9 :as day9]
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

(defn day5-1-answer []
  (let [data (slurp (io/resource "day5.txt"))]
    (day5/day5-part-1 data)))

(defn day5-2-answer []
  (let [data (slurp (io/resource "day5.txt"))]
    (day5/day5-part-2 data)))

(defn day6-1-answer []
  (let [data (slurp (io/resource "day6.txt"))]
    (day6/part-1 data)))

(defn day6-2-answer []
  (let [data (slurp (io/resource "day6.txt"))]
    (day6/part-2 data)))

(defn day7-1-answer []
  (let [data (slurp (io/resource "day7.txt"))]
    (day7/part-1 data)))

(defn day7-2-answer []
  (let [data (slurp (io/resource "day7.txt"))]
    (day7/part-2 data)))

(defn day8-1-answer []
  (let [data (slurp (io/resource "day8.txt"))]
    (day8/part-1 data)))

(defn day8-2-answer []
  (let [data (slurp (io/resource "day8.txt"))]
    (day8/part-2 data)))

(defn day9-1-answer []
  (let [data (slurp (io/resource "day9.txt"))]
    (day9/part-1 data)))

(defn day9-2-answer []
  (let [data (slurp (io/resource "day9.txt"))]
    (day9/part-2 data)))
