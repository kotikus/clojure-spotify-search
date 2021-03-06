(ns clojure-spotify-search.server
  (:use clojure-spotify-search.views)
  (:require
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :as response])
  (:use [hiccup core page]
        [compojure.core])
  (:gen-class))

(defroutes main-routes
  (GET "/" [] (index-page))
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (handler/site main-routes))

(use 'ring.server.standalone)

(defn -main [port]
  (serve app {:port (Integer. port)}))