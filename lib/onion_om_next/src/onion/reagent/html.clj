(ns onion.reagent.html)

(defn html [elementti attributes body]
  `[~(keyword elementti) ~attributes ~body])
