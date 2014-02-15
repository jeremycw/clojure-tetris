(ns tetris.angular.gamectrl
  (:require-macros [tetris.angular.macros :as macros])
  (:require [tetris.angular.config :as conf]
            [tetris.logic :as logic]
            [tetris.print :as p]))
(.controller
  (.module js/angular "tetris")
  "GameCtrl"
  (array "$scope" "$timeout"
         (fn [$scope $timeout]
           (do
             (set! (.-game $scope) (logic/new-game logic/random-block-seq))
             (macros/add-move-to-scope $scope "left")
             (macros/add-move-to-scope $scope "right")
             (macros/add-move-to-scope $scope "down")
             (macros/add-move-to-scope $scope "drop")
             (macros/add-move-to-scope $scope "rotate")
             ($timeout (fn down-on-timeout []
                         (do
                           (.down $scope)
                           (.log js/console (p/to-string (.-game $scope)))
                           ($timeout down-on-timeout 1000))) 1000)))))
