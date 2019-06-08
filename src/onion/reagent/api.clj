(ns onion.reagent.api)

(defn render
  []
  `(def ~'reagent-render
     '(fn 
        ([component# html-element#] (reagent.core/render [component#] html-element#))
        ([component# html-element# args#] (reagent.core/render (vec (apply component# args#)) html-element#))
        ([component# html-element# args# callback#]
         (reagent.core/render (vec (apply component# args#)) html-element# callback#)))))

(defn set-state!
  []
  `(def ~'reagent-set-state!
     '(fn [this# state-name# new-state#]
        (reset! state-name# new-state#)
        (reagent.core/force-update! this#))))

(defn set-state-no-render!
  []
  `(def ~'reagent-set-state-no-render!
     '(fn [this# state-name# new-state#]
        (reset! state-name# new-state#))))

(defn update-state!
  []
  `(def ~'reagent-update-state!
     '(fn [this# state-name# update-fn#]
        (swap! state-name# update-fn#)
        (reagent.core/force-update! this#))))

(defn get-state
  []
  `(def ~'reagent-get-state
     '(fn [this# state-name#]
        @state-name#)))

(defn use-element
  []
  `(def ~'reagent-use-element
     (fn [element-name# & params#]
        (into []
              (apply element-name# params#)))))
