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
   [:p
    "Die Antwort ist 29."]
   ;; [:p.text-xl
   ;;  ;; {:style {:color colors/heliotrope}}
   ;;  " 29 "]
   [:p
    "ein Tag bevor der See komplett bedeckt ist."]
   [:div.pt-2
    [elms/devider]]
   [:pre.mb-2
    {:style {:font-size "0.45rem"}}
    "
🞌
🞌
🞌
🞌
🞌
🞌
🞌
🞌
🞌
🞌
🞌
🞌
🞌
🞌
🞌
🞌
🞌
🞌
🞌
🞌
🞌
🞌
🞌
■
■ 3
■■ 6
■■■■■ 12
■■■■■■■■■■ 25
■■■■■■■■■■■■■■■■■■■■ 50
■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 100 "]
   [:btn.btn.mr-4 "Krass"]])
