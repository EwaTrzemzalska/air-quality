(ns air-quality.core
  (:require [air-quality.ui :as ui]
            [air-quality.waqi :as waqi])
  (:gen-class))

(defn -main
  [city]
  
  (-> city
      (waqi/get-air-quality)
      (ui/build-result-str)
      (println)))