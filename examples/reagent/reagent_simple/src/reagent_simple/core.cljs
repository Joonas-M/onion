(ns reagent-simple.core
  (:require [reagent.core :as r]
            [onion-simple.core :as sc]))

(defn main
  []
  [:div
   [sc/simple-element]])

(defn start
  []
  (r/render main (.getElementById js/document "app")))

(defn ^:export init []
  (start))
