(ns tetris.logic
  (:require [tetris.logic.impl :as impl]))

(defn new-game [block-seq]
  {:grid impl/initial-grid
   :block ((first (block-seq)) impl/blocks)
   :hit-bottom false
   :score 0
   :finished false
   :block-seq (rest (block-seq))
   :position impl/starting-pos})

(defn get-grid [{:keys [grid block position]}]
  (impl/assoc-block grid position block))

(defn game-over? [{finished :finished}] finished)

(defn get-score [{score :score}] score)

(defn random-block-seq []
  (cons (impl/rand-block) (lazy-seq (random-block-seq))))

(defn make-move [game command]
  (case command
    :left (impl/tetris-translate game [0 -1])
    :right (impl/tetris-translate game [0 1])
    :down (impl/tetris-down game)
    :rotate (impl/tetris-rotate game)
    :drop (impl/tetris-drop game)))

