(ns weddingnext.core
  (:require
   [reagent.dom :as rdom]
   [weddingnext.db]
   [re-frame.core :as rf]
   [weddingnext.specs :as ws]
   [weddingnext.assets.assets :as assets :refer [assets]]
   [weddingnext.animations.faces :as faces]
   ;; components
   [weddingnext.components.cake-result :refer [cake-result]]
   [weddingnext.components.cake :refer [cake]]
   [weddingnext.components.bakers :refer [bakers]]
   [weddingnext.components.bakers-result :refer [bakers-result]]
   [weddingnext.components.lake :refer [lake]]
   [weddingnext.components.door :refer [door]]
   [weddingnext.components.lake-result :refer [lake-result]]
   [weddingnext.components.counter :refer [counter]]))

(rf/reg-sub ::ws/page ::ws/page)

(defn
  weddingnext
  []
  (let [page @(rf/subscribe [::ws/page])]
    [:main
     ({:page/door [door]
       :page/lake [lake]
       :page/bakers [bakers]
       :page/cake [cake]
       :page/bakers-result [bakers-result]
       :page/cake-result [cake-result]
       :page/lake-result [lake-result]}
      page)]))

;; start is called by init and after code reloading finishes
(defn ^:dev/after-load start []
  (faces/mount)
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
  (faces/unmount)
  (js/console.log "stop"))
