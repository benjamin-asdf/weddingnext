(ns
    weddingnext.components.cake
    (:require
     [weddingnext.say-num
      :as
      say-num]
     [weddingnext.utils :as u]
     [weddingnext.specs :as ws]
     [weddingnext.components.input-field-with-btn
      :refer
      [input-field-and-btn]]
     [re-frame.core :as rf]))

(rf/reg-sub
 ::answer
 (fn [db]
   (::answer db)))

(defn
  correct?
  [answer]
  (#{"5"} (u/s-sanitize answer)))

(defn input []
  (let [answer @(rf/subscribe [::answer])]
    [input-field-and-btn
     answer
     {:on-change (fn
                   [e]
                   (let [answer (-> e .-target .-value)]
                     (rf/dispatch
                      [::update-answer answer])))
      :on-click
      (fn [_] (rf/dispatch [::submit]))}]))

(defn
  cake
  []
  [:div.w-30.p-1
   "Ein Stück von Opa Walters Apfel-Streusel-Kuchen wiegt 110 Gramm."
   " Die Äpfel wiegen 100 Gramm mehr als die Streusel."
   " Wieviel Gramm wiegen die Streusel?"
   [input]])

(rf/reg-event-db
 ::update-answer
 (fn [db [_ s]]
   (assoc db ::answer s)))

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
  (say-num/say-num-interceptor ::answer))

(rf/reg-event-fx
 ::submit
 [interceptor classify-answer]
 (fn
   [{:keys [db
            ::correct?
            ::say-num/say-num?]}
    _]
   (let [db (cond->
                db
                (not say-num?)
                (assoc ::ws/page
                       :page/cake-result)
                correct?
                (assoc ::correct? true))]
     (cond->
         {:db db}
         say-num?
         (assoc
          ::say-num/say-num?
          say-num?)))))

(comment
  (rf/dispatch [::submit "fo"])
  (rf/dispatch [::submit])
  (::answer @re-frame.db/app-db)
  (reset!
   re-frame.db/app-db
   weddingnext.db/init-db)
  (swap!
   re-frame.db/app-db
   assoc
   ::ws/page
   :page/cake))
