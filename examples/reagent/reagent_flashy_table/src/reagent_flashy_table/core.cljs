(ns reagent-flashy-table.core
  (:require [reagent.core :as r]
            [onion-flashy-table.core :as ftc]))

(defonce rows (r/atom [[1 2 3 4 5]
                       ["foo" "bar" "baz" "asd" "dsa"]
                       [1 2 3 4 5]
                       ["foo" "bar" "baz" "asd" "dsa"]
                       [1 2 3 4 5]]))

(defn main
  []
  (let [save-state! (fn [new-value [j i]]
                      (swap! rows update-in [j i] (fn [_] new-value)))]
    (fn []
      [:div
       [ftc/flashy-table @rows save-state!]])))

(defn start
  []
  (r/render [main] (.getElementById js/document "app")))

(defn ^:export init []
  (start))
