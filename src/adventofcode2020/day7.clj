(ns adventofcode2020.day7
  (:require [clojure.string :as str]
            [adventofcode2020.directed-graph :as dg]))


;;; #"^([a-z]+ [a-z]+) bags contain ((?:(?:(?:[0-9]+)|(?:no)) [a-z]+ [a-z]+ bags?,? ?)*)\.$"

(def test-data "light red bags contain 1 bright white bag, 2 muted yellow bags.
dark orange bags contain 3 bright white bags, 4 muted yellow bags.
bright white bags contain 1 shiny gold bag.
muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
dark olive bags contain 3 faded blue bags, 4 dotted black bags.
vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
faded blue bags contain no other bags.
dotted black bags contain no other bags.")

(defn parse-line [line]
  (let [[f r] (str/split line #"contain")
        bag (str/trim (first (str/split f #"bag")))
        rests (str/split r #",")
        rests-parsed (map (fn [s] (let [[_ n b] (re-matches #" *((?:(?:[0-9]+)|(?:no))) (?:([a-z]+ [a-z]+) bags?)\.?" s)] [n b])) rests)
        ]
    {
     :bag (str/replace bag " " "-")
     :children (map (fn [[c b]] { :count c :bag (str/replace b " " "-")})
                    (filter (fn [[n _]] (not= nil n))rests-parsed))
     }
    )
  )

(defn parse-data [data]
  (->> data
       (str/split-lines)
       (map parse-line)))

(defn build-graph [parsed-data]
  (reduce (fn [acc curr]
            (let [{bag :bag children :children} curr]
              (reduce (fn [a c]
                        (dg/add-edge [(keyword bag) (keyword (:bag c)) (Integer/parseInt (:count c))] a))
                      acc children)))
          dg/empty-graph parsed-data))

(defn find-parents [vertex graph]
  (let [reversed-graph (dg/reverse-graph graph)]
    (dg/dfs reversed-graph vertex)))


(defn part-1 [data]
  (->> data
       (parse-data)
       (build-graph)
       (find-parents :shiny-gold)
       (count)
       (dec)))

(defn part-2 [data]
  (->> data
       (parse-data)
       (build-graph)
       (dg/dfs-with-weights :shiny-gold)
       (second)
       (dec)))
