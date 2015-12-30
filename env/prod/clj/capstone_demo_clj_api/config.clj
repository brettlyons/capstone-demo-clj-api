(ns capstone-demo-clj-api.config
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[capstone-demo-clj-api started successfully]=-"))
   :middleware identity})
