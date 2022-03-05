(ns
    weddingnext.components.bakers-result
    (:require
     [weddingnext.components.bakers :as bakers]
     [weddingnext.components.elements
      :as
      elms]
     [weddingnext.specs :as ws]
     [re-frame.core :as rf]))

(rf/reg-sub
 ::bakers/correct?
 ::bakers/correct?)

(defn
  bakers-result
  []
  (let [correct? @(rf/subscribe
                   [::bakers/correct?])]
    [:<>
     [:div.w-30.p-1
      (if
          correct?
          "Richtig! "
          "Die richtige Antwort ist 90. ")
      "Jeder Bäcker braucht 90 Minuten für eine Torte."]
     [elms/devider]
     [:button.btn.p-1
      {:style (assoc
               elms/default-button-style
               :margin-left
               "1rem")
       :on-click (fn
                   [_]
                   (rf/dispatch [::click]))}
      [:p "Bitte weiter"]]]))

(rf/reg-event-db
 ::click
 (fn [db _]
   (assoc db ::ws/page :page/cake)))

(comment
  (reset!
   re-frame.db/app-db
   weddingnext.db/init-db)
  (swap!
   re-frame.db/app-db
   assoc
   ::ws/page
   :page/bakers-result
   ::bakers/correct? false)
  (::bakers/correct?
   @re-frame.db/app-db)
  )
