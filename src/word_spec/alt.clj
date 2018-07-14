(ns word-spec.alt
  (:require
    [clojure.set :refer [union]]
    [clojure.spec.alpha :as s]
    [orchestra.core :refer [defn-spec]]
    [orchestra.spec.test :as st]
    [expound.alpha :as expound]))

(comment
  (set! s/*explain-out* expound/printer)
  )

(def terminals #{\. \! \?})

(s/def ::char #(and (char? %) (not ((union #{\space \newline} terminals) %))))

(s/def ::word (s/cat :spaces (s/* #{\space}) :word (s/+ ::char)))

(s/def ::sentence (s/cat :sentence (s/+ ::word) :terminal terminals))

(s/def ::paragraph (s/cat :paragraph (s/+ ::sentence)
                          :newline (s/? #{\newline})))

(s/def ::document (s/* ::paragraph))

(st/instrument)

(defn ec [s st]
  (let [sq (seq st)]
    (prn)
    (prn s st)
    (s/explain s sq)
    (prn (s/conform s sq))))

(ec ::document "hi  ")
(ec ::document "hi")
(ec ::document "hi.")
(ec ::document "Hello. Good day.\nIsn't it!")
