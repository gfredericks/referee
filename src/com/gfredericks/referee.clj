(ns com.gfredericks.referee)

(defprotocol IThing
  (success? [x])
  (result [x]))

(defrecord Success [val]
  IThing
  (success? [_] true)
  (result [_] val)
  clojure.lang.IDeref
  (deref [_] val))

(defrecord Failure [ex]
  IThing
  (success? [_] false)
  (result [_] ex)
  clojure.lang.IDeref
  (deref [_] (throw ex)))

(defmacro catchingly
  [class-name & body]
  `(try (->Success (do ~@body))
        (catch ~class-name ex#
          (->Failure ex#))))
