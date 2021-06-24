(ns cargo.core)

(enable-console-print!)

(def counter 1) ; for id generation
(def state (atom {}))

(defn drag-over-handler [event]
  (set! (.. event -dataTransfer -dropEffect) "move")
  (. event preventDefault)) ; prevent default behavior to mark as drop zone

(defn drop-handler [event]
  (. event preventDefault)
  (let [id (.getData (.-dataTransfer event) "text/plain")
        target (.-target event)]
    (.appendChild target (.getElementById js/document id))))

(defn create-truck []
  (let [width (. (.getElementById js/document "truck-width") -value)
        height (. (.getElementById js/document "truck-height") -value)
        container (.getElementById js/document "truck-area")
        style     (.createAttribute js/document "style")]
    (set! (.-value style) (str "width: " width "px; height: " height "px;" "outline: solid black;"))
    (.addEventListener container "dragover" drag-over-handler)
    (.addEventListener container "drop" drop-handler)
    (.setAttributeNode container style)))

(defn drag-start-handler [event]
  (let [id (.. event -target -id)]
    (.setData (.-dataTransfer event) "text/plain" id)))

(defn create-cargo []
  (let [width (. (.getElementById js/document "cargo-width") -value)
        height (. (.getElementById js/document "cargo-height") -value)
        container (.getElementById js/document "cargo-area")
        style (.createAttribute js/document "style")
        cargo (.createElement js/document "div")
        id (str "cargo-" counter)]
    (set! (.-value style) (str "width: " width "px; height: " height "px;" "background-color: green;"))
    (.setAttributeNode cargo style)
    (.setAttribute cargo "id" id)
    (.setAttribute cargo "draggable" "true")
    (.addEventListener cargo "dragstart" drag-start-handler)
    (.appendChild container cargo)
    (set! counter (inc counter))))

(defn read-form! [id]
  (int (. (.getElementById js/document id) -value)))
  
(defn update-form-state [event]
  (let [truck-width (read-form! "truck-width")
        truck-height (read-form! "truck-height")
        cargo-width (read-form! "cargo-width")
        cargo-height (read-form! "cargo-height")]
    (swap! state assoc :tw truck-width :th truck-height :cw cargo-width :ch cargo-height)))

(defn setup-event-handlers []
(let [inputs (array-seq (.getElementsByTagName js/document "input"))]
  (dorun (map #(.addEventListener % "change" update-form-state) inputs))))

(setup-event-handlers)
