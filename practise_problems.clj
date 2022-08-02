;;Calculator using separate functions first
;;----------------------------------------------------------------------------------
(defn calculator [x a b]
  (case x "+" (println (+ a b))
        "-" (println (- a b))
        "*" (println (* a b))
        "/" (println (/ a b))))

(def x (read-line))
(def a (Integer/parseInt (clojure.string/trim (read-line))))
(def b (Integer/parseInt (clojure.string/trim (read-line))))

(calculator x a b)
;;----------------------------------------------------------------------------------

(defn fib [n]
  (case n
    0 0
    1 1
    (+ (fib (- n 1)) (fib (- n 2)))))
(fib 10)

;;----------------------------------------------------------------------------------

(defn fibsequence [max]
  (loop [a 1, b 1, acc [1]]
    (if (>= (count acc) max)
      acc
      (recur b (+ a b) (conj acc b)))))
(fibsequence 10)
;;----------------------------------------------------------------------------------

(defn add5 [list]
  (map #(+ % 5) list))

(add5 [1 2 3])

;;----------------------------------------------------------------------------------

(defn sum-of-squares [n]
  (reduce + (map #(* % %) (range 1 (inc n)))))

(sum-of-squares 10)

;;---------------------------------------------------------------------------------