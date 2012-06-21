(ns huffman.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-css include-js html5]]))

(defpartial layout [& content]
            (html5
              [:head
               [:title "Huffman Coding"]
               (include-css "/css/bootstrap.css")
               (include-css "/css/huffman.css")
               (include-js "/js/huffman.js")
;               (include-js "//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js")
                ]
              [:body
               [:div#wrapper
                content]])
        )

;<script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>