(ns word-spec.test
  (:require
    [clojure.test :as t]
    [word-spec.core :as c]))

(t/deftest test-word
  (t/is (= (c/parse "hi") "hi")))