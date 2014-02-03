(defproject tetris "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/clj" "src/cljs"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2138"]
                 [ring "1.2.1"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.5"]]

  :plugins [[lein-cljsbuild "1.0.0"]
            [lein-ring "0.8.10"]]

  :ring {:handler tetris.server/app}

  :cljsbuild {:builds
              [{:source-paths ["src/cljs"]

                :compiler {:output-to "resources/public/js/tetris.js"
                           :optimizations :whitespace
                           :pretty-print true}}]})
  
