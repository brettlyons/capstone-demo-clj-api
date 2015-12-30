(ns capstone-demo-clj-api.handler
  (:require [compojure.core :refer [defroutes routes wrap-routes]]
            [capstone-demo-clj-api.layout :refer [error-page]]
            [capstone-demo-clj-api.routes.home :refer [home-routes]]
            [capstone-demo-clj-api.routes.services :refer [service-routes]]
            [capstone-demo-clj-api.middleware :as middleware]
            [clojure.tools.logging :as log]
            [compojure.route :as route]
            [environ.core :refer [env]]
            [capstone-demo-clj-api.config :refer [defaults]]
            [mount.core :as mount]))

(defn init
  "init will be called once when
   app is deployed as a servlet on
   an app server such as Tomcat
   put any initialization code here"
  []
  (when-let [config (:log-config env)]
    (org.apache.log4j.PropertyConfigurator/configure config))
  (doseq [component (:started (mount/start))]
    (log/info component "started"))
  ((:init defaults)))

(defn destroy
  "destroy will be called when your application
   shuts down, put any clean up code here"
  []
  (log/info "capstone-demo-clj-api is shutting down...")
  (doseq [component (:stopped (mount/stop))]
    (log/info component "stopped"))
  (log/info "shutdown complete!"))

(def app-routes
  (routes
    (var service-routes)
    (wrap-routes #'home-routes middleware/wrap-csrf)
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))

(def app (middleware/wrap-base #'app-routes))
