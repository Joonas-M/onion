(ns onion.reagent.core)

(defn defelement
  [name parameters component-methods local-state lifetime-events body]
  `(def ~name
     (fn ~parameters
       (let ~(or component-methods [])
         (reagent.core/create-class ~(merge (merge-with (fn [user-defined-fn local-state-set]
                                                          `(comp ~user-defined-fn ~local-state-set))
                                                        lifetime-events
                                                        {:component-did-mount
                                                         `(fn [~'this]
                                                            (-> ~'this .-cljsState (set! ~local-state)))
                                                         :component-will-unmount
                                                         `(fn [~'this]
                                                            (-> ~'this .-cljsState (set! nil)))})
                                            {:render
                                             `(fn [~'this]
                                                (let [~(into []
                                                             (concat ['_] parameters)) (reagent.core/argv ~'this)]
                                                  ~@body))}))))))
