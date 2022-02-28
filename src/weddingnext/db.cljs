(ns weddingnext.db
  ;; (:refer-clojure [clojure.co])
  (:require
   [re-frame.core :as rf]
   [weddingnext.components.lake :as lake]
   [weddingnext.specs :as wedding.specs]
   [weddingnext.components.door :as door]))

(def
  init-db
  {::door/answer ""
   ::door/counter 0
   ::lake/answer ""
   ::wedding.specs/page :page/door})

(rf/reg-event-db
  :initialize-db
  (fn [_ _]
    init-db))
