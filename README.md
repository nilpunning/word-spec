# How to use

- Generate pom to make it easy to integrate with an IDE
  ```
  clj -Spom
  ```
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
