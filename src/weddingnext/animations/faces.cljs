(ns weddingnext.animations.faces
  (:require
   [cljs.core.async :as a]
   [re-frame.core :as rf]))

(defonce click-listener (atom nil))
(defonce eventd (atom nil))

(defn mount []
  (let [listener
        (fn [ev]
          (reset! eventd ev)
          (rf/dispatch [::click [(.-clientX ev)
                                 (.-clientY ev)]]))]
    (js/document.addEventListener "click" listener)
    (reset! click-listener listener)))

(defn
  unmount
  []
  (rf/console :log @click-listener)
  (js/document.removeEventListener
   "click"
   @click-listener))

(defn face []

  )

(rf/reg-fx
 ::click
 (fn [value]
   {::make-face (second value)}))


(rf/reg-event-fx
 ::click
 (fn [{:keys [event]}]

   {::make-face
    (peek event)}))

(defn
  get-face
  []
  (.getElementById
   js/document
   "face"))

(def
  position
  {:right {:x 600 :y 500}
   :left {:x -100 :y 500}
   :top {:x 150 :y -100}
   :bottom {:x 150 :y 800}})

(defn
  reset-face
  [face]
  (set! (.. face -style -visibility) "hidden")
  (set! (.. face -style -top) "200px")
  (set! (.. face -style -transform) "translateX(600px)")
  ;; (set! (.. face -style -left) "600px")
  )

(defn wiggle [face])

(comment
  (reset-face (get-face))

  (set!
   (..
    (get-face)
    -style
    -transform)
   "translateY(+2000%)")
  (set!
   (..
    (get-face)
    -style
    -visibility)
   "hidden"
   ;; "visible"
   )


  )

(rf/reg-fx
 ::make-face
 (fn
   [[x y]]
   (let [face (get-face)]
     ;; (set!
     ;;  (.. face -style -transform)
     ;;  "translateX(-600px)")
     (set!
      (.. face -style -transform)
      "translateY(+300px)")
     (set!
      (.. face -style -visibility)
      "visible")
     ;; (a/go
     ;;   (a/<! (a/timeout 1000))
     ;;   (rf/console :log "hi")
     ;;   (set! (.-visibility (.-style face)) "hidden")

     ;;   ;; (let [face (get-face)]
     ;;   ;;   (set! (.. face -style -visibility) "hidden"))
     ;;   )

     )))



;; make it come in from 1 side
;; rand pos
;; then wiggle
;; move back..?

(comment


  (let [freqs [1 2 3]]
    (map
     (fn [freq is] (map + freq is))
     (repeat 3 freqs)
     (into
      []
      (map-indexed
       (fn
         [i _]
         (assoc
           (into [] (repeat 3 0))
           i
           1))
       (range 3)))))

  (map + ;; (fn [i j] (+ i j))
       [1 0] [0 2])

  (let [i 0]
    (assoc (into [] (repeat 3 0)) i 1))

  )
