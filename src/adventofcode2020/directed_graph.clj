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

(defn paths-between [graph v1 v2]
  (loop [stack (vector v1)
         visited []
         current-path []
         paths []]
    (if (empty? stack)
      [current-path paths]
      (let [current-v (peek stack)
            at-goal (= current-v v2)
            new-current-path (conj current-path current-v)
            new-paths (if at-goal (conj paths (conj current-path current-v)) paths)
            new-new-current-path (if at-goal (pop current-path) current-path)
            neigbours (get-neigbours current-v graph)
            not-visited (filter #(not (or (visited? % visited) (= % current-v))) neigbours)
            new-stack (into (pop stack) not-visited)]
        (if (and (visited? current-v visited) (not at-goal))
          (recur new-stack visited new-current-path new-paths)
          (recur new-stack (conj visited current-v) new-current-path new-paths))))))
