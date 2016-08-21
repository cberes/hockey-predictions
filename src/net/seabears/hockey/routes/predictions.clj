(ns net.seabears.hockey.routes.predictions
  (:require [liberator.representation :refer [ring-response]]
            [clj-time.core :refer [date-time]]
            [clj-time.coerce :as coerce]
            [net.seabears.hockey.models.db :as db]))

(def default-headers
  (delay {"Access-Control-Allow-Origin" (System/getenv "HOCKEY_WEB_CORS_HOST")
          "Cache-Control" (str "max-age=" (System/getenv "HOCKEY_WEB_CACHE_SECS"))}))

(defn adapt-team [result bench]
  (let [location-keyword (keyword (str bench "_location"))
        score-keyword (keyword (str bench "_score"))
        team-keyword (keyword (str bench))
        team {:team {:name (str (team-keyword result))
                     :location (str (location-keyword result))}}]
    {(keyword bench)
     (if (some? (score-keyword result))
       (assoc team :score (score-keyword result))
       team)}))

(defn adapt-result [result]
  (merge
    {:id (:id result)}
    {:status (if (and (some? (:away_score result))
                      (some? (:home_score result))) "F" "S")}
    {:scheduled (-> result
                    :scheduled
                    coerce/from-sql-date
                    coerce/to-string)}
    {:prediction {:home_score (:home_pred result)
                  :away_score (:away_pred result)
                  :over_under (:over_under result)
                  :spread (:spread result)}}
    (adapt-team result "home")
    (adapt-team result "away")))

(defn adapt-results [results]
  {:games (map adapt-result results)})

(defn upcoming-games [ctx]
  (ring-response
    (adapt-results (db/upcoming-games 90 20))
    {:headers @default-headers}))
  
(defn recent-games [ctx]
  (ring-response
    (adapt-results (db/recent-games 90 20))
    {:headers @default-headers}))

(defn game [ctx id]
  (ring-response
    (adapt-results (db/game id))
    {:headers @default-headers}))

