(ns onion.om-next.html)

(defn html
  [element void-element? attributes body]
  (if void-element?
    `(~(symbol "om.dom" (str element)) (~'clj->js ~attributes))
    `(~(symbol "om.dom" (str element)) (~'clj->js ~attributes) ~body)))
