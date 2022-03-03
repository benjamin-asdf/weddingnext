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
    [:h1.w-30
     "Willkommen"
     [:div
      [:p.mb-4
       "Dein magischer Stein zeigt dir eine maechtige Tuere. "
       "Im Stein sind Buchstaben eingraviert, deren strahlen "
       "dich an Sternenlicht erinnert. "
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
       [input-field-and-btn
        input
        {:on-change (fn
                      [e]
                      (let [answer (-> e .-target .-value)]
                        (rf/dispatch
                         [::update-answer answer])))
         :on-click (fn
                     [_]
                     (rf/dispatch [::submit]))}]]]]))

(rf/reg-event-db
 ::submit
 (fn [db _]
   (submit-h db)))

(rf/reg-event-db
 ::update-answer
 (fn [db [_ s]]
   (assoc db ::answer s)))

;; (defn ^:dev/before-load foo [] (rf/console :log "hi"))


(comment
  @re-frame.db/app-db
  (rf/dispatch [::submit])
  (->
   (.
    js/document
    querySelector
    ":root"))
  (set!
   (..
    (.
     js/document
     querySelector
     ":root")
    -style
    -fontSize))
  (swap!
   re-frame.db/app-db
   assoc
   ::ws/page
   :page/door)
  (reset!
   re-frame.db/app-db
   weddingnext.db/init-db)
  (. js/document getElement)
  (set!
   (..
    (.getElementById
     js/document
     "foo")
    -style
    -transform)
   "100px")
  (set!
   (..
    (.getElementById
     js/document
     "foo")
    -style
    -transform)
   "translateX(500px)")
  (.-id
   (js/document.getElementById
    (name ::ball-container)))
  (let [container (.getElementById
                   js/document
                   (name ::ball-container))
        wrap (js/document.createElement
              "div")
        ball (js/document.createElement
              "div")
        x (rand-int 500)
        y (rand-int 500)]
    ;; (set! (.-className wrap) "ballwrap")
    (.appendChild wrap ball)
    (.appendChild container wrap)
    (set!
     (.-className wrap)
     "slideInRight face")
    ;; (set! (.-className ball) "ball")
    ;; (set! (.. wrap -style -transform)
    ;;       (str "translateX(" x "px)"))
    ;; (set! (.. wrap -style -top)
    ;;       (str (rand-int 500) "px"))
    ;; (set! (.. wrap -style -left)
    ;;       (str (rand-int 500) "px"))
    ;; (set! (.. ball -style -transform)
    ;;       (str "translateY(" y "px)"))
    ;; (set! (.. wrap -style -transform)
    ;;       (str "translateX(" (rand-int 500) "px)"))
    )
  (set!
   (..
    (.getElementById
     js/document
     (name ::face))
    -style
    -transform)
   ;; "translate3d(100%, 0, 0)"
   "translate3d(0, 0, 0)"
   ;; "translateX(-19px)"
   )
  (set!
   (..
    (.getElementById
     js/document
     (name ::face))
    -style
    -transform)
   ;; "translate3d(100%, 0, 0)"
   ;; "translate3d(0, 0, 0)"
   "translateX(-600px)"))
