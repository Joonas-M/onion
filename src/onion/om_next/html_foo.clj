(ns onion.om-next.html-foo
  (:require [onion.html :as html]
            [onion.env :as env]))

(defmacro require-wrapper-library
  []
  (when (= env/WRAPPER_LIBRARY "om-next")
    `(doall
      [~(ns onion.om-next.html-inner
          (:require [om.dom]))

       ~(html/html false)])
    #_`(require '~'[om.dom])))
