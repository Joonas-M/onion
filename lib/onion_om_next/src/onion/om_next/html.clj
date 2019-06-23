(ns onion.om-next.html
  (:require [onion.env :as env]))

(defn html
  [element attributes body]
  `(~(symbol "om.dom" (str element)) (~'clj->js ~attributes) ~body))
