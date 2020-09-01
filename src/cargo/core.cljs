(ns cargo.core
  (:require [clojure.browser.repl :as repl]))

;; (defonce conn
;;   (repl/connect "http://localhost:9000/repl"))

(enable-console-print!)

(defn create-grid [x y]
    (println "create grid with dimension" x "x" y))

(defn button-handler []
  (let [x (. (.getElementById js/document "x") -value)
        y (. (.getElementById js/document "y") -value)]
  (create-grid x y)))
