(ns onion.reagent.html)

(defn html [element void-element? attributes body]
  (let [react-key_ (gensym "react-key")
        attributes_ (gensym "attributes-dis")]
    `(let [~react-key_ (:react-key ~attributes)
           ~attributes_ (if ~react-key_
                          (dissoc ~attributes :react-key)
                          ~attributes)]
       (apply vary-meta
              ~(if void-element?
                 `[~(keyword element) ~attributes_]
                 `[~(keyword element) ~attributes_ ~body])
              (if ~react-key_
                [assoc :key ~react-key_]
                [identity])))))
