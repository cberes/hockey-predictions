(ns net.seabears.hockey.routes.predictions
  (:require [clj-time.core :refer [date-time]]
            [clj-time.coerce :as coerce]))

(defn home-team []
  {:name "Buffalo Sabres"
   :location "Buffalo"})

(defn away-team []
  {:name "Carolina Hurricanes"
   :location "Carolina"})

(defn recent [ctx]
  {:games [
           {:scheduled (coerce/to-string (date-time 1986 10 14 4 3 27 456))
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
                                :confidence 0.45}}}
           ]})
  
(defn upcoming [ctx]
  {:games [
           {:scheduled (coerce/to-string (date-time 1986 10 14 4 3 27 456))
            :home {:team (home-team)
                   :outcome {:score 2
                             :predicted 1}}
            :away {:team (away-team)
                   :outcome {:score 3
                             :predicted 2}}}
           {:scheduled (coerce/to-string (date-time 1986 10 14 4 3 27 456))
            :home {:team (home-team)
                   :outcome {:score 2
                             :predicted 3}}
            :away {:team (away-team)
                   :outcome {:score 4
                             :predicted 1}}}
           ]})

