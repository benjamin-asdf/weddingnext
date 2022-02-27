(ns weddingnext.components.door)


(defn
  door
  []
  [:h1 "Willkommen"
   [:p
    "Dein magischer Stein zeigt dir eine "
    "grosse Tuere. "
    "Auf der Tuere sind Buchstaben, deren strahlen "
    "dich an Sternenlich erinnert. "]
   [:p
    {:style
     {:border-style "double"
      :margin "10vw"
      :font-size "4vw"
      :color "#758ea7"
      :border "10px"}}
    "Sprich Kind und tritt ein"]])
