(ns onion.om-next.core
  (:require [clojure.string :as clj-str]))

(defn defelement
  [name parameters component-methods local-state lifetime-events body]
  (let [lifetime-events (if (nil? local-state)
                          lifetime-events
                          (merge-with (fn [user-defined-fn local-state-set]
                                        `(comp ~user-defined-fn ~local-state-set))
                                      lifetime-events
                                      {:component-did-mount
                                       `(fn [this]
                                          ('om.next/react-set-state! this local-state))
                                       :component-will-unmount
                                       `(fn [this]
                                          ('om.next/react-set-state! this nil))}))])
  `(om.next/defui ~(symbol (-> name
                                (clj-str/capitalize)
                                (clj-str/replace #"-[a-z]" #(clj-str/upper-case (second %)))))
    ~'Object
    ~@(map (fn [[fn-name f]]
             `(~fn-name
               ~@(drop-while (fn [sym]
                               (not (vector? sym)))
                             f)))
           (partition 2 component-methods))
    ~@(map (fn [[event-name event-fn]]
             `(~event-name
               ~@(drop-while (fn [sym]
                               (not (vector? sym)))
                             event-fn)))
           lifetime-events)
    (~'render [~'this]
     (let [~parameters ('om.next/props ~'this)]
       ~@body))))
