(ns weddingnext.components.input-field-with-btn
  (:require
   [re-frame.core :as rf]))

(defn
  answer-input
  [input on-change]
  [:input.m-3.w-16.h-8.p-1
   {:style
    {:color "#F689FF"
     :background-color "#2e2d35"}
    :type "text"
    :value input
    :on-change on-change}])

(defn
  input-field-and-btn
  [input {:keys [on-change on-click]}]
  [:div
   [answer-input input on-change]
   [:button.btn.p-1
    {:style {:color "#F689FF"
             :background-color "#2e2d35"}
     :on-click on-click}
    [:p "ok"]]])
