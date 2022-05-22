(ns org.msync.tfjs.toxicity
  (:require ["@tensorflow/tfjs" :as tf]
            ["@tensorflow-models/toxicity" :as toxicity]
            [promesa.core :as p]))

(defonce p-model (delay (.load toxicity)))

(defn ^:export toxic? [sentence]
  (p/then
    @p-model
    (fn [model]
      (p/then
        (.classify model #js [sentence])
        (fn [predictions]
          (js/console.log (js/JSON.stringify predictions nil 2)))))))

(comment
  (toxic? "You are a poopy head!"))
