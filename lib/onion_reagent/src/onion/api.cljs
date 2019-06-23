(ns onion.api
  (:require [onion.core :as o])
  (:require-macros [onion.api :as api]
                   [onion.core :as core]))

#_(def wrapper-library (cond
                       (identical? o/WRAPPER_LIBRARY "reagent") :reagent
                       (identical? o/WRAPPER_LIBRARY "reagent") :om-next
                       (identical? o/WRAPPER_LIBRARY "empty") :empty
                       :else :foo))
#_(case wrapper-library
    :reagent (require 'reagent.core)
    :om-next (require 'om.next)
    :empty "")

(core/require-wrapper-library)

;; (api/create-render)
;; (api/create-set-state!)
;; (api/create-set-state-no-render!)
;; (api/create-update-state!)
;; (api/create-get-state)
;; (api/create-use-element)

#_(defn render
  [& args]
  (cond
    (identical? o/WRAPPER_LIBRARY "reagent") (apply (eval reagent-render) args)
    (identical? o/WRAPPER_LIBRARY "om-next") (apply (eval om-next-render) args)
    (identical? o/WRAPPER_LIBRARY "empty") (apply (eval empty-render) args)))

;;(api/create-api-fn "render")

;; (api/create-api-fn 'set-state!)
;; (api/create-api-fn 'set-state-no-render!)
;; (api/create-api-fn 'update-state!)
;; (api/create-api-fn 'get-state)
;; (api/create-api-fn 'use-element)
