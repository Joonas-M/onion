(ns onion.core
  (:require [onion.reagent.core :as r-core]
            [onion.om-next.core :as on-core]
            [onion.env :as env]))

(defmacro require-wrapper-library
  []
  (case env/WRAPPER_LIBRARY
    "reagent" `(require 'reagent.core)
    "om-next" `(require 'om.next)
    nil))

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
       ;; Must return nil (or just anything) after require or else, compile exception happens
       "reagent" `(do (require '~'reagent.core)
                      ~(defelement* :reagent name parameters component-methods local-state lifetime-events body)
                      nil)
       "om-next" `(do #_(require '~'[om.next]
                                 '~'[om.dom])
                      (require 'om.next)
                      ~(defelement* :om-next name parameters component-methods local-state lifetime-events body)
                      nil)
       :empty)))


