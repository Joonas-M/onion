(ns onion.api
  (:require [onion.reagent.api :as reagent-api]))

(defmulti render*
  (fn [wrapper-library]
    wrapper-library))

(defmethod render* :reagent
  [_]
  (reagent-api/render))

(defmethod render* :empty
  [_]
  `(def ~'empty-render
     '(fn [& args#])))

(defmethod render* :foo
  [_]
  `(def ~'foo-render
     '(fn [& args#])))

(defmacro create-render [wrapper-library]
  (render* wrapper-library))
