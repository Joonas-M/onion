(ns reagent-flashy-table.core
  (:require [reagent.core :as r]
            [onion-flashy-table.core :as ftc]))

(defn main
  []
  [:div
   [ftc/simple-element]])

(defn start
  []
  (r/render main (.getElementById js/document "app")))

(defn ^:export init []
  (start))
