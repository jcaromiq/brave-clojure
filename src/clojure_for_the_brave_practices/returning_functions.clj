(ns clojure-for-the-brave-practices.returning-functions)

(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))

(def inc3 (inc-maker 3))
(def inc5 (inc-maker 5))

(inc3 7)
(inc5 10)
