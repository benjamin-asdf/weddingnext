(ns
    weddingnext.components.bakers
    (:require
     [re-frame.core :as rf]
     [weddingnext.utils :as u]
     [weddingnext.specs :as ws]
     [weddingnext.say-num
      :as
      say-num]
     [weddingnext.components.elements
      :as
      elms]
     [weddingnext.components.input-field-with-btn
      :refer
      [answer-input]]))

(rf/reg-sub
 ::answer
 (fn [db]
   (::answer db)))

(defn
  correct?
  [answer]
  (#{"90" "neunzig"} (u/s-sanitize answer)))

(defn
  bakers
  []
  (let [answer @(rf/subscribe [::answer])]
    [:div.w-30.p-1
     [:p
      "Nun bist du in einer Hochzeitsbäkerei. "
      "Tüchtige Bäker*innen backen geschaeftig. "
      "überall türmen sich die Mehlsäcke, Nüsse und karamellisierte Früchte"]
     [elms/devider]
     [:p
      "Neun Bäcker brauchen 90 Minuten für neun Hochzeitstorten. "
      "Wie lange dauert es für 24 Bäcker, 24 Torten zu backen?"]
     [:div
      [answer-input
       answer
       (fn
         [e]
         (let [answer (-> e .-target .-value)]
           (rf/dispatch
            [::update-answer answer])))]
      "Minuten"
      [:button.btn.ml-5
       ;; todo fix ui
       {:style elms/default-button-style
        :on-click
        (fn
          [_]
          (rf/dispatch [::submit]))}
       "ok"]]]))

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
                 :page/bakers-result)
                correct?
                (assoc ::correct? true))]
     (cond->
         {:db db}
         say-num?
         (assoc ::say-num/say-num? say-num?)))))

(comment
  (reset!
   re-frame.db/app-db
   weddingnext.db/init-db)
  [lake]
  (swap!
   re-frame.db/app-db
   assoc
   ::ws/page
   :page/lake)
   ;; :page/bakers
  :page/lake)
