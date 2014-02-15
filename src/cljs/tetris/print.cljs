(ns tetris.print
  (:require [tetris.logic :as logic]))
(defn to-string [game]
  (reduce
    #(str % %2 "\n")
    ""
    (mapv (fn [row]
            (apply str (mapv #(str "[" (if % (name %) " ") "]") row)))
          (logic/get-grid game))))
