(ns air-quality.core
  (:gen-class))


(def access-key "0bf2bab980a7b6eaf5f8e4f9451e2fc2de54c39b")
(def endpoint "https://api.waqi.info/feed/")

(defn build-request-about-city-str [city]
  (str endpoint city "/?token=" access-key))

(defn -main
  [city]
  (println (build-request-about-city-str city)))
