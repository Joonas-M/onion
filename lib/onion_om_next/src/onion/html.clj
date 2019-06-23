(ns onion.html
  (:require [onion.reagent.html :as r-html]
            [onion.om-next.html :as on-html]
            [clojure.string :as clj-str]
            [onion.env :as env]))

(def om-next-list
  #{"a" "abbr" "address" "area" "article" "aside" "audio" "b" "base" "bdi" "bdo" "big" "blockquote"
    "body" "br" "button" "canvas" "caption" "cite" "code" "col" "colgroup" "data" "datalist"
    "dd" "del" "details" "dfn" "dialog" "div" "dl" "dt" "em" "embed" "fieldset" "figcaption"
    "figure" "footer" "form" "h1" "h2" "h3" "h4" "h5" "h6" "head" "header" "hr" "html"
    "i" "iframe" "img" "ins" "kbd" "keygen" "label" "legend" "li" "link" "main" "map" "mark"
    "menu" "menuitem" "meta" "meter" "nav" "noscript" "object" "ol" "optgroup" "output"
    "p" "param" "picture" "pre" "progress" "q" "rp" "rt" "ruby" "s" "samp" "script" "section"
    "small" "source" "span" "strong" "style" "sub" "summary" "sup" "table" "tbody" "td"
    "tfoot" "th" "thead" "time" "title" "tr" "track" "u" "ul" "var" "video" "wbr"
    ;; svg
    "circle" "clipPath" "ellipse" "g" "line" "mask" "path" "pattern" "polyline" "rect"
    "svg" "text" "defs" "linearGradient" "polygon" "radialGradient" "stop" "tspan"})

(defn create-element
  [onion-html? element]
  (let [element-name# (clj-str/replace element #"[A-Z]" #(str "-" (clj-str/lower-case %)))
        name-ending# "-wrapper"
        element# (if onion-html?
                   (symbol element-name#)
                   (symbol (str element-name# name-ending#)))]
    `(defn ~(symbol element#)
       [~'attributes ~'body]
       ~(case env/WRAPPER_LIBRARY
          "reagent" (if onion-html?
                      `(~(symbol "onion.reagent.html" (str element# name-ending#)) ~'attributes ~'body)
                      (r-html/html element# 'attributes 'body))
          "om-next" (if (om-next-list element)
                      (if onion-html?
                        `(~(symbol "onion.om-next.html" (str element# name-ending#)) ~'attributes ~'body)
                        (on-html/html element 'attributes 'body))
                      `(throw (~'js/Error ~(str element " not supported by om-next"))))
         :else :foo))))

(defn html [onion-html?]
  `(doall
    ~(map #(create-element onion-html? %)
          [;html
           "a" "abbr" "acronym" "address" "applet" "area" "article" "aside" "audio" "b" "base"
           "basefont" "bdi" "bdo" "bgsound" "big" "blink" "blockquote" "body" "br" "button"
           "canvas" "caption" "center" "cite" "code" "col" "colgroup" "command" "content" "data"
           "datalist" "dd" "del" "details" "dfn" "dialog" "dir" "div" "dl" "dt" "element" "em"
           "embed" "fieldset" "figcaption" "figure" "font" "footer" "form" "frame" "frameset" "h1"
           "head" "header" "hgroup" "hr" "html" "i" "iframe" "image" "img" "input" "ins" "isindex"
           "kbd" "keygen" "label" "legend" "li" "link" "listing" "main" "map" "mark" "marquee" "menu"
           "menuitem" "meta" "meter" "multicol" "nav" "nextid" "nobr" "noembed" "noframes" "noscript"
           "object" "ol" "optgroup" "option" "output" "p" "param" "picture" "plaintext" "pre"
           "progress" "q" "rb" "rp" "rt" "rtc" "ruby" "s" "samp" "script" "section" "select"
           "shadow" "slot" "small" "source" "spacer" "span" "strike" "strong" "style" "sub"
           "summary" "sup" "table" "tbody" "td" "template" "textarea" "tfoot" "th" "thead" "time"
           "title" "tr" "track" "tt" "u" "ul" "var" "video" "wbr" "xmp"                              ;svg
           "altGlyph" "altGlyphDef" "altGlyphItem" "animate" "animateColor" "animateMotion"
           "animateTransform" "circle" "clipPath" "color-profile" "cursor" "defs" "desc" "ellipse" "feBlend"
           "feColorMatrix" "feComponentTransfer" "feComposite" "feConvolveMatrix"
           "feDiffuseLighting" "feDisplacementMap" "feDistantLight" "feFlood" "feFuncA" "feFuncB" "feFuncG" "feFuncR"
           "feGaussianBlur" "feImage" "feMerge" "feMergeNode" "feMorphology"
           "feOffset" "fePointLight" "feSpecularLighting" "feSpotLight"
           "feTile" "feTurbulence" "filter"
                                        ; "font" <--- Already defined
           "font-face" "font-face-format"
           "font-face-name" "font-face-src" "font-face-uri" "foreignObject" "g" "glyph" "glyphRef"
           "hkern"
                                        ; "image" <--- Already defined
           "line" "linearGradient" "marker" "mask" "metadata"
           "missing-glyph" "mpath" "path" "pattern" "polygon" "polyline" "radialGradient"
           "rect"
                                        ;"script" <--- Already defined
           "set" "stop"

                                        ;"style" <--- Already defined
           "svg" "switch" "symbol"
           "text" "textPath"
                                        ;"title" <--- Already defined
           "tref" "tspan" "use" "view" "vkern"])))

(defmacro html-elements
  ([onion-html?] (html onion-html?))
  ([onion-html? wrapper-library]
   (case env/WRAPPER_LIBRARY
     "reagent" (when (= wrapper-library "reagent")
                 (html onion-html?))
     "om-next" (when (= wrapper-library "om-next")
                 `(do
                  #_(require '~'om.dom)
                  ~(html onion-html?)))
     :else :foo)))
