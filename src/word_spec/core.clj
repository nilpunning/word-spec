(ns word-spec.core
  (:require
    [clojure.spec.alpha :as s]
    [orchestra.core :refer [defn-spec]]
    [orchestra.spec.test :as st]
    [expound.alpha :as expound]))

(set! s/*explain-out* expound/printer)

(s/def ::newlines (s/+ #{\newline}))

(s/def ::word (s/or :s (s/+ char?) :s string?))
(s/def ::word-map (s/keys :req [::word]))

(s/def ::sentence (s/* ::word-map))
(s/def ::sentence-map (s/keys :req [::sentence]))

(s/def ::paragraph (s/* ::sentence-map))
(s/def ::paragraph-map (s/keys :req [::paragraph]))

(s/def ::arg-word (s/cat :s ::word))

(defn partition-remove [s character]
  (->> (partition-by #{character} s)
       (remove #(some #{character} %))))

(defn-spec parse-word ::word-map [w ::word]
  {::word (apply str w)})

(defn-spec parse-sentence ::sentence-map [w ::word]
  {::sentence (map parse-word (partition-remove w \space))})

(defn-spec parse-paragraph ::paragraph-map [w ::word]
  {::paragraph (map parse-sentence (partition-remove w \.))})

(defn-spec parse (s/* ::paragraph-map) [w ::word]
  (map parse-paragraph (partition-remove w \newline)))

(st/instrument)

(comment

  (parse "Hello Clojure.\nHello Spec.  How are you?")

  (parse 8)

  (s/valid? (s/alt :w (s/cat :k #{::word} :v ::word)) [::word "hi"])

  (s/valid? (s/keys :req [::word]) {::word "hi"})
  (s/valid? (s/keys :req [::word]) {::word 8})

  (s/valid? (s/keys) {::word 4})


  (s/valid? (s/* string?) 8)
  (s/valid? (s/* ::word) 3)
  )