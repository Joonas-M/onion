(ns onion.api
  (:require [onion.reagent.api :as reagent-api]
            [onion.om-next.api :as om-next-api]
            [onion.env :as env]))

#_(defmacro create-multi
  [name]
  `(list
    (defmulti ~(symbol (str name "*"))
      (fn [wrapper-library]
        wrapper-library))
    
    (defmethod ~(symbol (str name "*")) :reagent
      [_]
      (~(symbol (str "reagent-api/" name))))
    
    (defmethod ~(symbol (str name "*")) :om-next
      [_]
      (~(symbol (str "om-next-api/" name))))
    
    (defmethod ~(symbol (str name "*")) :empty
      [_]
      (def ~(symbol (str "empty-" name))
        '(fn [& args#])))))

;; (defmulti render*
;;   (fn [wrapper-library]
;;     wrapper-library))

;; (defmethod render* :reagent
;;   [_]
;;   (reagent-api/render))

;; (defmethod render* :om-next
;;   [_]
;;   (om-next-api/render))

;; ;; empty is for debugging
;; (defmethod render* :empty
;;   [_]
;;   `(def ~'empty-render
;;      '(fn [& args#])))

;; (defmulti set-state* [])

(defmacro create-api-fn
  [fn-name]
  (case env/WRAPPER_LIBRARY
    "reagent"  ((var (symbol "onion.reagent.api" fn-name)))
     "om-next" ((eval (symbol "onion.om-next.api" fn-name)))
     `(def ~(symbol fn-name)
        (fn [& ~'args]
          (throw ('js/Error. (str fn-name " not defined for " env/WRAPPER_LIBRARY
                                 ". Please, report this bug."))))))
#_  `(defn ~(symbol quo)
     [& args#]
     (case env/WRAPPER_LIBRARY
       "reagent"  `(apply (eval ~(symbol (str "reagent-" quo))) args#)
       "om-next" `(apply (eval ~(symbol (str "om-next-" quo))) args#)
       `(apply (eval ~(symbol (str "empty-" quo))) args#)))) 

#_(defn- create-fns
  [sym]
  `(do
     ~((eval (symbol "onion.reagent.api" sym)))
     ~((eval (symbol "onion.om-next.api" sym)))  
     (def ~(symbol (str "empty-" sym))
       '(fn [& args]))))

#_(create-multi 'render)
#_(defmacro create-render []
  (create-fns "render"))

#_(create-multi 'set-state!)
#_(defmacro create-set-state! []
  #_(set-state!* wrapper-library)
  (create-fns 'set-state!))

#_(create-multi 'set-state-no-render!)
#_(defmacro create-set-state-no-render! []
  (create-fns 'set-state-no-render!))

#_(create-multi 'update-state!)
#_(defmacro create-update-state! []
  (create-fns 'update-state!))

#_(create-multi 'get-state)
#_(defmacro create-get-state []
  (create-fns 'get-state))

#_(create-multi 'use-element)
#_(defmacro create-use-element []
  (create-fns 'use-element))
