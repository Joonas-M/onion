(ns onion.reagent.api)

(defn render
  []
  `(def ~'render
     (fn 
        ([component# html-element#] (reagent.core/render [component#] html-element#))
        ([component# html-element# args#] (reagent.core/render (vec (apply component# args#)) html-element#))
        ([component# html-element# args# callback#]
         (reagent.core/render (vec (apply component# args#)) html-element# callback#)))))

(defn set-state!
  []
  `(def ~'set-state!
     (fn [this# new-state#]
       (-> this# .-cljsState (set! new-state#))
       (reagent.core/force-update this#))))

(defn set-state-no-render!
  []
  `(def ~'set-state-no-render!
     (fn [this#  new-state#]
       (-> this# .-cljsState (set! new-state#)))))

(defn update-state!
  []
  `(def ~'update-state!
     (fn [this# update-fn# & args#]
       (let [old-state# (.-cljsState this#)]
         (-> this# .-cljsState (set! (apply update-fn# old-state# args#)))
         (reagent.core/force-update this#)))))

(defn get-state
  []
  `(def ~'get-state
     (fn [this#]
       (.-cljsState this#))))

#_(defn use-element
  []
  `(def ~'use-element
     (fn [element-name# & params#]
       (into []
             (concat [element-name#] params#)))))

(defn use-element
  [element-name# args#]
  (into []
        (cons element-name# args#)))
