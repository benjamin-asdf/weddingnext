(ns
    weddingnext.components.lake
    (:require-macros
     [weddingnext.slurp
      :refer
      [slurp]])
    (:require
     [re-frame.core :as rf]
     [weddingnext.specs :as ws]
     [weddingnext.components.elements :as elms]
     [weddingnext.components.input-field-with-btn
      :refer
      [input-field-and-btn]]))

(defn sanitize [s]
  (.. s toLowerCase trim))

(defn
  correct?
  [answer]
  (#{"29"} (sanitize answer)))

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
      (str
       "Du kommst zu einem See. Do hoerst sanftes Plaetschern."
       " Eine Seerose "
       "schwimmt auf der Oberflaeche. "
       "Du stells dir die Frage")]
     [elms/devider]
     [:p
      "Wenn sich die Flaeche der Seerosen jeden Tag verdoppelt "
      "und nach 30 Tagen der See komplett bedeckt ist, "
      "an welchem Tag ist die Healfte des Sees bedeckt? "]
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
  say-num-interceptor
  (rf/->interceptor
   :id ::say-num?
   :before
   (fn
     [context]
     (let [answer (::answer (rf/get-coeffect context :db))]
       (rf/assoc-coeffect
        context
        ::say-num?
        (when answer (js/isNaN answer)))))))
(def
  classify-answer
  (rf/->interceptor
   :id ::classify-answer
   :before
   (fn
     [context]
     (let [db (rf/get-coeffect context :db)
           answer (::answer db)]
       (cond->
           context
           (not (rf/get-coeffect context ::say-num?))
           (rf/assoc-coeffect
            ::correct?
            (correct? answer)))))))

(rf/reg-event-fx
 ::submit
 [say-num-interceptor classify-answer]
 (fn [{:keys [db ::correct? ::say-num?]} _]
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
         (assoc ::say-num say-num?)))))

(rf/reg-fx
 ::say-num
 (fn [_]
   (js/alert "Sage eine Zahl.")))

(comment
  (rf/dispatch [::submit "fo"])
  (::answer @re-frame.db/app-db)
  (reset! re-frame.db/app-db weddingnext.db/init-db)
  (swap! re-frame.db/app-db assoc ::ws/page :page/lake)
  (try
    (.parseInt "f")
    (catch nil))
  (rf/console :log (.parseInt "f")))
