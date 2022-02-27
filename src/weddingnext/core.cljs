(ns weddingnext.core
  (:require
    [reagent.dom :as rdom]
    [weddingnext.db]
    [re-frame.core :as rf]
    ;; components
    [weddingnext.components.lake :refer [lake]]
    [weddingnext.components.counter :refer [counter]]))

(defn weddingnext []
  [:main
   ;; [counter]
   [lake]
   ;; [(fn []
   ;;    [:p "fo"])]
   ;; [lake]
   ])

;; start is called by init and after code reloading finishes
(defn ^:dev/after-load start []
  (rdom/render [weddingnext]
               (.getElementById js/document "weddingnext")))

(defn init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (rf/dispatch-sync [:initialize-db])
  (js/console.log "start")
  (start))

; this is called before any code is reloaded
(defn ^:dev/before-load stop []
  (js/console.log "stop"))