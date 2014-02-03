(ns tetris.server
  (:use compojure.core)
  (:use hiccup.core)
  (:require [compojure.route :as route]))

(defroutes app
  (GET "/" []
    (html
      [:head
        [:script {:type "text/javascript" :src "js/tetris.js"}]]
      [:body {:ng-app "tetris"}
        [:ng-view]]))
  (route/files "/" {:root "resources/public"})
  (route/not-found (html [:h1 "Page not found"])))
