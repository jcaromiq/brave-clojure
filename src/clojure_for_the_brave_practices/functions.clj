(ns clojure-for-the-brave-practices.functions)
(map (fn [name] (str "Hi, " name))
     ["Darth Vader", "Mr. Magoo"])

((fn [x] (* x 3)) 8)

(def my-multiplier (fn [x] (* x 3)))
(my-multiplier 12)

(def my-divider #(/ % 3))
(my-divider 3)

(map #(str "Hi, " %)
     ["Darth Vader", "Mr. Magoo"])

(#(str %1 " and " %2) "cornbread" "butter beans")

(#(identity %&) 1 "dos" :tres ["Darth Vader", "Mr. Magoo"])