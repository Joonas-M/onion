(ns onion.reagent.api)

(defn render
  []
  `(def ~'reagent-render
     '(fn 
        ([component# html-element#] (reagent.core/render [component#] html-element#))
        ([component# html-element# args#] (reagent.core/render (vec (apply component# args#)) html-element#))
        ([component# html-element# args# callback#]
         (reagent.core/render (vec (apply component# args#)) html-element# callback#)))))

