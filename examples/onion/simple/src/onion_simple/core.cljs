(ns onion-simple.core
  (:require [onion.core :as o]
            [onion.html :as html])
  (:require-macros [onion.core :as om]))

(om/defelement simple-element
  [] nil nil nil
  [:div "foo"]
  (html/div nil
            "foo"))
