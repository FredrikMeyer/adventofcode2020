(ns adventofcode2020.day6
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def test-data "abc

a
b
c

ab
ac

a
a
a
a

b")

(defn split-string [regex string]
  (str/split string regex))

(defn parse-data [data]
  (->> data
       (split-string #"\n\n")))


(defn part-1 [data]
  (->> data
       (parse-data)
       (map (fn [s] (str/replace s #"\n" "")))
       (map set)
       (map count)
       (apply +)))

(defn part-2 [data]
  (->> data
       (parse-data)
       (map str/split-lines)
       (map (fn [g] (map set g)))
       (map (fn [g] (apply set/intersection g)))
       (map count)
       (apply +)))
