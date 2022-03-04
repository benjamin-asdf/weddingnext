(ns
    weddingnext.components.lake-result
  (:require
   [re-frame.core :as rf]
   [reagent.core :as r]
   [weddingnext.specs :as ws]
   [weddingnext.components.elements :as elms]
   [weddingnext.assets.colors :as colors]))

(rf/reg-sub
 :weddingnext.components.lake/correct?
 (fn
   [db]
   (:weddingnext.components.lake/correct?
    db)))

(defn
  lake-result*
  []
  (let [correct? @(rf/subscribe
                   [:weddingnext.components.lake/correct?])]
    [:div
     [:div.w-30.m-2.p-2
      (if
          correct?
          [:div
           {:style {:color colors/mint-green}}
           "29."]
          [:p
           "Die Antwort ist "
           [:p
            {:style {:color colors/heliotrope}}
            "29."]])
      [:p
       "Ein Tag bevor der See komplett bedeckt ist."]
      [:div.pt-2 [elms/devider]]
      [:div.mb-2
       [:pre
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
■■■■■■■■■■ 25"]
       [:pre
        {:style {:font-size "0.45rem"
                 :color (if
                            correct?
                            colors/mint-green
                            colors/heliotrope)}}
        "■■■■■■■■■■■■■■■■■■■■ 50"]
       [:pre
        {:style {:font-size "0.45rem"}}
        "■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 100 "]]
      [:btn.btn.mr-4
       {:on-click
        (fn [] (rf/dispatch [::click]))}
       "krass"]]]))
(defn
  lake-result
  []
  (r/create-class
   {:component-did-mount
    (fn
      []
      (js/window.scrollTo 0 0))
    :reagent-render lake-result*}))

(rf/reg-event-db
 ::click
 (fn [db _]
   (assoc db ::ws/page :page/bakers)))

(comment
  10
  (reset!
   re-frame.db/app-db
   (assoc
    weddingnext.db/init-db
    :weddingnext.components.lake/correct?
    true
    ::ws/page
    :page/lake-result)))
