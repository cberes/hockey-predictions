(ns net.seabears.hockey.routes.predictions
  (:require [liberator.representation :refer [ring-response]]
            [clj-time.core :refer [date-time]]
            [clj-time.coerce :as coerce]
            [net.seabears.hockey.models.db :as db]))

(defn adapt-team [result bench]
  (let [location-keyword (keyword (str bench "_location"))
        pred-keyword (keyword (str bench "_pred"))
        score-keyword (keyword (str bench "_score"))
        team-keyword (keyword (str bench))
        team {:team {:name (str (team-keyword result))
                     :location (str (location-keyword result))}
              :prediction {:score (pred-keyword result)
                           :confidence 0.0}}]
    {(keyword bench)
     (if (contains? result score-keyword)
       (assoc team :score (score-keyword result))
       team)}))

(defn adapt-result [result]
  (merge
    {:id (:id result)}
    {:scheduled (-> result
                    :scheduled
                    coerce/from-sql-date
                    coerce/to-string)}
    (adapt-team result "home")
    (adapt-team result "away")))

(defn adapt-results [results]
  {:games (map adapt-result results)})

(defn upcoming [ctx]
  (ring-response
    (adapt-results (db/upcoming-games 90 20))
    {:headers {"Access-Control-Allow-Origin" "*"}}))
  
(defn recent [ctx]
  (ring-response
    (adapt-results (db/recent-games 90 20))
    {:headers {"Access-Control-Allow-Origin" "*"}}))

