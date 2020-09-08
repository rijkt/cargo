(ns cargo.core
  (:require [clojure.browser.repl :as repl]))

;; (defonce conn
;;   (repl/connect "http://localhost:9000/repl"))

(enable-console-print!)

(defn create-grid [width height]
  (let [container (.getElementById js/document "cargo-area")
        style     (.createAttribute js/document "style")]
    (set! (.-value style) (str "width: " width "px; height: " height "px;"))
    (.setAttributeNode container style)))

(defn button-handler []
  (let [x (. (.getElementById js/document "width") -value)
        y (. (.getElementById js/document "height") -value)]
  (create-grid x y)))
