(ns onion-flashy-table.core
  (:require [onion.html :as html]
            [onion.api :as api]
            [reagent.core])
  (:require-macros [onion.core :as om]))

(om/defelement cell-input
  [cell-value on-keydown-fn change-colour!]
  [hande-keydown! (fn [e]
                    (.preventDefault e)
                    (on-keydown-fn (.. e -target -value))
                    (change-colour!))]
  nil
  nil
  (html/input {:type "text"
               :on-keydown handle-keydown!
               :value cell-value}))

(om/defelement flashy-table
  [rows on-keydown-fn]
  [change-colour! (fn [this i j n-rows n-columns n]
                   (let [current-colours (api/get-state this)
                         new-colours (for [row-i (range n-rows)]
                                       (for [row-j (range n-columns)]
                                         (cond
                                           (or (= row-i (+ i n))
                                               (= row-i (- i n))
                                               (= row-j (+ j n))
                                               (= row-j (- j n)))
                                           (-> (get-in current-colours
                                                       [i j]
                                                       {:r 255 :g 255 :b 255})
                                               (update :r #(max (- % (* 5 n)) 0)
                                                       :g #(max (- % (* 10 n)) 0)
                                                       :b #(max (- % (* 15 n)) 0)))
                                           (or (= row-i (+ i (dec n)))
                                               (= row-i (- i (dec n)))
                                               (= row-j (+ j (dec n)))
                                               (= row-j (- j (dec n))))
                                           (-> (get-in current-colours
                                                       [i j]
                                                       {:r 255 :g 255 :b 255})
                                               (update :r #(min (+ % (* 5 n)) 255)
                                                       :g #(min (+ % (* 10 n)) 255)
                                                       :b #(min (+ % (* 15 n)) 255)))
                                           :else nil)))]
                     (api/set-state! this new-colours)))]
  []
  {:component-did-mount (fn [this]
                          )}
  (html/table
   nil
   (map-indexed (fn [i row]
                  (html/tr
                   nil
                   (map-indexed (fn [j cell-value]
                                  (let [cell-colour (get-in (api/get-state this)
                                                            [i j]
                                                            {:r 255 :g 255 :b 255})]
                                    (html/td
                                     {:style {:background-color (str "rgb("
                                                                     (apply str
                                                                            (interleave ", "
                                                                                        [(:r cell-colour)
                                                                                         (:g cell-colour)
                                                                                         (:b cell-colour)]))
                                                                     ")")}}
                                     (api/use-element cell-input cell-value on-keydown-fn (partial change-colour! this i j (count rows) (count row))))))
                                row)))
                rows)))
