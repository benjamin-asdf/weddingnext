(ns
    weddingnext.components.elements
    (:require
     [weddingnext.assets.colors :as colors]
     [re-frame.core :as rf]))

(defn
  devider
  []
  [:hr.m-2
   {:style
    {:color colors/hit-pink
     :border-bottom "1px"
     :border-top "2px solid"}}])


(defn default-text [& hiccup]
  (into [:div.w-30.p-1] hiccup))

(def
  default-button-style
  {:color colors/heliotrope
   :background-color colors/woodsmoke-tint-1})

(defn
  button
  [on-click]
  [:button.btn.p-1
   {:style
    default-button-style
    :on-click on-click}
   [:p "ok"]])

(comment
  (default-text [:p "foooo"]))
