(ns
    weddingnext.components.lake
    (:require-macros
     [weddingnext.slurp
      :refer
      [slurp]])
    (:require
     [re-frame.core :as rf]
     [weddingnext.say-num :as say-num]
     [weddingnext.utils :as u]
     [weddingnext.specs :as ws]
     [weddingnext.components.elements :as elms]
     [weddingnext.components.input-field-with-btn
      :refer
      [input-field-and-btn]]))
(defn
  correct?
  [answer]
  (#{"29"} (u/s-sanitize answer)))

(def asci
  (slurp "public/art/lake"))

(rf/reg-sub
 ::answer
 (fn [db]
   (::answer db)))

(defn
  lake
  []
  (let [answer @(rf/subscribe [::answer])]
    [:div.w-30.p-1
     [:p
      "Du kommst zu einem See. Du hörst sanftes Plätschern."
      " Eine Seerose "
      "schwimmt auf der Oberfläche. "
      "Du stellst dir die Frage:"]
     [elms/devider]
     [:p
      "Wenn sich die Fläche der Seerosen jeden Tag verdoppelt "
      "und nach 30 Tagen der See komplett bedeckt ist, "
      "an welchem Tag ist die Hälfte des Sees bedeckt? "]
     [elms/devider]
     [:pre.font-monospace.mt-10.mb-10
      {:style {:font-size "0.1rem"
               :line-height "0.15rem"}}
      asci]
     [:div.p-0.ml-10
      (input-field-and-btn
       answer
       {:on-change (fn
                     [e]
                     (let [answer (-> e .-target .-value)]
                       (rf/dispatch
                        [::update-answer answer])))
        :on-click (fn [_] (rf/dispatch [::submit]))})]]))

(rf/reg-event-db
 ::update-answer
 (fn [db [_ s]]
   (assoc db ::answer s)))

(def
  classify-answer
  (say-num/classifier
   ::classify-answer
   ::answer
   ::correct?
   correct?))

(def
  interceptor
  (say-num/say-num-interceptor
   (fn [db] (::answer db))))

(rf/reg-event-fx
 ::submit
 [interceptor
  classify-answer]
 (fn
   [{:keys [db ::correct? ::say-num/say-num?]} _]
   (let [db (cond->
                db
                (not say-num?)
                (assoc
                 ::ws/page
                 :page/lake-result)
                correct?
                (assoc ::correct? true))]
     (cond->
         {:db db}
         say-num?
         (assoc ::say-num/say-num? say-num?)))))

(comment
  (rf/dispatch [::submit "fo"])
  (require '[re-frame.db])
  (require '[weddingnext.db])
  (rf/dispatch [::submit])
  (::answer @re-frame.db/app-db)
  (reset!
   re-frame.db/app-db
   weddingnext.db/init-db)
  (swap!
   re-frame.db/app-db
   assoc
   ::ws/page
   :page/lake)

)
