(ns onion.core
  (:require [onion.reagent.core :as r-core]
            [onion.om-next.core :as on-core]
            [onion.env :as env]))


(defmulti defelement*
  (fn [wrapper-library _ _ _ _ _ _]
    wrapper-library))

(defmethod defelement* :reagent
  [_ name parameters component-methods local-state lifetime-events body]
  (r-core/defelement name parameters component-methods local-state lifetime-events body))

(defmethod defelement* :om-next
  [_ name parameters component-methods local-state lifetime-events body]
  (on-core/defelement name parameters component-methods local-state lifetime-events body))

(defmacro defelement
  [name parameters component-methods local-state lifetime-events & body]
  `(list
    ~(case env/WRAPPER_LIBRARY
       "reagent" (defelement* :reagent name parameters component-methods local-state lifetime-events body)
       "om-next" (defelement* :om-next name parameters component-methods local-state lifetime-events body)
       :empty)))
