## Description
A toy project to learn more about clojure.spec and deps.edn.

## How to use
- Run with text
  ```
  clj -m word-spec Hello Clojure.  Hello world.
  ```
- Run with stdin
  ```
  clj -m word-spec < cap.txt
  ```
- Test
  ```
  clj -Atest

  ```  

## Discoveries
Besides the basics of spec and deps, these were harder to learn.

- [deps.edn is not yet supported by Cursive](https://github.com/cursive-ide/cursive/issues/1910)
  - Create a pom from deps.edn:
    ```
    clj -Spom
    ```
  - In Cursive open the directory where deps.edn and the newly generated pom.xml exists
  - Right click `pom.xml` -> "Add as Maven Project"
  - When deps change click `pom.xml` -> "Maven" -> "Reimport"
- [s/spec](https://clojure.github.io/spec.alpha/clojure.spec.alpha-api.html#clojure.spec.alpha/spec) is required to start a new nested regex context.
- [clojure.spec.test.alpha/instrument](https://clojure.github.io/spec.alpha/clojure.spec.test.alpha-api.html#clojure.spec.test.alpha/instrument) does not instrument function `:ret` specs, an alternative like [Orchestra](https://github.com/jeaye/orchestra) must be used instead.
- Functions which return lazy-seqs are eagerly evaluated when an `fdefs` is specified and instrumented.
- s/keys doesn't just look for the key, it checks the spec of the value:
  ```
  (s/def ::word string?)
  (s/valid? (s/keys :req [::word]) {::word "hi"}) => true
  (s/valid? (s/keys :req [::word]) {::word 4}) => false
  (s/valid? (s/keys) {::word 4}) => false
  ```