(ns weddingnext.components.input-field-with-btn
  (:require
   [weddingnext.assets.colors :as colors]
   [weddingnext.components.elements
    :as elms]
   [re-frame.core :as rf]))

(defn
  answer-input
  [input on-change]
  [:input.m-3.w-16.h-8.p-1.border-solid.border-2
   {:style
    {:color colors/heliotrope
     :border-color colors/heliotrope
     :background-color colors/woodsmoke-tint-1}
    :type "text"
    :value input
    :on-change on-change}])

(defn
  input-field-and-btn
  [input {:keys [on-change on-click]}]
  [:div
   [answer-input input on-change]
   [elms/button on-click]])
