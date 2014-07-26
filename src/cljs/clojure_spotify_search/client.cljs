(ns clojure-spotify-search.cs
    (:use [jayq.core :only [$]])
    (:require [jayq.core :as jq]
              [ajax.core :refer [GET]]))

(def $clickhere ($ :#clickhere))
(def $prevent-default ($ :#google-link))

(jq/bind $clickhere :click (fn [e] (js/console.log "Clicked!!")))

(jq/bind $prevent-default :click (fn [e] (.preventDefault e)))

;(js/console.log (get {:a 1 :b 2 } :a))

(defn append-track [track]
  (jq/append ($ :body) ( str "<div class='track'>Name: " ( track "name" )  " link: " (track "external_url") "</div>" )))

(defn return-tracks [tracks]
  ((tracks "tracks") "items"))

(defn log-track [response]
  (js/console.log ((last (return-tracks response)) "name")))

;(defn handler [response]
;  (js/console.log  ((last ((response "tracks") "items")) "name")))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "something bad happened: " status " " status-text)))

(defn print-track [track]
  (js/console.log ( str ( track "name") (track "external_url") )))

(append-track {"name" "sam" "external_url" "rodger"})

(defn loop-tracks [response]
  (dotimes [i (count (return-tracks response)) ]
    (append-track ((return-tracks response) i))
      ))

;(js/console.log (str i ": " ((return-tracks response) i)
(js/console.log (subvec {"name" "sam" "external_url" "rodger"} "rodger"))


(GET "https://api.spotify.com/v1/search"
  {:params {:q "taking back sunday"
            :type "track"
            :limit 5}
    :handler loop-tracks
    :error-handler error-handler})
