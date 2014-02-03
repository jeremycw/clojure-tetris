(ns tetris.angular.keydown
  (:require-macros [tetris.angular.macros :as macros]))
(macros/keydown-directive "keyLeft" 37)
(macros/keydown-directive "keyRight" 39)
(macros/keydown-directive "keyUp" 38)
(macros/keydown-directive "keyDown" 40)
(macros/keydown-directive "keySpace" 32)
