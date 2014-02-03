(ns tetris.logic
  (:require [tetris.logic.impl :as impl]))

(defn new-game []
  {:grid impl/initial-grid
   :block ((impl/rand-block) impl/blocks)
   :hit-bottom false
   :score 0
   :finished false
   :position impl/starting-pos})

(defn get-grid [{:keys [grid block position]}]
  (impl/assoc-block grid position block))

(defn game-over? [{finished :finished}] finished)

(defn get-score [{score :score}] score)

(defn make-move [game command]
  (case command
    :left (impl/tetris-translate game [0 -1])
    :right (impl/tetris-translate game [0 1])
    :down (impl/tetris-down game)
    :rotate (impl/tetris-rotate game)
    :drop (impl/tetris-drop game)))
