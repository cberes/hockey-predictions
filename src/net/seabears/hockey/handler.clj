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
  :handle-ok predictions/recent)

(defresource upcoming-games [_]
  :allowed-methods [:get]
  :available-media-types ["application/json"]
  :handle-ok predictions/upcoming)

(defroutes app
  (ANY "/games/recent" [] recent-games)
  (ANY "/games/upcoming" [] upcoming-games))

(def handler 
  (-> app 
      wrap-params))
