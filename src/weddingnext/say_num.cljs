(ns
    weddingnext.say-num
    (:require
     [re-frame.core :as rf]))

(defn
  say-num-interceptor
  [get-answer]
  (rf/->interceptor
   :id ::say-num?
   :before
   (fn
     [context]
     (rf/console :log get-answer)
     (let [answer (get-answer (rf/get-coeffect context :db))]
       (rf/assoc-coeffect
        context
        ::say-num?
        (or (nil? answer)
            (not answer)
            (zero? (count answer))
            (js/isNaN answer)))))))

(rf/reg-fx
 ::say-num?
 (fn
   [_]
   (rf/console :log "hi1")
   (js/alert "Sage eine Zahl.")))

(defn
  classifier
  [id get-answer correct-id pred]
  (rf/->interceptor
   :id
   id
   :before
   (fn
     [context]
     (let [db (rf/get-coeffect context :db)
           answer (get-answer db)]
       (rf/console :log
                   (rf/get-coeffect
                    context
                    ::say-num?))
       (cond->
           context
           (not
            (rf/get-coeffect
             context
             ::say-num?))
           (rf/assoc-coeffect
            correct-id
            (pred answer)))))))
