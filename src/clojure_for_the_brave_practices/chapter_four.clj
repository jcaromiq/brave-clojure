(ns clojure-for-the-brave-practices.chapter-four)

;;------- Treating Lists, Vectors, Sets, and Maps as Sequences
(map titleize ["Hamsters" "Ragnarok"])
; => ("Hamsters for the Brave and True" "Ragnarok for the Brave and True")

(map titleize '("Empathy" "Decorating"))
; => ("Empathy for the Brave and True" "Decorating for the Brave and True")

(map titleize #{"Elbows" "Soap Carving"})
; => ("Elbows for the Brave and True" "Soap Carving for the Brave and True")

(map #(titleize (second %)) {:uncomfortable-thing "Winking"})
; => ("Winking for the Brave and True")


;;------- First rest and cons
(seq '(1 2 3))
; => (1 2 3)

(seq [1 2 3])
; => (1 2 3)

(seq #{1 2 3})
; => (1 2 3)

(seq {:name "Bill Compton" :occupation "Dead mopey guy"})
; => ([:name "Bill Compton"] [:occupation "Dead mopey guy"])

;You can convert the seq back into a map by using into to stick the result into an empty map
(into {} (seq {:a 1 :b 2 :c 3}))
; => {:a 1, :c 3, :b 2}

;;------- MAP
(map inc [1 2 3])
; => (2 3 4)
(map str ["A" "B" "C"] ["a" "b" "c"])
; => ("Aa" "Bb" "Cc")

(def human-consumption   [8.1 7.3 6.6 5.0])
(def critter-consumption [0.0 0.2 0.3 1.1])

(defn unify-diet-data
  [human critter]
  {:human human
   :critter critter})

(map unify-diet-data human-consumption critter-consumption)
;=> ({:human 8.1, :critter 0.0}
;    {:human 7.3, :critter 0.2}
;    {:human 6.6, :critter 0.3}
;    {:human 5.0, :critter 1.1})

(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))

(stats [3 4 10])
; => (17 3 17/3)

(stats [80 1 44 13 6])
; => (144 5 144/5)

(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-Man" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Easter Bunny" :real "Your dad"}])

(map :real identities)
; => ("Bruce Wayne" "Peter Parker" "Your mom" "Your dad")

;;------- REDUCE
(reduce (fn [new-map [key val]]
          (assoc new-map key (inc val)))
        {} {:max 30 :min 10})
;=> {:max 31, :min 11}

(reduce (fn [new-map [key val]]
          (if (> val 4)
            (assoc new-map key val)
            new-map))
        {} {:human 4.1 :critter 3.9})
;=> {:human 4.1}

;;------- take, drop, take-while, and drop-while
(take 3 [1 2 3 4 5 6 7 8 9])
; => (1 2 3)
(drop 3 [1 2 3 4 5 6 7 8 9])
; => (4 5 6 7 8 9)

(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])

(take-while #(< (:month %) 3) food-journal)
; => ({:month 1 :day 1 :human 5.3 :critter 2.3}
;     {:month 1 :day 2 :human 5.1 :critter 2.0}
;     {:month 2 :day 1 :human 4.9 :critter 2.1}
;     {:month 2 :day 2 :human 5.0 :critter 2.5})

(drop-while #(< (:month %) 3) food-journal)
; => ({:month 3 :day 1 :human 4.2 :critter 3.3}
;     {:month 3 :day 2 :human 4.0 :critter 3.8}
;     {:month 4 :day 1 :human 3.7 :critter 3.9}
;     {:month 4 :day 2 :human 3.7 :critter 3.6})


(take-while #(< (:month %) 4)
            (drop-while #(< (:month %) 2) food-journal))
; => ({:month 2 :day 1 :human 4.9 :critter 2.1}
;     {:month 2 :day 2 :human 5.0 :critter 2.5}
;     {:month 3 :day 1 :human 4.2 :critter 3.3}
;     {:month 3 :day 2 :human 4.0 :critter 3.8})

;;------- filter and some
(filter #(< (:human %) 5) food-journal)
; => ({:month 2, :day 1, :human 4.9, :critter 2.1}
;     {:month 3, :day 1, :human 4.2, :critter 3.3}
;     {:month 3, :day 2, :human 4.0, :critter 3.8}
;     {:month 4, :day 1, :human 3.7, :critter 3.9}
;     {:month 4, :day 2, :human 3.7, :critter 3.6})

(filter #(< (:month %) 3) food-journal)
; => ({:month 1, :day 1, :human 5.3, :critter 2.3}
;     {:month 1, :day 2, :human 5.1, :critter 2.0}
;     {:month 2, :day 1, :human 4.9, :critter 2.1}
;     {:month 2, :day 2, :human 5.0, :critter 2.5})

(some #(> (:critter %) 5) food-journal)
; => nil

(some #(> (:human %) 5) food-journal)
; => true

(some #(and (> (:critter %) 3) %) food-journal)
; => {:month 3 :day 1 :human 4.2 :critter 3.3}


;;------- sort and sort-by
(sort [3 1 4])
; => (1 3 4)

(sort '(3 1 4))
;=> (1 3 4)

(sort #{3 1 4})
;=> (1 3 4)

(sort-by count ["aaa" "c" "bb"])
; => ("c" "bb" "aaa")

;;------- concat
(concat [1 2] [3 4])
; => (1 2 3 4)

;;------- Lazy Seqs