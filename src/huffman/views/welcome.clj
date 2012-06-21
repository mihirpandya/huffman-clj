(ns huffman.views.welcome
  (:require [huffman.views.common :as common]
            [noir.content.getting-started]
            [huffman.views.encoding.front :as front]
            [huffman.views.encoding.back :as back]
            [huffman.views.encoding.priority-queue :as pq])
  (:use [noir.core :only [defpage defpartial]]
        [hiccup.core]
        [hiccup.page-helpers]
        [hiccup.form-helpers]))


(defpartial title []
      (html5
        [:div#title
          [:h1.title "Huffman Coding"]
          [:h2.sub "Lossless Data Compression"]
        ]
      )
    )

(defpage "/welcome" []
         (common/layout
         	[:h1 "Huffman Coding"]
           [:p "Welcome to Huffman Coding"]
           )
         )