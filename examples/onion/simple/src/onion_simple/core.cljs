(ns onion-simple.core
  (:require [onion.html :as html])
  (:require-macros [onion.core :as om]))

(om/defelement simple-element
  [] nil nil nil
  (html/div nil
            "foo"))
