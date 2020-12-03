(ns adventofcode2020.day3
  (:require [clojure.string :as str]))

(def test_data "..##.........##.........##.........##.........##.........##.......
#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..
.#....#..#..#....#..#..#....#..#..#....#..#..#....#..#..#....#..#.
..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#
.#...##..#..#...##..#..#...##..#..#...##..#..#...##..#..#...##..#.
..#.##.......#.##.......#.##.......#.##.......#.##.......#.##.....
.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#
.#........#.#........#.#........#.#........#.#........#.#........#
#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...
#...##....##...##....##...##....##...##....##...##....##...##....#
.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#")

(defn parse-data [data]
  (->> data
       (str/split-lines)
       (map cycle)))

(defn count-trees [lines x-step y-step]
  (loop [count 0
         current-pos 0
         rest-of-lines lines]
    (let [current-line (first (take y-step rest-of-lines))
          value-here (nth current-line current-pos)
          new-count (+ count (if (= value-here \#) 1 0))]
      (if (empty? (nthrest rest-of-lines y-step))
        new-count
        (recur new-count
               (+ current-pos x-step)
               (nthrest rest-of-lines y-step))))))

(defn day3-part-1 [data]
  (-> data
      (parse-data)
      (count-trees 3 1)))


(defn day3-part-2 [data]
  (->> data
       (parse-data)
       ((fn [d]
          (map (fn [[x-step y-step]] (count-trees d x-step y-step))
               [[1 1] [3 1] [5 1] [7 1] [1 2]])))
       (apply *)))
