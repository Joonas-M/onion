(ns onion.core
  (:require-macros [onion.core :as c]))

(enable-console-print!)

(goog-define ^string WRAPPER_LIBRARY "empty")

(c/html-elements)

;(c/create-defelement WRAPPER_LIBRARY)

