(ns weddingnext.components.lake
  (:require-macros [weddingnext.slurp :refer [slurp]])
  (:require [re-frame.core :as rf]))



(def asci
  (slurp "public/art/lake"))

(defn
  lake
  []
  (let [answer @(rf/subscribe [::answer])]
    [:div
     [:p
      {:style {:max-width 1000}}
      (str
       "Du kommst zu einem See. Do hoerst sanftes Plaetschern."
       " Eine Seerose "
       "schwimmt auf der Oberflaeche. "
       ;; sitzt
       ;; briese
       ;; plaetchern

       "Du stells dir die Frage")]
     [:hr {:style {
                   :color "#feb48f"
                   :border-bottom "1px"  :border-top "2px solid" ;; "1px solid #CCC"
                   }}]
     [:p
      {:style {:max-width 1000}}
       "Wenn sich die Flaeche der Seerosen jeden Tag verdoppelt "
       "und nach 30 Tagen der See komplett bedeckt ist, "
       "an welchem Tag ist die Healfte des Sees bedeckt? "]

     [:pre
      {:style {
               ;; :font-size ".9vw"
               :font-family :monospace
               }}
      asci]]))


(rf/reg-sub
 ::answer
 (fn [db]
   (::answer db)))

(rf/reg-event-db
 ::give-answer
 (fn [db [_ s]]
   (cond->
       db
       (not (::answer db))
       (assoc ::answer s))))
