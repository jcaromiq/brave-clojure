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

;; ------- PULLING IT ALL TOGETHER -------
(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a seq of maps that hace a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-body-parts
               (set [part (matching-part part)])))))))

(symmetrize-body-parts asym-hobbit-body-parts)

;; ------- LET -------
(let [x 3]
  x )

(def dalmatian-list
  ["Pongo" "Perdita" "Puppy 1" "Puppy 2"])
(let [dalmatians (take 2 dalmatian-list)]
  dalmatians)

(def x 0)
(let [x 1] x)

(def zero 0)
(let [x (inc zero)] x)

(let [[pongo perdita & dalmatians] dalmatian-list]
  [pongo perdita dalmatians])

;; ------- LOOP -------

(loop [iteration 0]
  (println (str "Iteration: " iteration))
  (if (> iteration 3)
    (println "Goodbye!")
    (recur (inc iteration))))

(defn recursive-printer
  ([]
   (recursive-printer 0))
  ([iteration]
   (println iteration)
   (if (> iteration 3)
     (println "Goodbye!")
     (recursive-printer (inc iteration)))))

  (recursive-printer)

;; ------- REGULAR EXPRESSIONS -------
(re-find #"^left-" "left-eye")
(re-find #"^left-" "cleft-chin")
(re-find #"^left-" "wongleblart")

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(matching-part {:name "left-eye" :size 1})
(matching-part {:name "head" :size 3})

;; ------- REDUCE -------
(reduce + [1 2 3 4 5])
(reduce + 15 [1 2 3 4 5])

(defn better-symmetrize-body-parts
  "Expects a seq of maps that hace a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          ;;Second parameter in reduce, in this case, a empty list
            []
            asym-body-parts))

(better-symmetrize-body-parts asym-hobbit-body-parts)

(defn hit
  [asym-body-parts]
  (let [sym-parts (better-symmetrize-body-parts asym-body-parts)
        body-part-size-sum (reduce + (map :size sym-parts))
        target (rand body-part-size-sum)]
    (loop [[part & remaining] sym-parts
           accumulated-size (:size part)]
      (if (> accumulated-size target)
        part
        (recur remaining (+ accumulated-size (:size (first remaining))))))))
