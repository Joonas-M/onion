(ns onion.html
  (:require [onion.reagent.html :as r-html]
            [onion.om-next.html :as on-html]
            [clojure.string :as clj-str]
            [onion.env :as env]))

(def all-elements
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
   "title" "tr" "track" "tt" "u" "ul" "var" "video" "wbr" "xmp"
   ;svg
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
   "tref" "tspan" "use" "view" "vkern"])

(def void-elements
  #{"area" "base" "br" "col" "embed" "hr" "img" "input" "link" "meta" "param" "source" "track" "wbr"})

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
  [element]
  (let [element# (clj-str/replace element #"[A-Z]" #(str "-" (clj-str/lower-case %)))
        void-element?# (void-elements element)
        attributes_ (gensym "attributes")
        body_ (gensym "body")]
    `(defn ~(symbol element#)
       ~(if void-element?#
          [attributes_]
          [attributes_ body_])
       ~(case env/WRAPPER_LIBRARY
          "reagent" (r-html/html element# void-element?# attributes_ body_)
          "om-next" (if (om-next-list element)
                      (on-html/html element void-element?# attributes_ body_)
                      `(throw (~'js/Error ~(str element " not supported by om-next"))))
         :else :foo))))

(defmacro html-elements []
  (for [element all-elements]
    (create-element element)))
