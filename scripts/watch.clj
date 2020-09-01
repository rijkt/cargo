(require '[cljs.build.api :as b])

(b/watch "src"
  {:main 'cargo.core
   :output-to "out/cargo.js"
   :output-dir "out"})
