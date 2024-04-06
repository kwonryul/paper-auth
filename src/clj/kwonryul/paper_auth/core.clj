(ns kwonryul.paper-auth.core
  (:require
    [clojure.tools.logging :as log]
    [integrant.core :as ig]
    [kwonryul.paper-auth.config :as config]
    [kwonryul.paper-auth.env :refer [defaults]]

    ;; Edges       
    [kit.edge.server.undertow]
    [kwonryul.paper-auth.web.handler]

    ;; Routes
    [kwonryul.paper-auth.web.routes.api]
    
    [kwonryul.paper-auth.web.routes.pages] 
    [kit.edge.db.sql.conman] 
    [kit.edge.db.sql.migratus])
  (:gen-class))

;; log uncaught exceptions in threads
(Thread/setDefaultUncaughtExceptionHandler
  (reify Thread$UncaughtExceptionHandler
    (uncaughtException [_ thread ex]
      (log/error {:what :uncaught-exception
                  :exception ex
                  :where (str "Uncaught exception on" (.getName thread))}))))

(defonce system (atom nil))

(defn stop-app []
  ((or (:stop defaults) (fn [])))
  (some-> (deref system) (ig/halt!))
  (shutdown-agents))

(defn start-app [& [params]]
  ((or (:start params) (:start defaults) (fn [])))
  (->> (config/system-config (or (:opts params) (:opts defaults) {}))
       (ig/prep)
       (ig/init)
       (reset! system))
  (.addShutdownHook (Runtime/getRuntime) (Thread. stop-app)))

(defn -main [& _]
  (start-app))
