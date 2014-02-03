(ns tetris.angular.config)
(.config
  (.module js/angular "tetris" (array "ngRoute" "ngAnimate"))
  (array "$routeProvider"
         (fn [$routeProvider]
           (do
             (.when
               $routeProvider
               "/"
               (js-obj "controller" "GameCtrl" "templateUrl"
                       "templates/game.html"))
             (.otherwise
               $routeProvider
               (js-obj "redirectTo" "/"))))))

