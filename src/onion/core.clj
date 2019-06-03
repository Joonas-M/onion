(ns onion.core
  (:require [onion.html :as html]
            [onion.reagent.core :as r-core]))

(defmulti defelement*
  (fn [wrapper-library _ _ _ _ _]
    wrapper-library))

(defmethod defelement* :reagent
  [_ name parameters local-state lifetime-events body]
  (r-core/defelement name parameters local-state lifetime-events body))

(defmethod defelement* :default
  [_ name parameters local-state lifetime-events body]
  )

(defmacro defelement
  [name parameters local-state lifetime-events & body]
  (let [fn-name (gensym)]
    `(list
      (cond
        ;; Must return nil (or just anything) after require or else, compile exception happens
        (identical? WRAPPER_LIBRARY "reagent") (do (require '~'reagent.core) nil)
        (identical? WRAPPER_LIBRARY "empty") :foo)
      ~(defelement* :reagent (symbol (str fn-name "reagent")) parameters local-state lifetime-events body)
                                        ;~(defelement* :om (symbol (str fn-name "om")) parameters local-state lifetime-events body)
      (def ~name
        (fn ~parameters
          (cond
            (identical? WRAPPER_LIBRARY "reagent") (apply ~(symbol (str fn-name "reagent"))
                                                          ~parameters)
            (identical? WRAPPER_LIBRARY "empty") :foo))))))

(defmacro html-elements []
  (html/html))
