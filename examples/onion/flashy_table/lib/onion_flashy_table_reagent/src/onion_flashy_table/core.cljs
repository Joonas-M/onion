(ns onion-flashy-table.core
  (:require [onion.html :as html]
            [onion.api :as api]
            [reagent.core])
  (:require-macros [onion.core :as om]))

(om/defelement cell-input
  [cell-value i j on-keydown-fn change-colour!]
  [handle-change! (fn [this e]
                    (.preventDefault e)
                    (js/console.log (str "VALUE: " (.. e -target -value)))
                    (on-keydown-fn (.. e -target -value) [j i])
                    (change-colour! (api/get-state this))
                    (api/update-state! this inc))]
  1
  nil
  (html/input {:type "text"
               :style {:border "none"
                       :background-color "rgba(255,255,255,0)"}
               :onChange (partial handle-change! this)
               :value cell-value}))

(om/defelement flashy-table
  [rows on-keydown-fn]
  [change-colour! (fn [this i j n-rows n-columns n]
                    (js/console.log [i j n-rows n-columns n])
                    
                    (let [current-colours (api/get-state this)
                          _ (js/console.log current-colours)
                          new-colours (vec (for [row-i (range n-columns)]
                                             (vec (for [row-j (range n-rows)]
                                                    (cond
                                                      (or (and
                                                           (or (= row-i (+ i n))
                                                               (= row-i (- i n)))
                                                           (<= (- j n) row-j (+ j n)))
                                                          (and
                                                           (or (= row-j (+ j n))
                                                               (= row-j (- j n)))
                                                           (<= (- i n) row-i (+ i n))))
                                                      (-> (get-in current-colours
                                                                  [j i]
                                                                  {:r 255 :g 255 :b 255})
                                                          (update :r #(max (- % (* 40 n)) 0)
                                                                  :g #(max (- % (* 60 n)) 0)
                                                                  :b #(max (- % (* 80 n)) 0)))
                                                      (or (and
                                                           (or (= row-i (+ i (dec n)))
                                                               (= row-i (- i (dec n))))
                                                           (<= (- j (dec n)) row-j (+ j (dec n))))
                                                          (and
                                                           (or (= row-j (+ j (dec n)))
                                                               (= row-j (- j (dec n))))
                                                           (<= (- i (dec n)) row-i (+ i (dec n)))))
                                                      (-> (get-in current-colours
                                                                  [j i]
                                                                  {:r 255 :g 255 :b 255})
                                                          (update :r #(min (+ % (* 40 n)) 255)
                                                                  :g #(min (+ % (* 60 n)) 255)
                                                                  :b #(min (+ % (* 80 n)) 255)))
                                                      :else nil)))))]
                     (js/console.log "NEW COLOURS: ")
                     (js/console.log  (str (into [] new-colours)))
                     (api/set-state! this new-colours)))]
  []
  {:component-did-mount (fn [this]
                          )}
  (html/table
   nil
   (html/tbody nil
               (doall
                (map-indexed (fn [j row]
                               (html/tr {:react-key j}
                                        (map-indexed (fn [i cell-value]
                                                         (let [cell-colour (get-in (api/get-state this)
                                                                                   [j i]
                                                                                   {:r 255 :g 255 :b 255})]
                                                           (js/console.log "COLOUR: " (str "rgb("
                                                                                            (apply str
                                                                                                   (interpose ", "
                                                                                                               [(:r cell-colour)
                                                                                                                (:g cell-colour)
                                                                                                                (:b cell-colour)]))
                                                                                            ")"))
                                                           (html/td
                                                            {:react-key i
                                                             :style {:background-color (str "rgb("
                                                                                            (apply str
                                                                                                   (interpose ", "
                                                                                                               [(:r cell-colour)
                                                                                                                (:g cell-colour)
                                                                                                                (:b cell-colour)]))
                                                                                            ")")}}
                                                            (api/use-element cell-input cell-value i j on-keydown-fn (partial change-colour! this i j (count rows) (count row))))))
                                                       row)))
                             rows)))))
