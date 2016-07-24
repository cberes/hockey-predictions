(ns net.seabears.hockey.models.db
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :as str])
  (:import java.sql.DriverManager))

(def db
  (delay {:subprotocol "postgresql"
          :subname (System/getenv "HOCKEY_WEB_DB_URL")
          :user (System/getenv "HOCKEY_WEB_DB_USERNAME")
          :password (System/getenv "HOCKEY_WEB_DB_PASSWORD")}))

(defn get-error [e]
  (if-let [cause (.getNextException e)] cause e))

(defn trim [s]
  (if (str/blank? s) nil (str/trim s)))

(defn upcoming-games []
  (sql/query
    @db
    ["select g._id as id, g.scheduled,
             h.name as home, h.location as home_location, 0 as home_pred,
             a.name as away, a.location as away_location, 0 as away_pred
     from game g
     join team h on h._id = g.home_team_id
     join team a on a._id = g.away_team_id
     left outer join game_result res on res.game_id = g._id
     where res.game_id is null
     order by g.scheduled"]
    :result-set-fn doall))

(defn recent-games []
  (sql/query
    @db
    ["select g._id as id, g.scheduled,
             h.name as home, h.location as home_location, res.home_score, 0 as home_pred,
             a.name as away, a.location as away_location, res.away_score, 0 as away_pred
     from game g
     join team h on h._id = g.home_team_id
     join team a on a._id = g.away_team_id
     join game_result res on res.game_id = g._id
     order by g.scheduled desc"]
    :result-set-fn doall))
