(ns om-next-simple.core
  (:require [om.next :as om :refer [defui]]
            [om.dom :as dom]
            [onion-simple.core :as sc]))

(def simple-element (om/factory sc/SimpleElement))

(defui Main
  Object
  (render [this]
          (dom/div nil
                   (simple-element))))

(def main (om/factory Main))

(defn start
  []
  (js/ReactDOM.render (main) (.getElementById js/document "app")))

(defn ^:export init []
  (start))

