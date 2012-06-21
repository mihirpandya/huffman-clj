(ns huffman.views.main
  (:require [huffman.views.common :as common]
            [noir.content.getting-started]
            [huffman.views.encoding.front :as front]
            [huffman.views.encoding.back :as back]
            [huffman.views.encoding.priority-queue :as pq])
  (:use [noir.core :only [defpage defpartial]]
        [hiccup.core]
        [hiccup.page-helpers]
        [hiccup.form-helpers]
        [cheshire.core :only [parse-string generate-string]]))

; Setting up main page
(defpartial center []
      (html5 
        [:div#center
          [:h1.center "Huffman Coding"]
          [:h2.sub "Lossless Data Compression"]]
        )
  )

(defpartial encode []
    (html5 [:div#codeE.span6
                  [:h1#code {:onclick "showEncode()"} "Encode"]
                  (text-field {:class "span2"} "inputstring")
                  [:button#encodeSubmit {:onclick "sendString()"} "Encode!"]
                  [:div#res.span6]
                ]
      )
  )

(defpartial decode []
    (html5 [:div#codeD.span6
                  [:h1#code {:onclick "showDecode()"} "Decode"] ; Decode button
                  [:div#resD]
                  [:button#decodeSubmit {:onclick "sendDecode()"} "Decode!"]
                  [:button#addField {:onclick "addField()"} "Add Field"]
                  [:br]
                  (text-field {:class "offset2" :placeholder "\"Bitstring\""} "bitstringinput")
                  [:br]
                  [:div#freqTable
;                    (text-field {:class "ch" :placeholder "Character"} "character")
;                    (text-field {:class "freq" :placeholder "Frequency"} "frequency")
;                    [:br]
                    ]
                ]
      )
  )

; Main Page
(defpage "/main" {:as input}
        (common/layout
          (center) ; Huffman Coding title
          (encode)
          (decode)
        )
    )

; Decode Page

; Simulating JSON input
(def f-t-string (generate-string 
      (reduce (fn [x y] (merge y x)) (map (fn [x] {(:ch x) (str "" (:freq x))}) (back/freq "more free coffee"))))
    )

(def bs (front/encode "more free coffee"))

(defn getBitstring
    [bitstring t]
      (println t)
      (front/decode bitstring 
        (first 
          (front/build (map 
            (fn [x y] (back/createHnode x (Integer/parseInt y)))
            (keys (sort t)) (vals (sort t))))
          )
        )
    )

(defpage [:post "/decode"] {:keys [bitstring table]}
    (println table)
    (println (parse-string table))
    (println bitstring)
    (html5 [:div#bitstring [:h3 (getBitstring bitstring (parse-string table))]])
  )

; Doing the Encoding

(defn valid? [{:keys [inputstring]}]
;  (not (= "" inputstring))
  true
  )

(defn getFreqTable [s]
    (map (fn [x] (conj [] (:ch x) (:freq x))) (back/freq s))
  )

(defn convertToTable [s]
    (if (empty? s)
      [:table#res]
      (let [pair (first s)]
        (if (empty? (first s))
          (conj (convertToTable (next s)) [:tr [:td] [:td]])
          (conj (convertToTable (next s)) [:tr [:td (first pair)]
                                                [:td (peek pair)]])
          )
        )
      )
  )

(defpage [:post "/encode"] {:keys [inputstring]}
  (if (valid? [{:keys [inputstring]}])
    (str (html [:div#bitstring (front/encode inputstring)])
    (html (convertToTable (getFreqTable inputstring))))
  )
)