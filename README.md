# referee

A Clojure library for handling exceptions across thread boundaries.

## Obtention

![version](http://clojars.org/com.gfredericks/referee/latest-version.svg)

## Usage

``` clojure
(require '[com.gfredericks.referee :as referee])

(def my-success
  (referee/catchingly Exception
    (/ 8 7)))

(def my-failure
  (referee/catchingly Exception
    (/ 42 0)))

(map referee/success? [my-success my-failure])
;; => [true false]

(referee/result my-success)
;; => 8/7

(referee/result my-failure)
;; => #<ArithmeticException java.lang.ArithmeticException: Divide by zero>

(deref my-success)
;; => 8/7

(deref my-failure)
;; throws #<ArithmeticException java.lang.ArithmeticException: Divide by zero>
```

## License

Copyright © 2014 Gary Fredericks

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
