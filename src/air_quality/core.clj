(ns air-quality.core
  (:require [air-quality.ui :as ui])
  (:gen-class))

(defn -main
  [city]
  (println (ui/build-result-str city)))