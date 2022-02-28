(ns
    weddingnext.components.door
    (:require
     [weddingnext.specs :as ws]
     [re-frame.core :as rf]))

(defn
  answer-input
  [input]
  [:input
   {:style {:margin-bottom "5vw"
            :color "#F689FF"
            :background-color "#2e2d35"}
    :type "text"
    :value input
    :on-change (fn
                 [e]
                 (let [answer (-> e .-target .-value)]
                   (rf/dispatch
                    [::update-answer answer])))}])

(defn
  correct?
  [answer]
  (#{"merle" "katja"}
   (.toLowerCase answer)))

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

(defn
  door
  []
  (let [input @(rf/subscribe [::answer])]
    [:h1
     "Willkommen"
     [:div
      [:p
       {:style {:font-size "1.2vw"
                :width "800px"}}
       "Dein magischer Stein zeigt dir eine maechtige Tuere. "
       "Im Stein sind Buchstaben eingraviert, deren strahlen "
       "dich an Sternenlich erinnert. "
       "Es is ein Raetsel. "]
      [:div.absolute
       {:style {:color "#6da2bc"
                :text-align :center
                :margin-top "1vw"
                :border-color "#feb48f"
                :border-style "double"
                :border-width "10px"}}
       [:p
        {:style {:margin "10vw"
                 :font-size "4vw"
                 :border "10px"}}
        "Sprich Kind und tritt ein"]
       [:div
        [answer-input input]
        [:button.btn
         {:style
          {:margin "0.8vw"
           :width "4vw"
           :color "#F689FF"
           :background-color "#2e2d35"}
          :on-click (fn
                      [_]
                      (rf/dispatch [::submit]))}
         "submit"]]]]]))

(rf/reg-sub
 ::counter
 (fn [db]
   (::counter db)))

(rf/reg-sub
 ::answer
 (fn [db]
   (::answer db)))

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
  (swap! re-frame.db/app-db assoc ::ws/page :page/door))
