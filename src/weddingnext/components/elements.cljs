(ns
    weddingnext.components.elements
    (:require
     [weddingnext.assets.colors :as colors]
     [re-frame.core :as rf]))

(defn
  devider
  []
  [:hr.m-2
   {:style {:color colors/hit-pink
            :border-bottom "1px"
            :border-top "2px solid"}}])


(defn default-text [& hiccup]
  (into [:div.w-30.p-1] hiccup))

(default-text [:p "foooo"])
