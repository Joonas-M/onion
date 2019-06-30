(ns onion.reagent.core)

(defn defelement
  [name parameters component-methods local-state lifetime-events body]
  `(def ~name
     (fn ~parameters
       (let component-methods
         (reagent.core/create-class ~(merge (merge-with (fn [user-defined-fn local-state-set]
                                                          `(comp ~user-defined-fn ~local-state-set))
                                                        lifetime-events
                                                        {:component-did-mount
                                                         `(fn [this]
                                                            (-> this .-cljsState (set! local-state)))
                                                         :component-did-unmount
                                                         `(fn [this]
                                                            (-> this .-cljsState (set! nil)))})
                                            {:render
                                             `(fn [~'this]
                                                (let [~parameters (reagent.core/argv ~'this)]
                                                  ~@body))}))))))
