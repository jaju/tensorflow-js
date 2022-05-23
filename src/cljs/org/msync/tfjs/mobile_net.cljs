(ns org.msync.tfjs.mobile-net
  (:require ["@tensorflow/tfjs" :as tf]
            ["@tensorflow-models/mobilenet" :as mb]
            [promesa.core :as p]))


(defonce i-model (delay (.load mb)))

(defn ^:export classify [img cb]
  (p/then
    @i-model
    (fn [model]
      (p/then
        (.classify model img)
        (fn [predictions]
          (cb predictions)
          (js/console.log (js/JSON.stringify predictions nil 2)))))))

(comment
  (def img-el (js/document.getElementById "img"))
  (def response (atom nil))
  (defn record-response [predictions]
    (reset! response (js->clj predictions :keywordize-keys true)))
  (classify img-el record-response))