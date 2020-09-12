(ns cargo.core
  (:require [clojure.browser.repl :as repl]))

;; (defonce conn
;;   (repl/connect "http://localhost:9000/repl"))

(enable-console-print!)

(defn create-truck []
  (let [width (. (.getElementById js/document "truck-width") -value)
        height (. (.getElementById js/document "truck-height") -value)
        container (.getElementById js/document "cargo-area")
        style     (.createAttribute js/document "style")]
    (set! (.-value style) (str "width: " width "px; height: " height "px;" "outline: solid black;"))
    (.setAttributeNode container style)))
