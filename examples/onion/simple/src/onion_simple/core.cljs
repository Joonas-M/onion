(ns onion-simple.core
  (:require [onion.html :as html]
            <<WRAPPER_CORE_NS>>)
  (:require-macros [onion.core :as om]))

(om/defelement simple-element
  [] nil nil nil
  (html/div nil
            "foo"))
