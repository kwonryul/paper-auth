(ns kwonryul.paper-auth.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init       (fn []
                 (log/info "\n-=[paper-auth starting]=-"))
   :start      (fn []
                 (log/info "\n-=[paper-auth started successfully]=-"))
   :stop       (fn []
                 (log/info "\n-=[paper-auth has shut down successfully]=-"))
   :middleware (fn [handler _] handler)
   :opts       {:profile :prod}})
