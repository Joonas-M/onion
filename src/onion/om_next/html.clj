(ns onion.om-next.html
  (:require [onion.env :as env]))

(defn html
  [element attributes body]
  `(~(symbol "om.dom" (str element)) (~'clj->js ~attributes) ~body))

(defn create-element [element]
  `(defn ~(symbol (str element "-wrapper"))
     [~'attributes ~'body]
     (`~'~(str "onion.om-next.html-inner/" element) ~'attributes ~'body)))

(defmacro fns []
  `(doall
    ~(map create-element
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
