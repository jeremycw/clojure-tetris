(ns tetris.angular.gamectrl
  (:require-macros [tetris.angular.macros :as macros]))
(.controller
  (.module js/angular "tetris")
  "GameCtrl"
  (array "$scope" "$timeout"
         (fn [$scope $timeout]
           (do
             (set! (.-game $scope) (logic/new-game))
             (macros/add-move-to-scope $scope "left")
             (macros/add-move-to-scope $scope "right")
             (macros/add-move-to-scope $scope "drop")
             (macros/add-move-to-scope $scope "rotate")
             ($timeout (fn down-on-timeout []
                         (do
                           (.down $scope)
                           ($timeout down-on-timeout 1000))) 1000)))))
