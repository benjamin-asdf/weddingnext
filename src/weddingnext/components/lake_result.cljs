(ns
    weddingnext.components.lake-result
    (:require
     [re-frame.core :as rf]
     [weddingnext.specs :as ws]
     [weddingnext.components.elements :as elms]
     [weddingnext.components.input-field-with-btn
      :refer
      [input-field-and-btn]]))


(defn lake-result []
  [:p "Die Antwort ist"]
  [:div.w-30.p-4.m-2
   [:p
    "Die Antwort ist "]
   [:p.text-xl.
    "29"]

   [elms/devider]])
