(ns huffman.views.encoding.front 
	[:require 
		[huffman.views.encoding.back :as back]
		[huffman.views.encoding.priority-queue :as pq]
	])

(defrecord Encoding [ch code])

(defn connect
		"connects two huffman nodes to become one huffman tree"
		[h1 h2]
		(if (nil? h1)
			h1
			(if (nil? h2)
				h2
				(huffman.views.encoding.back.Hnode. nil (+ (:freq h1) (:freq h2)) h1 h2)
				))
	)

(defn build
		"Builds a huffman tree from a given priority queue"
		[pq]
		(if (= (count pq) 1)
			pq
			(let [h1 (first (pq/ret-min :freq pq))
				 h2 (first (pq/ret-min :freq (pq/delete :freq pq)))]
				 (recur (cons (connect h1 h2)
				 		(pq/delete :freq (pq/delete :freq pq))))
				 )
			)
	)

(defn treeify
		"Converts string into huffman tree"
		[s]
		(first (build (back/freq s)))
	)

(defn convert
		"Returns sequence of encodings"
		([s]
			(convert (treeify s) "" {}))
		([t past res]
			(if (and (nil? (:l t)) (nil? (:r t)))
				(assoc res (:ch t) past)
				(let [l_r (convert (:l t) (str past "0") res)]
					(convert (:r t) (str past "1" (:code l_r)) l_r)
					)
				)
			)
		)

(defn encode
		"Putting it all together"
		([s]
		(let [huff_map (convert s)]
			(encode s huff_map)
			))
		([s t]
			(if (empty? s)
				""
				(str (t (first s)) (encode (next s) t))
			)
		)
	)

(defn decode
		"Decodes a bitstring, given the huffman tree"
		([bs t]
			(decode bs t t "")
		)
		([bs curr_t fin_t res]
;			(println (str "bs: " bs))
;			(println (str "res: " res))
			(cond
				(nil? (:ch curr_t)) 
					(cond
						(= (first bs) \0) (recur (next bs) (:l curr_t) fin_t res)
						(= (first bs) \1) (recur (next bs) (:r curr_t) fin_t res)
					)
				:else 
					(if (empty? bs)
						(str res (:ch curr_t))
						(recur bs fin_t fin_t (str res (:ch curr_t)))
			)
		)
	)
)


(def ex1 "You should test individual functions 
					as you work on the assignment. However, 
					we have also provided the HW6 main in 
					hw6-main.c0, which will comprehensively 
					test all of the parts of this assignment.")

(def res1 (encode ex1))

(def tr1 (treeify ex1))

(def ex2 "Lorem ipsum dolor sit amet, consectetur adipiscing
				 elit. Nullam enim nisi, aliquet vitae porttitor et, 
				 malesuada sit amet ante. Suspendisse commodo sagittis 
				 lobortis. Vestibulum sapien magna, pretium rutrum 
				 aliquam sed, iaculis in sapien. Aliquam hendrerit, 
				 risus sed dignissim cursus, augue risus porttitor orci, 
				 ut pretium elit nibh ac neque. Nunc sed mattis quam. 
				 Donec facilisis, erat sit amet posuere faucibus, risus
				  ligula viverra mi, ut commodo nulla diam vitae risus.
				   Donec sit amet rhoncus justo. Morbi a venenatis dui.
				    Vestibulum vitae porta orci. Nulla nulla lacus, lobortis 
				    sed ullamcorper nec, adipiscing vel lorem. 
				    Vestibulum ante ipsum primis in faucibus orci 
				    luctus et ultrices posuere cubilia Curae; Suspendisse 
				    est nisi, vestibulum ut gravida non, ullamcorper at massa. 
				    Morbi eu augue libero, vitae vulputate eros. Proin 
				    venenatis pellentesque varius. Nulla in massa elit, 
				    id pellentesque nulla.

				Integer lobortis, metus vel bibendum vehicula, ante risus 
				euismod dui, ac interdum odio lorem id sapien. Phasellus 
				vel velit nulla, a sollicitudin lectus. Vestibulum ante 
				ipsum primis in faucibus orci luctus et ultrices posuere
				 cubilia Curae; Etiam ante purus, auctor sit amet vehicula
				  et, ultrices a nunc. Sed suscipit ornare lectus eget
				   dictum. Nam aliquam leo mattis nisl pulvinar at 
				   tincidunt nunc gravida. Nam eu nulla sit amet leo 
				   interdum sollicitudin nec et mauris. Integer aliquam, 
				   massa sit amet lacinia cursus, lectus sapien lacinia
				    nulla, eget mattis dolor eros a orci. Nullam venenatis
				     tempor dui, venenatis tristique sem rhoncus et. Ut
				      fermentum nisi eget magna lacinia sit amet accumsan 
				      velit tempus. Mauris iaculis tempor erat eget dictum.
				       Aliquam a enim eget ipsum imperdiet convallis nec nec
				        nibh. Curabitur ut dolor ante.")

(def res2 (encode ex2))

(def tr2 (treeify ex2))



