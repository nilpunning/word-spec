(ns word-spec
  (:require [word-spec.core :as c])
  (:import [java.io BufferedReader]))

(defn seq-char []
  (let [br (BufferedReader. *in*)]
    (->> (repeatedly #(.read br))
         (take-while pos-int?)
         (map char))))

(defn -main [& args]
  ; Either read from stdin or args
  (->> (if (> (.available System/in) 0)
         (seq-char)
         (apply str (interpose " " args)))
       c/parse
       (run! prn)))