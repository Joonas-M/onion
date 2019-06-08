(ns onion.reagent.core)

(defn defelement
  [name parameters component-methods local-state lifetime-events body]
  `(def ~name
     (fn ~parameters
       ~(if (or component-methods
                local-state)
          `(let ~(vec (concat component-methods
                              (into []
                                    (mapcat (fn [state-name state-value]
                                              [state-name (clojure.core/atom state-value)])
                                            (partition 2 local-state)))))
             (reagent.core/create-class ~(merge lifetime-events
                                                {:render
                                                 `(fn [~'this]
                                                    (let [~parameters (reagent.core/argv ~'this)]
                                                      ~@body))})))
          `(reagent.core/create-class ~(merge lifetime-events
                                              {:render
                                               `(fn [~'this]
                                                  (let [~parameters (reagent.core/argv ~'this)]
                                                    ~@body))}))))))
