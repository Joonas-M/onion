(ns onion.env
  (:require [clojure.string :as clj-str]))

(defonce WRAPPER_LIBRARY (clj-str/trim <<WRAPPER_LIBRARY>>))
