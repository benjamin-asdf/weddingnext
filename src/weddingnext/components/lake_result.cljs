(ns
    weddingnext.components.lake-result
    (:require
     [re-frame.core :as rf]
     [weddingnext.specs :as ws]
     [weddingnext.components.elements :as elms]
     [weddingnext.assets.colors :as colors]
     [weddingnext.components.input-field-with-btn
      :refer
      [input-field-and-btn]]))

(defn lake-result []
  [:p "Die Antwort ist"]
  [:div.w-30.m-2.p-2
   [:p.p-2
    "Die Antwort ist "]
   [:p.text-xl.ml-2.pt-2
    {:style {:font-size "3rem"
             :line-height "2rem"
             :color colors/heliotrope}}
    " 29 "]
   [:p.p-2
    "ein Tag bevor der See komplett bedeckt ist."]
   [:div.pt-2
    [elms/devider]]])
