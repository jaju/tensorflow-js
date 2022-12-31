(ns org.msync.tfjs.toxicity
  (:require ["@tensorflow/tfjs" :as tf]
            ["@tensorflow-models/toxicity" :as toxicity]
            [promesa.core :as p]))

(defonce p-model (delay (.load toxicity)))

(defn ^:export toxic? [sentence cb]
  (p/then
    @p-model
    (fn [model]
      (p/then
        (.classify model #js [sentence])
        cb))))

(comment
  (def response (atom nil))
  (defn record-response [predictions]
    (js/console.log (js/JSON.stringify predictions nil 2))
    (reset! response (js->clj predictions :keywordize-keys true)))
  (toxic? "You are a poopy head!" record-response)
  (toxic? "Marwadis are intelligent!" record-response)
  @response)
