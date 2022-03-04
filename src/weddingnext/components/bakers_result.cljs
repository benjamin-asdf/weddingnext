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
  (let [correct? @(rf/subscribe [::bakers/correct?])]
    [:div.w-30.p-1
     (if correct?
       "Richtig! - "
       "Die richtige Antwort is 90. ")
     "Jeder Bäcker braucht 90 minuten fuer eine Torte."]))

(comment
  (reset!
   re-frame.db/app-db
   weddingnext.db/init-db)
  (swap!
   re-frame.db/app-db
   assoc
   ::ws/page
   :page/bakers-result
   ::bakers/correct? true)
  (::bakers/correct? @re-frame.db/app-db)
  )
