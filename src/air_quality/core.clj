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

; (defn get-current-weather
;   "If city found returns a map with current air pollution informations from waqi API. 

;   A map in following format is returned:
;   {\"status\":\"ok\"
;     \"data\":{\"aqi\":70
;     \"idx\":8689
;     \"attributions\":[{\"url\":\"http://monitoring.krakow.pios.gov.pl/\"
;     \"name\":\"Regional Inspectorate for Environmental Protection in Krakow (WIOŚ - Wojewódzki Inspektorat Ochrony Środowiska w Krakowie)\"
;     \"logo\":\"poland-wios-krakowie.png\"}
;     {\"url\":\"http://powietrze.gios.gov.pl/\"
;     \"name\":\"Główny inspektorat ochrony środowiska\"
;     \"logo\":\"poland-wios-national.png\"}
;     {\"url\":\"https://waqi.info/\"
;     \"name\":\"World Air Quality Index Project\"}]
;     \"city\":{\"geo\":[50.057447
;     19.946008]
;     \"name\":\"Kraków-ul. Dietla
;      Małopolska
;      Poland\"
;     \"url\":\"https://aqicn.org/city/poland/malopolska/krakow-ul.-dietla\"}
;     \"dominentpol\":\"pm25\"
;     \"iaqi\":{\"co\":{\"v\":6}
;     \"no2\":{\"v\":19.2}
;     \"o3\":{\"v\":12.1}
;     \"p\":{\"v\":1020.6}
;     \"pm10\":{\"v\":26}
;     \"pm25\":{\"v\":70}
;     \"so2\":{\"v\":3.8}}
;     \"time\":{\"s\":\"2020-01-13 13:00:00\"
;     \"tz\":\"+01:00\"
;     \"v\":1578920400}
;     \"debug\":{\"sync\":\"2020-01-13T23:15:42+09:00\"}}}

;   Will throw an error if city is not found"
;   [query]
;   (let [response (send-request (build-request-about-city-str query))]
;     (if (false? (get response :status))
;       (throw (ex-info "Please provide existing city" {:code (get-in response [:error :code])}))
;       response)))


(defn -main
  [city]
  (println (send-request city)))
