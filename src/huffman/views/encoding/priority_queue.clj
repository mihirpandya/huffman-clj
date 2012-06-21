; Purpose: Impement priority queue for maps so as to set
;          the priority by the the value of a given key

(ns huffman.views.encoding.priority-queue)

(def new-pq ())

(defn insert
		"inserts d into pq"
		[d pq]
		(cons d pq)
		)

(defn ret-min
		"puts element from pq with smallest value of key k first"
		[k pq]
		(let [del (reduce 
					(fn [d1 d2] (if (< (k d1) (k d2))
									d1
									d2)) pq)]
			(cons del (filter (fn [d] (not (= d del))) pq))
		)
	)

(defn delete
		"deletes element form pq with smallest value of k"
		[k pq]
		(next (ret-min k pq))
	)