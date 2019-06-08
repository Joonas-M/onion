(ns onion.core
  (:require #_[onion.html :as html]
            [onion.reagent.core :as r-core]
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
  (let [fn-name (gensym)]
    `(list
      ~(case env/WRAPPER_LIBRARY
         ;; Must return nil (or just anything) after require or else, compile exception happens
         "reagent" `(do (require '~'reagent.core)
                        ~(defelement* :reagent name parameters component-methods local-state lifetime-events body)
                        nil)
         "om-next" `(do (require '~'om.next)
                        ~(defelement* :om-next (symbol (str fn-name "om-next")) parameters component-methods local-state lifetime-events body)
                        nil)
         :empty)
      #_(def ~name
        (fn ~parameters
          ~(case env/WRAPPER_LIBRARY
             "reagent" 
             `(apply ~(symbol (str fn-name "reagent"))
                                 ~parameters)
               "om-next" `(apply ~(symbol (str fn-name "om-next"))
                                 ~parameters)
               :empty))))))


