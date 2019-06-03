(ns onion.api
  (:require [onion.core :as o])
  (:require-macros [onion.api :as api]))

(def wrapper-library (cond
                       (identical? o/WRAPPER_LIBRARY "reagent") :reagent
                       (identical? o/WRAPPER_LIBRARY "empty") :empty
                       :else :foo))

(case wrapper-library
  :reagent (require 'reagent.core))

(api/create-render :reagent)
(api/create-render :empty)
(api/create-render :foo)

(defn render
  [& args]
  (case wrapper-library
    :reagent (apply (eval reagent-render) args)
    :empty (apply (eval empty-render) args)
    :foo (apply (eval foo-render) args)))
