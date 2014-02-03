(ns tetris.logic.impl
  (:use [jeremycw.util :only [fill-vec sum-vecs truthy?]]))

(def grid-width 10)
(def grid-height 22)
(def starting-pos [0 3])
(def block-coords [[0 0] [0 1] [0 2] [0 3]
                   [1 0] [1 1] [1 2] [1 3]
                   [2 0] [2 1] [2 2] [2 3]
                   [3 0] [3 1] [3 2] [3 3]])

(def initial-grid (fill-vec (fill-vec nil grid-width) grid-height))

(defn block [shape & coords]
  (reduce #(assoc-in %1 %2 shape)
          (fill-vec (fill-vec nil 4) 4) coords))

(def blocks {:j (block :j [0 2] [1 2] [2 1] [2 2])
             :l (block :l [0 1] [1 1] [2 1] [2 2])
             :s (block :s [0 1] [0 2] [1 0] [1 1])
             :z (block :z [0 1] [0 2] [1 2] [1 3])
             :i (block :i [0 1] [1 1] [2 1] [3 1])
             :t (block :t [0 0] [0 1] [0 2] [1 1])
             :o (block :o [0 1] [0 2] [1 1] [1 2])})

(defn rand-block []
  (rand-nth [:i :j :l :s :z :t :o]))

(defn rotate-block [block]
  (apply mapv vector block))

(defn coord-in-bounds? [[first-row & xs :as grid] [row col]]
  (and (< row (count grid))
       (< col (count first-row))
       (>= row 0)
       (>= col 0)))

(defn legal-move? [grid destination block]
  (every? truthy? (mapv #(let [coord (sum-vecs % destination)
                               empty-in-block (not (get-in block %))
                               empty-in-grid (not (get-in grid coord))
                               in-bounds (coord-in-bounds? grid coord)]
                           (or empty-in-block (and in-bounds empty-in-grid)))
                        block-coords)))

(defn assoc-block [grid destination block]
  (reduce #(assoc-in %1 (sum-vecs destination %2) (get-in block %2))
          grid
          block-coords))

(defn calc-score [score rows-cleared]
  (+ score (* rows-cleared rows-cleared 10)))

(defn score-game [{:keys [grid score] :as game}]
  (let [size (count grid)
        cleared-grid (filter #(not (every? truthy? %)) grid)
        rows-cleared (- size (count cleared-grid))
        next-block ((rand-block) blocks)
        new-rows (fill-vec
                   (fill-vec nil (count (first grid)))
                   rows-cleared)
        next-game-base (assoc game :block next-block
                                   :finished (not (legal-move?
                                                    grid
                                                    starting-pos
                                                    next-block))
                                   :position starting-pos)]
    (if (zero? rows-cleared)
      next-game-base
      (assoc next-game-base :grid (into new-rows cleared-grid)
                            :score (calc-score score rows-cleared)))))

(defn tetris-translate [{:keys [position block grid] :as game} translation]
  (let [destination (sum-vecs position translation)]
    (if (legal-move? grid destination block)
      (assoc game :position destination)
      game)))

(defn tetris-rotate [{:keys [position block grid] :as game}]
  (let [rotated-block (rotate-block block)]
    (if (legal-move? grid position rotated-block)
      (assoc game :block rotated-block)
      game)))
  
(defn tetris-down [{:keys [position block grid hit-bottom] :as game}]
  (let [destination (sum-vecs position [1 0])]
    (if (legal-move? grid destination block)
      (assoc game :position destination)
      (if hit-bottom
        (score-game
          (assoc game :hit-bottom false
                      :grid (assoc-block grid position block)))
        (assoc game :hit-bottom true)))))

(defn tetris-drop [game]
  (loop [{hb :hit-bottom :as g} game]
    (if hb
      (tetris-down g)
      (recur (tetris-down g)))))
