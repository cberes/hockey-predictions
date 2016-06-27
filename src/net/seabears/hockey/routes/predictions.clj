(ns net.seabears.hockey.routes.predictions
  (:require [liberator.representation :refer [ring-response]]
            [clj-time.core :refer [date-time]]
            [clj-time.coerce :as coerce]))

(defn home-team []
  {:name "Buffalo Sabres"
   :location "Buffalo"})

(defn away-team []
  {:name "Carolina Hurricanes"
   :location "Carolina"})

(defn upcoming [ctx]
  (ring-response
    {:games
     [{:scheduled (coerce/to-string (date-time 1986 10 14 4 3 27 456))
       :home {:team (home-team)
              :prediction {:score 1
                           :confidence 0.57}}
       :away {:team (away-team)
              :prediction {:score 2
                           :confidence 0.45}}}
      {:scheduled (coerce/to-string (date-time 1986 10 14 4 3 27 456))
       :home {:team (home-team)
              :prediction {:score 3
                           :confidence 0.57}}
       :away {:team (away-team)
              :prediction {:score 1
                           :confidence 0.45}}}]}
    {:headers {"Access-Control-Allow-Origin" "*"}}))
  
(defn recent [ctx]
  (ring-response
    {:games
     [{:scheduled (coerce/to-string (date-time 1986 10 14 4 3 27 456))
       :home {:team (home-team)
              :score 2
              :prediction {:score 1
                           :confidence 0.57}}
       :away {:team (away-team)
              :score 3
              :prediction {:score 2
                           :confidence 0.45}}}
      {:scheduled (coerce/to-string (date-time 1986 10 14 4 3 27 456))
       :home {:team (home-team)
              :score 2
              :prediction {:score 3
                           :confidence 0.57}}
       :away {:team (away-team)
              :score 4
              :prediction {:score 1
                           :confidence 0.45}}}]}
    {:headers {"Access-Control-Allow-Origin" "*"}}))

