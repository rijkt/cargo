(ns cargo.core
  (:require cljsjs.fabric))

(enable-console-print!)

(def state (atom {}))

(defn create-truck []
  (let [wrapper (.getElementById js/document "canvas-wrapper")
        new-canvas (.createElement js/document "canvas")]
    (set! (.-innerHTML wrapper) "") ; todo: this is not recommended, find a better way
    (.appendChild wrapper new-canvas)
    (let [width (:tw @state)
          height (:th @state)
          fabric-canvas (new (.-Canvas js/fabric) new-canvas)]
      (.setDimensions fabric-canvas (clj->js {:width width :height height}))
      (swap! state assoc :canvas fabric-canvas))))

(defn create-cargo []
  (let [width (:cw @state)
        height (:ch @state)
        canvas (:canvas @state)]
    (.add canvas
          (new (.-Rect js/fabric)
               (clj->js {:width width :height height :fill "red" })))))

(defn read-form! [id]
  (int (. (.getElementById js/document id) -value)))
  
(defn update-form-state []
  (let [truck-width (read-form! "truck-width")
        truck-height (read-form! "truck-height")
        cargo-width (read-form! "cargo-width")
        cargo-height (read-form! "cargo-height")]
    (swap! state assoc :tw truck-width :th truck-height :cw cargo-width :ch cargo-height)))

(defn setup-event-handlers []
(let [inputs (array-seq (.getElementsByTagName js/document "input"))]
  (dorun (map #(.addEventListener % "change" update-form-state) inputs))))

(update-form-state) ; read data left in form after refresh
(setup-event-handlers)
