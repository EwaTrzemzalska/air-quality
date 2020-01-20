(ns air-quality.ui)

(defn- air-quality-info->location [air-quality-info]
  (get-in air-quality-info [:data :city :name]))

(defn- air-quality-info->aqi [air-quality-info]
  (get-in air-quality-info [:data :aqi]))

(defn- air-quality-info->pm25 [air-quality-info]
  (get-in air-quality-info [:data :iaqi :pm25 :v]))

(defn- air-quality-info->pm10 [air-quality-info]
  (get-in air-quality-info [:data :iaqi :pm10 :v]))

(defn- build-location-str [air-quality-info]
  (when-let [location (air-quality-info->location air-quality-info)]
    (str "In " location)))

(defonce aqi-norm 50)
(defonce pm25-norm 25)
(defonce pm10-norm 50)

(defn- build-aqi-str [air-quality-info]
  (if-let [aqi (air-quality-info->aqi air-quality-info)]
    (str " - AQI: " aqi " (" (* 100 (/ aqi aqi-norm)) "%)")
    "no data about AQI, "))

(defn- build-pm25-str [air-quality-info]
   (if-let [pm25 (air-quality-info->pm25 air-quality-info)]
     (str ", PM 2.5: " pm25 " μg/m3 (" (* 100 (/ pm25 pm25-norm)) "%)")
     ", no data about PM 2.5 in this location"))

(defn- build-pm10-str [air-quality-info]
  (if-let [pm10 (air-quality-info->pm10 air-quality-info)]
    (str ", PM 10: " pm10 " μg/m3 (" (* 100 (/ pm10 pm10-norm)) "%).")
    ", no data about PM 10 in this location."))

(defn build-result-str [air-quality-info]
    (if-let [location-str (build-location-str air-quality-info)]
     (str location-str
          (build-aqi-str air-quality-info)
          (build-pm25-str air-quality-info)
          (build-pm10-str air-quality-info))
      "Location not found"))