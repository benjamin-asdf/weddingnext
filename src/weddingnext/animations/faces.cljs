(ns weddingnext.animations.faces
  (:require
   [cljs.core.async :as a]
   [reagent.core :as r]
   [re-frame.core :as rf]))

(defonce click-listener (atom nil))
(defonce eventd (atom nil))
(defonce face-nodes (atom #{}))

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
  (reset! face-nodes #{})
  (js/document.removeEventListener
   "click"
   @click-listener))

;; (defn face [i]
;;   (r/create-class
;;    {:component-did-mount
;;     (fn [cmp]
;;       (let [node (reagent.dom/dom-node cmp)]
;;         (swap! face-nodes conj node)))
;;     :component-will-unmount
;;     (fn [cmp]
;;       (let [node (reagent.dom/dom-node cmp)]
;;         (swap! face-nodes disj node)))
;;     :reagent-render
;;     (fn []
;;       [:div.ball
;;        {:id (str :face- 0)}])}))

(defn face []
  (r/create-class
   {:component-did-mount
    (fn [cmp]
      (let [node (reagent.dom/dom-node cmp)]
        (swap! face-nodes conj node)))
    :component-will-unmount
    (fn [cmp]
      (let [node (reagent.dom/dom-node cmp)]
        (swap! face-nodes disj node)))
    :reagent-render
    (fn []
      [:div.face {:id "my-face"}])}))

(rf/reg-fx
 ::click
 (fn [value]
   {::make-face (second value)}))


;; (defn
;;   reset-face
;;   [face]
;;   (set! (.. face -style -visibility) "hidden")
;;   (set! (.. face -style -top) "200px")
;;   (set! (.. face -style -transform) "translateX(600px)")
;;   ;; (set! (.. face -style -left) "600px")
;;   )

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
  wiggle
  [face]
  (let [wiggle-back (fn
                      []
                      (set!
                       (.. face -style -transform)
                       "rotateZ(0deg)"))]
    (set!
     (.. face -style -transform)
     "rotateZ(45deg)")
    (set!
     (.. face -style -transition)
     "transform 0.2s")
    (a/go
      (a/<! (a/timeout 200))
      (wiggle-back))))

(defn reset-face [face]
  )

(comment
  (reset-face (get-face))

  (set!
   (..
    (get-face)
    -style
    -visibility)
   "hidden"
   ;; "visible"
   )
  (set!
   (..
    (get-face)
    -style
    -transform)
   "translateY(+2000%)")

  (set!
   (..
    (first @face-nodes)
    -style
    -background)
   "blue")

  (wiggle (first @face-nodes))

  (set!
   (..
    (first @face-nodes)
    -style
    -transform)
   "translateY(+2000%)")
  (set!
   (..
    (first @face-nodes)
    -style
    -transform)
   "translateY(+500%)")

  (a/go
    (set!
     (..
      (first @face-nodes)
      -style
      -transform)
     "translateY(+500%)")
    (set!
     (..
      (first @face-nodes)
      -style
      -transform)
     "rotateZ(45deg)")
    (set!
     (..
      (first @face-nodes)
      -style
      -transform)
     "rotateZ(0deg)")
    (a/<! (a/timeout 600))
    (set!
     (..
      (first @face-nodes)
      -style
      -transform) ""))

  (.remove (first @face-nodes))
  (set! (.-background (.-style (first @face-nodes))) "yellow")
  (set! (.-background (.-style (first @face-nodes))) "brown")
  (set! (.-color (.-style (first @face-nodes))) "yellow")

  (r/children (first @face-nodes))
  (set!
   (..
    (rf/console :log (first @face-nodes))
    -style
    -border-radius "25px"))

  (doseq [face @face-nodes]
    (set! (.. face -style -top) "100px")
    (set! (.. face -style -position) "absolute")
    (set! (.. face -style -left) "100px")))

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
      "visible"))))

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
  (map
   + ;; (fn [i j] (+ i j))
   [1 0]
   [0 2])
  (let [i 0]
    (assoc
     (into [] (repeat 3 0))
     i
     1)))
