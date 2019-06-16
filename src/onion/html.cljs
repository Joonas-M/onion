(ns onion.html
  (:refer-clojure :exclude [map meta time filter set symbol use])
  (:require-macros [onion.html :as html])
  (:require [onion.om-next.html]
            [onion.reagent.html]))

(html/html-elements true)
