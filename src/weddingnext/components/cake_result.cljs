(ns
    weddingnext.components.cake-result
    (:require-macros
     [weddingnext.slurp
      :refer
      [slurp]])
    (:require
     [re-frame.core :as rf]
     [reagent.core :as r]
     [weddingnext.specs :as ws]
     [weddingnext.components.elements
      :as
      elms]
     [weddingnext.components.cake
      :as
      cake]
     [weddingnext.assets.colors
      :as
      colors]))

(rf/reg-sub ::cake/correct? ::cake/correct?)

(def asci
  (slurp "public/art/cake2"))

(defn
  cake-result
  []
  (let [correct? @(rf/subscribe
                   [::cake/correct?])]
    [:<>
     [:div.w-30.p-1
      "Die Antwort ist 5 Gramm."
      " 105 Gramm Äpfel plus 5 Gramm Streusel ergeben 110 Gramm"]
     [elms/devider]
     [:pre.font-monospace.mt-10.mb-10
      {:style {:font-size "0.1rem"
               :line-height "0.15rem"}}
      asci]]))

(comment
  (reset!
   re-frame.db/app-db
   (assoc
    weddingnext.db/init-db
    ::ws/page :page/cake-result)))
