(ns cargo.core
  (:require cljsjs.fabric))

(enable-console-print!)

(def state (atom {}))

(defn create-truck
  "Create loading area based on form inputs. Since creating a fabric Canvas changes the DOM,
  this fn first deletes any existing canvas and creates a new one."
  []
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
        color (:cc @state)
        canvas (:canvas @state)]
    (.add canvas
          (new (.-Rect js/fabric)
               (clj->js {:width width :height height :fill color})))))

(defn read-form! [id]
  (. (.getElementById js/document id) -value))
  
(defn update-form-state []
  (let [truck-width (int (read-form! "truck-width"))
        truck-height (int (read-form! "truck-height"))
        cargo-width (int (read-form! "cargo-width"))
        cargo-height (int (read-form! "cargo-height"))
        cargo-color (str (read-form! "cargo-color"))]
    (swap! state assoc :tw truck-width
           :th truck-height
           :cw cargo-width
           :ch cargo-height
           :cc cargo-color)))

(defn setup-event-handlers []
(let [inputs (array-seq (.getElementsByTagName js/document "input"))]
  (dorun (map #(.addEventListener % "change" update-form-state) inputs))))

(update-form-state) ; read data left in form after refresh
(setup-event-handlers)
(create-truck)
