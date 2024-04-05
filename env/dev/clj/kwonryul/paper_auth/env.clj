(ns kwonryul.paper-auth.env
  (:require
    [clojure.tools.logging :as log]
    [kwonryul.paper-auth.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init       (fn []
                 (log/info "\n-=[paper-auth starting using the development or test profile]=-"))
   :start      (fn []
                 (log/info "\n-=[paper-auth started successfully using the development or test profile]=-"))
   :stop       (fn []
                 (log/info "\n-=[paper-auth has shut down successfully]=-"))
   :middleware wrap-dev
   :opts       {:profile       :dev
                :persist-data? true}})
