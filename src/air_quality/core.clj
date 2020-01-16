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

(defn get-air-quality
  "If city found returns a map with air quality informations from waqi API.

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
           :debug {:sync 2020-01-15T03:49:28+09:00}}}


    {:status ok
     :data {:aqi 53
            :idx 6132
            :attributions [{:url http://www.stadtentwicklung.berlin.de/umwelt/luftqualitaet/
                            :name Berlin Air Quality - (Luftqualität in Berlin)
                            :logo Germany-Berlin.png} 
                           {:url https://waqi.info/
                            :name World Air Quality Index Project}]
            :city {:geo [52.5200066 13.404954]
                   :name Berlin Germany
                   :url https://aqicn.org/city/germany/berlin}
            :dominentpol pm10
            :iaqi {:no2 {:v 30.2}
                   :o3 {:v 17.1}
                   :pm10 {:v 53}
                   :t {:v 12.8}
                   :wg {:v 24.5}}
                   :time {:s 2020-01-15 14:00:00
                          :tz +01:00
                          :v 1579096800}
                   :debug {:sync 2020-01-15T23:17:43+09:00}}}"
  [city]
  (let [response (send-request (build-request-about-city-str city))]
    (if (= "ok" (get response :status))
      response
      (println "Please provide correct city"))))

(defn get-location [response]
  (let [location (get-in response [:data :city :name])]
    (if (not (= location nil))
      (str "In " location)
      "Location not found, ")))

(defn get-aqi [response]
  (let [aqi (get-in response [:data :aqi])]
    (if (not (= aqi nil))
      (str " - AQI: " aqi " (" (* 100 (/ aqi 50)) "%)")
      "No data about AQI in this location")))

(defn get-pm10 [response]
  (let [pm10 (get-in response [:data :iaqi :pm10 :v])]
    (if (not (= pm10 nil))
      (str ", PM 10: " pm10 " μg/m3 (" (* 100 (/ pm10 50)) "%).")
      ", no data about PM 10 in this location")))

(defn get-pm25 [response]
  (let [pm25 (get-in response [:data :iaqi :pm25 :v])]
    (if (not (= pm25 nil))
      (str ", PM 2.5: " pm25 " μg/m3 (" (* 100 (/ pm25 50)) "%)")
      ", no data about PM 2.5 in this location")))

(defn get-final-str [city]
  (let [response (get-air-quality city)]
    (str (get-location response) (get-aqi response) (get-pm25 response) (get-pm10 response))))

(defn -main
  [city]
  (println (get-final-str city)))