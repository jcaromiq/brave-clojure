(ns clojure-for-the-brave-practices.chapter-three)

;; ------- DESTRUCTURING -------
;; Return the first element of a collection
(defn my-first
  [[first-thing]]
  first-thing)
(my-first ["first item", "second item", "third item"])

(defn chosser
  [[first-choice second-choice & unimportant-choices]]
  (println (str "Your first choice is: " first-choice))
  (println (str "Your second choice is: " second-choice))
  (println (str "We're ignoring the rest of your choices. "
                "Here they are in case you need to cry over them: "
                (clojure.string/join ", " unimportant-choices))))
(chosser ["Marmalade", "Handsome Jack", "Pigpen", "Aquaman"])

(defn announce-treasure-location
  [{lat :lat lng :lng}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))
(announce-treasure-location {:lat 28.22 :lng 81.33})

(defn announce-treasure-location
  [{:keys [lat lng]}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))
(announce-treasure-location {:lat 28.22 :lng 81.33})

;; retain access to the original map argument by using the :as keyword
(defn receive-treasure-location
  [{:keys [lat lng] :as treasure-location}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng))
  treasure-location)

(receive-treasure-location {:lat 28.22 :lng 81.33})

;; ------- FUNCTIONS -------
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

;; ------- RETURNING FUNCTIONS -------
(ns clojure-for-the-brave-practices.returning-functions)

(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))

(def inc3 (inc-maker 3))
(def inc5 (inc-maker 5))

(inc3 7)
(inc5 10)
