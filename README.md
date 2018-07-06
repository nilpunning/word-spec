# Description
A toy project to learn more about clojure.spec and deps.edn.

# How to use

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
- A pom was created to make it easy to integrate with an IDE, like [Cursive](https://github.com/cursive-ide/cursive/issues/1910)
  ```
  clj -Spom
  ```

# Stumbling blocks

- [s/spec](https://clojure.github.io/spec.alpha/clojure.spec.alpha-api.html#clojure.spec.alpha/spec) is required to start a new nested regex context.
- [clojure.spec.test.alpha/instrument](https://clojure.github.io/spec.alpha/clojure.spec.test.alpha-api.html#clojure.spec.test.alpha/instrument) does not instrument `:ret`, you must use an alternative like [Orchestra](https://github.com/jeaye/orchestra) instead.

# TODO
- Return seq of paragraphs instead of document
- Try https://github.com/bhb/expound
- Try more concise Orchestra function definitions