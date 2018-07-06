(ns word-spec.core
  (:require
    [clojure.spec.alpha :as s]
    [orchestra.spec.test :as st]))

(s/def ::newlines (s/+ #{\newline}))

(s/def ::word (s/or :s (s/+ char?) :s string?))
(s/def ::word-ret (s/keys :req [::word]))

(s/def ::sentence (s/* (s/spec ::word-ret)))
(s/def ::sentence-ret (s/keys :req [::sentence]))

(s/def ::paragraph (s/* (s/spec ::sentence-ret)))
(s/def ::paragraph-ret (s/keys :req [::paragraph]))

(s/def ::document (s/* (s/spec ::paragraph-ret)))
(s/def ::document-ret (s/keys :req [::document]))

(s/def ::arg-word (s/cat :s (s/spec ::word)))

(defn partition-remove [s character]
  (->> (partition-by #{character} s)
       (remove #(some #{character} %))))

(defn parse-word [s]
  {::word (apply str s)})

(defn parse-sentence [s]
  {::sentence (map parse-word (partition-remove s \space))})

(defn parse-paragraph [s]
  {::paragraph (map parse-sentence (partition-remove s \.))})

(defn parse [s]
  {::document (map parse-paragraph (partition-remove s \newline))})

;(fn [x] (prn (type x) x) true) :fn (constantly true)
(s/fdef parse-word :args ::arg-word :ret ::word-ret)
(s/fdef parse-sentence :args ::arg-word :ret ::sentence-ret)
(s/fdef parse-paragraph :args ::arg-word :ret ::paragraph-ret)
(s/fdef parse :args ::arg-word :ret ::document-ret)

(st/instrument)

(comment

  (parse "Hello Clojure.\nHello Spec.  How are you?")

  (parse 8)

  )