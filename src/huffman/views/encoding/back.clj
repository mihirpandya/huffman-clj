(ns huffman.views.encoding.back
 (:require [huffman.views.encoding.priority-queue :as pq]))

; Node of a huffman tree.
; If it's a leaf node, l and r are nil
; If it's an intermediate node, ch is nil and freq
; is the sum of the freqs of the two child nodes.

(defrecord Hnode [ch freq l r])

(defn createHnode
    "Creates a Hnode from character and frequency"
    [c f]
    (Hnode. c f nil nil)
  )

(defn freq-helper
		"Accepts a ch and sequence of characters
		and returns the ch's corresponding Hnode."
		[c s]
			(let [freq (count (filter (fn [*c] (= *c c)) s))]
			{c freq})
		)

(defn freq-interm
		"Returns a vector of maps for each character"
		[s]
		(if (empty? s)
				pq/new-pq
				(cons 
					(freq-helper (first s) s)
					(freq-interm (filter (fn [c] (not (= c (first s)))) s))
						)
				)
		)

;(def m (reduce merge (map (fn [x] {(first x) (peek x)}) (sort test))))

(defn freq
		[s]
		(let [t (reduce merge (freq-interm s))]
			(map 
            (fn [x y] (createHnode x y))
            (keys (sort t)) (vals (sort t)))
		)
	)


