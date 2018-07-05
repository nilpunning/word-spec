(ns word-spec.test
  (:require
    [clojure.test :as t]
    [clojure.spec.alpha :as s]
    [clojure.spec.gen.alpha :as g]
    [word-spec.core :as c]))

(t/deftest test-word
  (prn (g/generate (s/gen ::c/document)))
  (t/is (= (c/parse "hi") "hi")))