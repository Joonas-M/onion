(ns onion.html
  (:refer-clojure :exclude [map meta time filter set symbol use])
  (:require-macros [onion.html :as html])
  (:require <<DOM_DEFS_CLJS>>))

(html/html-elements true)
