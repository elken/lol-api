(ns blitzcrank.macros
  (:use [slingshot.slingshot :only [try+]]))

(defmacro try-all
  "Simple macro to try/catch multiple statements concisely.
  Use `catch-all` for multiple catch blocks which can be expanded."
  [& body]
  (let [catch-all? #(and (seq? %) (= (first %) 'catch-all))
        expand-catch (fn [[_catch-all exceptions & catch-tail]]
                       (map #(list* 'catch % catch-tail) exceptions))
        transform (fn [form]
                    (if (catch-all? form)
                      (expand-catch form)
                      [form]))]
    (cons `try+ (mapcat transform body))))
