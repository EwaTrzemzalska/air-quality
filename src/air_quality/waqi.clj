(ns air-quality.waqi
  (:require [clj-http.client :as client]
            [cheshire.core :as cheshire]))

(def access-key "0bf2bab980a7b6eaf5f8e4f9451e2fc2de54c39b")
(def endpoint "https://api.waqi.info/feed/")

(defn build-request-about-city-str [city]
  (str endpoint city "/?token=" access-key))

(defn send-request [request-string]
  (-> (client/get request-string)
      :body
      (cheshire/parse-string true)))

(defn get-air-quality
  "If city found returns a map with air quality informations from WAQI API.

   A map in following format is returned:
   {:status ok
    :data {:aqi 148
           :idx 8689
           :attributions [{:url http://monitoring.krakow.pios.gov.pl/
                           :name Regional Inspectorate for Environmental Protection in Krakow (WIOŚ - Wojewódzki Inspektorat Ochrony Środowiska w Krakowie)
                           :logo poland-wios-krakowie.png} 
                          {:url http://powietrze.gios.gov.pl/
                           :name Główny inspektorat ochrony środowiska
                           :logo poland-wios-national.png} 
                          {:url https://waqi.info/
                           :name World Air Quality Index Project}]
           :city {:geo [50.057447 19.946008]
                  :name Kraków-ul. Dietla Małopolska Poland
                  :url https://aqicn.org/city/poland/malopolska/krakow-ul.-dietla}
           :dominentpol pm25
           :iaqi {:co {:v 15.2}
                  :no2 {:v 24.2}
                  :p {:v 1019.7}
                  :pm10 {:v 58}
                  :pm25 {:v 148}
                  :t {:v -0.1}
                  :w {:v 0.2}
                  :wg {:v 2.5}}
           :time {:s 2020-01-14 17:00:00
                  :tz +01:00
                  :v 1579021200}
           :debug {:sync 2020-01-15T03:49:28+09:00}}}"
  [city]
  (let [response (send-request (build-request-about-city-str city))]
    (if (= "ok" (get response :status))
      response
      (println "Please provide correct city"))))