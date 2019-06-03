(ns onion.reagent.core)

(defn defelement
  [name parameters local-state lifetime-events body]
  `(def ~name
     (fn ~parameters
        ~(if local-state
           `(let local-state
              (reagent.core/create-class ~(merge lifetime-events
                                                 {:reagent-render
                                                  `(fn ~parameters
                                                     ~@body)})))
           `(reagent.core/create-class ~(merge lifetime-events
                                               {:reagent-render
                                                `(fn ~parameters
                                                   ~@body)}))))))
