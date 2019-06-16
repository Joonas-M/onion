(ns onion.foo
  (:require [onion.env :as env]))

(defmacro require-wrapper-library
  []
  
  (case env/WRAPPER_LIBRARY
      "om-next" `(:require ~'[om.dom])
      "")
  )
