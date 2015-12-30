(ns capstone-demo-clj-api.config
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [capstone-demo-clj-api.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[capstone-demo-clj-api started successfully using the development profile]=-"))
   :middleware wrap-dev})
