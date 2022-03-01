(ns
    weddingnext.components.elements
    (:require
     [re-frame.core :as rf]))

(defn
  devider
  []
  [:hr.m-2
   {:style {:color "#feb48f"
            :border-bottom "1px"
            :border-top "2px solid"}}])


(defn default-text [& hiccup]
  (into [:div.w-30.p-1] hiccup))

(default-text [:p "foooo"])
