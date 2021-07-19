(ns cargo.core
  (:require cljsjs.fabric))

(enable-console-print!)

(def state (atom {}))

(defn movement-handler
  "Takes a JS object with the properties target (fabric object) and e (Mouse Event)"
  [options]
  (let [target (.-target options)
        x (.-left target)
        y (.-top target)
        width (:tw @state)
        height (:th @state)
        x-under? (< x 0)
        y-under? (< y 0)
        x-over? (> (+ x (.-width target)) width)
        y-over? (> (+ y (.-height target)) height)]
    (cond (and x-under? y-under?)
          (do
            (. target setLeft 0)
            (. target setTop 0))
          (and x-under? y-over?)
          (do
            (. target setLeft 0)
            (. target setTop (- height (.-height target))))
          (and x-over? y-under?)
          (do
            (. target setLeft (- width (.-width target)))
            (. target setTop 0))
          (and x-over? y-over?)
          (do
            (. target setLeft (- width (.-width target)))
            (. target setTop (- height (.-height target))))
          x-under? (. target setLeft 0)
          y-under? (. target setTop 0)
          x-over? (. target setLeft (- width (.-width target)))
          y-over? (. target setTop (- height (.-height target))))
    (. target setCoords))) ; make control positions recalculate

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
      (.on fabric-canvas (js-obj "object:moving" movement-handler))
      (swap! state assoc :canvas fabric-canvas))))

(defn create-cargo []
  (let [width (:cw @state)
        height (:ch @state)
        color (:cc @state)
        canvas (:canvas @state)
        rect-options {:width width :height height :fill color}
        rect (new (.-Rect js/fabric) (clj->js rect-options))
        control-options {:mtr false :tr false :br false :bl false :mb false
                         :ml false :mr false :mt false :tl false}]
    (.setControlsVisibility rect (clj->js control-options))
    (.add canvas rect)))

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
