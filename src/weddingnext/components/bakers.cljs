(ns
    weddingnext.components.bakers
    (:require
     [re-frame.core :as rf]
     [weddingnext.specs :as ws]
     [weddingnext.components.elements
      :as
      elms]
     [weddingnext.components.input-field-with-btn
      :refer
      [input-field-and-btn]]))

(rf/reg-sub
 ::answer
 (fn [db]
   (::answer db)))


(defn
  bakers
  []
  (let [answer @(rf/subscribe [::answer])]
    [:div.w-30.p-1
     [:p
      "Nun bist du in einer Hochzeitzbärei. "
      "Tüchtige "
      "Mehl Früchte Schokoladenblöcke"
      "backen geschäftig."]
     [elms/devider]
     [:p
      "8 Bäcker brauchen 80 Minuten für 8 Hochzeitskuchen."
      "Wie lange dauert es für 24 Bäcker, 24 Kuchen zu backen?"]
     [:div.p-0.ml-5
      (input-field-and-btn
       answer
       {:on-change (fn
                     [e]
                     (let [answer (-> e .-target .-value)]
                       (rf/dispatch
                        [::update-answer answer])))
        :on-click (fn
                    [_]
                    (rf/dispatch [::submit]))})]]))


(comment
  (reset!
   re-frame.db/app-db
   weddingnext.db/init-db)
  (swap!
   re-frame.db/app-db
   assoc
   ::ws/page
   :page/bakers)
  :page/lake
  [lake])
