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

(defn upcoming-games [days n]
  (sql/query
    @db
    ["select g._id as id, g.scheduled,
             h.name as home, h.location as home_location,
             a.name as away, a.location as away_location,
             round(cast(coalesce(4.6523 + 1.3305 * dh.corsi_rel + 0.4913 * da.corsi_rel, 0.0) as numeric), 1) as over_under,
             round(cast(coalesce(0.4595 + 5.1658 * dh.corsi_rel - 5.8210 * da.corsi_rel, 0.0) as numeric), 1) as spread
     from game g
     join team h on h._id = g.home_team_id
     join team a on a._id = g.away_team_id
     left outer join game_result res on res.game_id = g._id
     left outer join past_corsi_rel dh on g._id = dh.game_id and h._id = dh.team_id
     left outer join past_corsi_rel da on g._id = da.game_id and a._id = da.team_id
     where g.scheduled <= now() + ? * interval '1 day'
     and res.game_id is null
     order by g.scheduled
     limit ?" days n]
    :result-set-fn doall))

(defn recent-games [days n]
  (sql/query
    @db
    ["select g._id as id, g.scheduled,
             h.name as home, h.location as home_location, res.home_score,
             a.name as away, a.location as away_location, res.away_score,
             round(cast(coalesce(4.6523 + 1.3305 * dh.corsi_rel + 0.4913 * da.corsi_rel, 0.0) as numeric), 1) as over_under,
             round(cast(coalesce(0.4595 + 5.1658 * dh.corsi_rel - 5.8210 * da.corsi_rel, 0.0) as numeric), 1) as spread
     from game g
     join team h on h._id = g.home_team_id
     join team a on a._id = g.away_team_id
     join game_result res on res.game_id = g._id
     left outer join past_corsi_rel dh on g._id = dh.game_id and h._id = dh.team_id
     left outer join past_corsi_rel da on g._id = da.game_id and a._id = da.team_id
     where g.scheduled >= now() - ? * interval '1 day'
     order by g.scheduled desc
     limit ?" days n]
    :result-set-fn doall))

