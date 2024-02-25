(ns net.seabears.hockey.models.db
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :as str])
  (:import java.sql.DriverManager))

(def db
  (delay {:subprotocol "postgresql"
          :subname (System/getenv "HOCKEY_WEB_DB_URL")
          :user (System/getenv "HOCKEY_WEB_DB_USERNAME")
          :password (System/getenv "HOCKEY_WEB_DB_PASSWORD")}))

(def game-query
  "select g._id as id, g.scheduled,
          h.name as home, h.location as home_location, res.home_score,
          a.name as away, a.location as away_location, res.away_score,
          coalesce(2.606 + 3.685 * dh.corsi_rel - 3.200 * da.corsi_rel, 0.0) as home_pred,
          coalesce(2.675 - 3.655 * dh.corsi_rel + 3.300 * da.corsi_rel, 0.0) as away_pred,
          coalesce(5.28163 + 0.03059 * dh.corsi_rel + 0.10037 * da.corsi_rel, 0.0) as over_under,
          coalesce(-0.06864 + 7.33985 * dh.corsi_rel - 6.50023 * da.corsi_rel, 0.0) as spread
  from game g
  join team h on h._id = g.home_team_id
  join team a on a._id = g.away_team_id
  left outer join past_corsi_rel dh on g._id = dh.game_id and h._id = dh.team_id
  left outer join past_corsi_rel da on g._id = da.game_id and a._id = da.team_id
  left outer join game_result res on res.game_id = g._id ")

(defn upcoming-games [days n]
  (sql/query
    @db
    [(str game-query
          "where g.scheduled <= now() + ? * interval '1 day'
          and res.game_id is null
          order by g.scheduled
          limit ?") days n]
    :result-set-fn doall))

(defn recent-games [days n]
  (sql/query
    @db
    [(str game-query
          "where g.scheduled >= now() - ? * interval '1 day'
          and res.game_id is not null
          order by g.scheduled desc
          limit ?") days n]
    :result-set-fn doall))

(defn game [id]
  (sql/query
    @db
    [(str game-query
          "where g._id = ?") id]
    :result-set-fn doall))

