(ns onion.om-next.api)

(defn render
  []
  `(def ~'om-next-render
     '(fn 
        ([component# html-element#] (.render js/ReactDOM (component#) (.getElementById js/document html-element#)))
        ([component# html-element# args#] (.render js/ReactDOM (apply component# args#) (.getElementById js/document html-element#)))
        ([component# html-element# args# callback#]
         (.render js/ReactDOM (apply component# args#) (.getElementById js/document html-element#) callback#)))))

(defn set-state!
  []
  `(def ~'om-next-set-state!
     '(fn [this# state-name# new-state#]
        (om.next/set-state! this#
                            (assoc (om.next/get-state! this#)
                                   (keyword state-name#)
                                   new-state#)))))

(defn set-state-no-render!
  []
  `(def ~'om-next-set-state-no-render!
     '(fn [this# state-name# new-state#]
        (om.next/react-set-state! this#
                                  (assoc (om.next/get-state! this#)
                                         (keyword state-name#)
                                         new-state#)))))

(defn update-state!
  []
  `(def ~'om-next-update-state!
     '(fn [this# state-name# update-fn#]
        (om.next/update-state! this# update (keyword state-name#) update-fn#))))

(defn get-state
  []
  `(def ~'om-next-get-state
     '(fn [this#]
        (om.next/get-state this#))))

(defn use-element
  []
  `(def ~'use-element
     '(fn [element-name# & params#]
        (apply element-name# params))))
