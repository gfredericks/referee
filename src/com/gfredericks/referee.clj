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

(comment
  (defn catchingly*
    [class func]
    (try (->Success (func))
         (catch Throwable ex
           (if (instance? class ex)
             (->Failure ex)
             (throw ex)))))

  (defmacro catchingly
    [class-name & body]
    `(catchingly* ~class-name (fn [] ~@body))))

(defmacro catchingly
  [class-name & body]
  `(try (->Success (do ~@body))
        (catch ~class-name ex#
          (->Failure ex#))))
