(ns onion.om-next.html)

(defn html
  [element attributes body]
  `(~(symbol "om.dom" (str element)) (~'clj->js ~attributes) ~body))
