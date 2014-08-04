(ns com.gfredericks.referee-test
  (:require [clojure.test :refer :all]
            [com.gfredericks.referee :as referee])
  (:import clojure.lang.ExceptionInfo))

(deftest catchingly-test
  (is (referee/catchingly Exception (/ 42 0)))
  (is (thrown? Exception
               (referee/catchingly ExceptionInfo (/ 42 0)))))

(deftest success?-test
  (is (referee/success? (referee/catchingly Throwable (+ 1 2))))
  (is (not (referee/success? (referee/catchingly Throwable (throw (Exception. "WHoops")))))))

(deftest result-test
  (is (= 42 (referee/result (referee/catchingly Throwable (* 2 3 7)))))
  (is (instance? ArithmeticException
                 (referee/result (referee/catchingly Exception (/ 42 0))))))

(deftest deref-test
  (is (= 42 @(referee/catchingly Exception (* 2 3 7))))
  (let [x (referee/catchingly Exception (/ 42 0))]
    (is (thrown? ArithmeticException @x))))
