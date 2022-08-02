(ns my_api.db
  (:require [toucan.db :as toucandb]
            [toucan.models :as models])
  (:import java.sql.Timestamp))

(toucandb/set-default-db-connection!
 {:classname   "org.postgresql.Driver"
  :subprotocol "postgresql"
  :subname     "//postgres:5432/mydb"
  :user        "user"
  :password    "password"})

(defn- now [] (Timestamp. (System/currentTimeMillis)))

(defn add-timestamp-columns [lead]
  (assoc lead :created_at (now), :updated_at (now)))

;; define the Lead model
(models/defmodel Lead :lead
  models/IModel
  (primary-key [_] :lead_id)
  (pre-insert [this]
              (add-timestamp-columns this)))

(defn create-lead [lead]
  (->>
   (toucandb/insert! Lead lead)
   (:lead_id)))

(defn get-lead [id]
  (try (Lead id)
       (catch Exception e
         (.printStackTrace e))))
