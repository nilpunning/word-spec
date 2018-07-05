(ns word-spec.core
  (:require [clojure.spec.alpha :as s]))

(s/def ::newlines (s/+ #{\newline}))

(s/def ::word (s/+ char?))
(s/def ::sentence (s/* (s/spec ::word)))
(s/def ::paragraph (s/* (s/spec ::sentence)))
(s/def ::document (s/* (s/spec ::paragraph)))
(s/def ::arg-word (s/cat :s (s/spec ::word)))

(s/fdef parse-sentence :ars ::arg-word :ret ::sentence)
(s/fdef parse-paragraph :args ::arg-word :ret ::paragraph)
(s/fdef parse :args (s/cat :s (s/or :s ::word :s string?)) :ret ::document)

(defn partition-remove [s character]
  (->> (partition-by #{character} s)
       (remove #(some #{character} %))))

(defn parse-sentence [s]
  (partition-remove s \space))

(defn parse-paragraph [s]
  (map parse-sentence (partition-remove s \.)))

(defn parse [s]
  (map parse-paragraph (partition-remove (seq s) \newline)))

(stest/instrument)

(comment
  (require '[clojure.spec.test.alpha :as stest])

  (parse "Hello Clojure.\nHello Spec.  How are you?")
  (parse 8)

  (s/valid? ::word [\a])
  (s/valid? ::sentence [[\a]])
  (s/valid? ::paragraph [[[\a]]])
  (s/valid? ::document [[[[\a]]]])

  )