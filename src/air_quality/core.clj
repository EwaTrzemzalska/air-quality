(ns air-quality.core
  (:require [clj-http.client :as client]
            [cheshire.core :as cheshire])
  (:gen-class))


(def access-key "0bf2bab980a7b6eaf5f8e4f9451e2fc2de54c39b")
(def endpoint "https://api.waqi.info/feed/")

(defn build-request-about-city-str [city]
  (str endpoint city "/?token=" access-key))

(defn send-request [request-string]
  (-> (client/get request-string)
      :body
      (cheshire/parse-string true)))




(defn -main
  [city]
  (println (send-request city)))
