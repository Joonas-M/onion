(ns onion-simple.core
  (:require [onion.core :as o]
            [onion.api :as oapi])
  (:require-macros [onion.core :as om]))

(om/defelement simple-element
  [] nil nil
  [:div "foo"]
  #_(o/div nil
         "foo"))
