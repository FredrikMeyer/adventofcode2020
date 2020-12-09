(ns adventofcode2020.day8
  (:require [clojure.string :as str]))

(def test-data "nop +0
acc +1
jmp +4
acc +3
jmp -3
acc -99
acc +1
jmp -4
acc +6")

(defn parse-line [idx l]
  (let [[op val] (str/split l #" ")]
    {:op (keyword op)
     :val (read-string val)
     :index idx
     }))

(defn parse-data [data]
  (->> data
       (str/split-lines)
       (map-indexed parse-line)
       (vec)))

(def start-state
  {
   :accumulator 0
   :visited-instructions #{}
   :current-instruction 0
   })

(defn reached-loop [state]
  (let [curr (:current-instruction state)
        visited (:visited-instructions state)]
    (contains? visited curr)))

(defn terminated? [state max]
  (>= (:current-instruction state) max))

(defn add-to-visited [state index]
  (update state :visited-instructions (fn [v] (conj v index))))

(defn accumulate [state index value]
  (-> state
      (update :accumulator (fn [v] (+ v value)))
      (update :current-instruction inc)
      (add-to-visited index)))

(defn jump [state index value]
  (-> state
      (update :current-instruction (fn [v] (+ v value)))
      (add-to-visited index)))

(defn nop [state index]
  (-> state
      (update :current-instruction inc)
      (add-to-visited index)))

(defn run-instruction [state instruction]
  (let [index (:index instruction)]
    (condp = (:op instruction)
      :acc (accumulate state index (:val instruction))
      :jmp (jump state index (:val instruction))
      :nop (nop state index))))


(defn run-instructions [instructions]
  (loop [current-index 0
         state start-state]
    (let [current-instruction (get instructions current-index)
          new-state (run-instruction state current-instruction)
          new-index (:current-instruction new-state)]
      (cond (reached-loop new-state) { :result new-state :reason :loop }
            (terminated? new-state (count instructions)) { :result new-state :reason :terminated }
            :else (recur new-index new-state)))))

(defn get-jump-indices [parsed-data]
  (->> parsed-data
       (map-indexed (fn [i d] [i d]))
       (filter (fn [[_ d]] (or (= (:op d) :jmp) (= (:op d) :nop))))
       (map first)))

(defn exchange-nop-jmp [instruction]
  (update instruction :op (fn [o] (condp = o :jmp :nop :nop :jmp :acc :acc))))

(defn fix-at-pos [instructions pos]
  (let [new-instructions (update instructions pos exchange-nop-jmp)]
    new-instructions))

(defn try-fixes [instructions]
  (let [indices (get-jump-indices instructions)]
    (loop [current-index (first indices)
           rest-of-indices (rest indices)]
      (let [fixed (fix-at-pos instructions current-index)
            result (run-instructions fixed)]
        (if (= (:reason result) :terminated)
          result
          (recur (first rest-of-indices) (rest rest-of-indices)))))))

(defn part-1 [data]
  (->> data
       (parse-data)
       (run-instructions)
       :result
       :accumulator))

(defn part-2 [data]
  (->> data
       (parse-data)
       (try-fixes)
       :result
       :accumulator))

(def test-data2 "nop +0
acc +1
jmp +4
acc +3
jmp -3
acc -99
acc +1
nop -4
acc +6")
