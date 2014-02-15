(ns tetris.angular.macros)

(defmacro add-move-to-scope [scope command]
  `(set! (~(symbol (str ".-" command)) ~scope)
         (fn []
           (do
             (set! (.-game ~scope)
                   (logic/make-move (.-game ~scope) ~(keyword command)))
             (set! (.-grid ~scope)
                   (cljs/core/clj->js (logic/get-grid (.-game ~scope))))))))

(defmacro keydown-directive [directive-name key-code]
  `(.directive
     (.module js/angular "tetris")
     ~directive-name
     (fn []
       (fn [scope# element# attr#]
         (.bind
           element#
           "keydown"
           #(if (= (.-which %) ~key-code)
              (.$eval scope# (~(symbol (str ".-" directive-name)) attr#))
              nil))))))
