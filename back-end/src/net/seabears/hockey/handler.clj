(ns net.seabears.hockey.handler
  (:require [liberator.core :refer [resource defresource]]
            [ring.middleware.params :refer [wrap-params]]
            [compojure.core :refer [defroutes ANY]]
            [net.seabears.hockey.routes.predictions :as predictions]))

(defn init []
  nil)

(defresource recent-games [_]
  :allowed-methods [:get]
  :available-media-types ["application/json"]
  :handle-ok predictions/recent-games)

(defresource upcoming-games [_]
  :allowed-methods [:get]
  :available-media-types ["application/json"]
  :handle-ok predictions/upcoming-games)

(defresource game [id]
  :allowed-methods [:get]
  :available-media-types ["application/json"]
  :handle-ok (fn [ctx] (predictions/game ctx (Integer/parseInt id))))

(defroutes app
  (ANY "/games/recent" [] recent-games)
  (ANY "/games/upcoming" [] upcoming-games)
  (ANY "/games/:id" [id] (game id)))

(def handler 
  (-> app 
      wrap-params))
