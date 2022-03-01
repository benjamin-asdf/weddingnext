(ns
    weddingnext.components.door
    (:require
     [weddingnext.specs :as ws]
     [weddingnext.components.input-field-with-btn :refer [input-field-and-btn]]
     [re-frame.core :as rf]))

(defn
  correct?
  [answer]
  (#{"merle" "katja"}
   (.trim
    (.toLowerCase
     answer))))

(rf/reg-sub
 ::counter
 (fn [db]
   (::counter db)))

(rf/reg-sub
 ::answer
 (fn [db]
   (::answer db)))

(defn
  door
  []
  (let [input @(rf/subscribe [::answer])]
    [:h1.w-30
     "Willkommen"
     [:div
      [:p.mb-4
       "Dein magischer Stein zeigt dir eine maechtige Tuere. "
       "Im Stein sind Buchstaben eingraviert, deren strahlen "
       "dich an Sternenlich erinnert. "
       "Es is ein Raetsel. "]
      [:div.mt-1
       {:class "space-y-2 m-0.5"
        :style {:color "#6da2bc"
                :text-align :center
                :margin-top "1vw"
                :border-color "#feb48f"
                :border-style "double"
                :border-width "10px"}}
       [:p.text-2xl
        {:class "mt-3"}
        "Sprich Kind und tritt ein"]
       (input-field-and-btn
        input
        {:on-change (fn
                      [e]
                      (let [answer (-> e .-target .-value)]
                        (rf/dispatch
                         [::update-answer answer])))
         :on-click (fn
                     [_]
                     (rf/dispatch [::submit]))})]]]))

(defn
  submit-h
  [{::keys [answer] :as db}]
  (let [db (update db ::counter inc)
        good-answer? (or
                      (> (::counter db) 2)
                      (correct? answer))]
    (cond->
        db
        good-answer?
        (assoc ::ws/page :page/lake))))

(rf/reg-event-db
 ::submit
 (fn [db _]
   (submit-h db)))

(rf/reg-event-db
 ::update-answer
 (fn [db [_ s]]
   (assoc db ::answer s)))


(comment
  @re-frame.db/app-db
  (rf/dispatch [::submit])
  (->  (. js/document querySelector ":root" ) )

  (set! (..  (. js/document querySelector ":root") -style -fontSize) )

  (swap! re-frame.db/app-db assoc ::ws/page :page/door)
  (reset! re-frame.db/app-db weddingnext.db/init-db)
  )
