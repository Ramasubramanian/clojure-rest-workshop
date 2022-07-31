(ns my_api.db
  (:require [toucan.db :as toucandb]
            [toucan.models :as models]))

(toucandb/set-default-db-connection!
 {:classname   "org.postgresql.Driver"
  :subprotocol "postgresql"
  :subname     "//postgres:5432/mydb"
  :user        "user"
  :password    "password"})

;; define the Lead model
(models/defmodel LeadData :lead
  models/IModel
  (primary-key [_] :lead_id))

(defn create-lead [lead]
  (->>
   (toucandb/insert! LeadData lead)
   (:lead_id)))

(defn get-lead [id]
  (try (LeadData id)
       (catch Exception e
              (.printStackTrace e))))
