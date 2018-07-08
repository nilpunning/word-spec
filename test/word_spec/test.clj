(ns word-spec.test
  (:require
    [clojure.test :as t]
    [clojure.spec.alpha :as s]
    [word-spec.core :as c]))

(t/deftest test-word
  (t/is (= (c/parse "hi")
           [{::c/paragraph [{::c/sentence [{::c/word "hi"}]}]}])))


(comment

  (t/run-tests)
  )