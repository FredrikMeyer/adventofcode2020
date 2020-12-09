(ns adventofcode2020.directed-graph)

(defn get-neigbours [vertex graph]
  (->> (get graph vertex [])
       (map :to)))

(defn get-neighbour-edges [vertex graph]
  (get graph vertex []))

(def empty-graph {})

(defn add-edge [[from to weight] graph]
  (update graph from
          (fn [e]
            (if e
              (conj e {:to to :weight weight})
              [{:to to :weight weight}]))))

(defn add-edges [edge-list graph]
  (reduce (fn [acc curr] (add-edge curr acc)) graph edge-list))

(def sample-graph
  (->> empty-graph
       (add-edge [:DO :BW 1])
       (add-edge [:DO :MY 2])))

(defn get-edges
  "Returns the edges as a list of [start to weight] triples"
  [graph]
  (mapcat
   (fn [[k val]] (map (fn [v] [k (:to v) (:weight v)]) val)) graph))

(defn reverse-graph [graph]
  (let [edges (get-edges graph)]
    (reduce (fn [acc [from to weight]] (add-edge [to from weight] acc))
            empty-graph edges)))

(defn visited? [vertex all]
  (some #(= % vertex) all))

(defn dfs [graph v]
  (loop [stack (vector v)
         visited []]
    (if (empty? stack)
      visited
      (let [current-v (peek stack)
            neigbours (get-neigbours current-v graph)
            not-visited (filter #(not (visited? % visited)) neigbours)
            new-stack (into (pop stack) not-visited)]
        (if (visited? current-v visited)
          (recur new-stack visited)
          (recur new-stack (conj visited current-v)))))))

;;; Takk Eivind!! 
(defn dfs-with-weights [v graph]
  (loop [stack (vector {:vertex v :weight 1})
         visited []
         cum-weight 0]
    (if (empty? stack)
      [visited cum-weight]
      (let [current-v (peek stack)
            neigbours (get-neighbour-edges (:vertex current-v) graph)
            not-visited (filter #(not (visited? (:vertex %) visited)) neigbours)
            new-stack (into (pop stack) (map (fn [e] {:vertex (:to e) :weight (* (:weight current-v) (:weight e))}) not-visited))]
        (if (visited? (:vertex current-v) visited)
          (recur new-stack visited (+ cum-weight (:weight current-v)))
          (recur new-stack (conj visited current-v) (+ cum-weight (:weight current-v))))))))
