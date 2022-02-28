(ns weddingnext.db
  (:require
   [re-frame.core :as rf]
   [weddingnext.components.lake :as lake]
   [weddingnext.components.door :as door]))

(def
  init-db
  {::door/answer ""
   ::door/counter 0
   ::lake/answer ""})

(rf/reg-event-db
  :initialize-db
  (fn [_ _]
    init-db))
