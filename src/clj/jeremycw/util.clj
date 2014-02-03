(ns jeremycw.util)

(defn fill-vec [item length]
  (loop [w length row []]
    (if (= w 0)
      row
      (recur (- w 1) (conj row item)))))

(defn sum-vecs [first-vec & vecs]
  (reduce (fn [acc next-vec]
            (mapv (fn [[a b]] (+ a b)) (mapv vector acc next-vec)))
          first-vec
          vecs))

(defn truthy? [thing]
  (if thing true false))
