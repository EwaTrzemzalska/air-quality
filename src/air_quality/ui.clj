(ns air-quality.ui
  (:require [air-quality.waqi :as waqi]))

(defn- air-quality-info->location [air-quality-info]
  (get-in air-quality-info [:data :city :name]))

(defn- air-quality-info->aqi [air-quality-info]
  (get-in air-quality-info [:data :aqi]))

(defn- air-quality-info->pm25 [air-quality-info]
  (get-in air-quality-info [:data :iaqi :pm25 :v]))

(defn- air-quality-info->pm10 [air-quality-info]
  (get-in air-quality-info [:data :iaqi :pm10 :v]))

(defn- location-str [air-quality-info]
  (let [location (air-quality-info->location air-quality-info)]
    (when location
      (str "In " location))))

(defonce aqi-norm 50)
(defonce pm25-norm 25)
(defonce pm10-norm 50)

(defn- aqi-str [air-quality-info]
  (let [aqi (air-quality-info->aqi air-quality-info)]
    (if aqi
      (str " - AQI: " aqi " (" (* 100 (/ aqi aqi-norm)) "%)")
      "no data about AQI, ")))

(defn- pm25-str [air-quality-info]
  (let [pm25 (air-quality-info->pm25 air-quality-info)]
    (if pm25
      (str ", PM 2.5: " pm25 " μg/m3 (" (* 100 (/ pm25 pm25-norm)) "%)")
      ", no data about PM 2.5 in this location")))

(defn- pm10-str [air-quality-info]
  (let [pm10 (air-quality-info->pm10 air-quality-info)]
    (if pm10
      (str ", PM 10: " pm10 " μg/m3 (" (* 100 (/ pm10 pm10-norm)) "%).")
      ", no data about PM 10 in this location.")))

(defn build-result-str [city]
  (let [air-quality-info (waqi/get-air-quality city)]
    (if (location-str air-quality-info)
      (str (location-str air-quality-info)
           (aqi-str air-quality-info)
           (pm25-str air-quality-info) 
           (pm10-str air-quality-info))
      "Location not found")))