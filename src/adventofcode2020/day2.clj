(ns adventofcode2020.day2
  (:require [clojure.string :as str]))

(defn parse-data [data]
  (->> data
       (str/split-lines)
       (map (fn [s] (str/split s #" ")))
       (map (fn [[r l-colon s]]
              (let [[low high] (map read-string (str/split r #"-"))
                    l (first l-colon)]
                {
                 :high high
                 :low low
                 :letter l
                 :password s
                 }
                )))))



(defn is-valid-1 [password]
  (let [filtered (filter (fn [s] (= s (:letter password))) (:password password))
        length (count filtered)]
    (and (<= length (:high password)) (>= length (:low password)))))

(defn is-valid-2 [password]
  (let [pos1 (:low password)
        pos2 (:high password)
        pw (:password password)
        letter1 (get pw (dec pos1))
        letter2 (get pw (dec pos2))]
    (or
     (and (not= (:letter password) letter1) (= (:letter password) letter2))
     (and (= (:letter password) letter1) (not= (:letter password) letter2)))))


(defn day2-part-1 [data]
  (->> data
       (parse-data)
       (filter is-valid-1)
       (count)))

(defn day2-part-2 [data]
  (->> data
       (parse-data)
       (filter is-valid-2)
       (count)))
