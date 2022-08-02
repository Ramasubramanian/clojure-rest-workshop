(ns my-api.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]
            [my_api.db :refer :all]))

(defn valid-date?
  [date]
  (try (let [format (java.time.format.DateTimeFormatter/ofPattern "dd-MM-yyyy")]
         (java.time.LocalDate/parse date format)
         true)
       (catch java.time.format.DateTimeParseException e
         false)))


(s/defschema LeadRequest
  {:first_name s/Str
   :last_name s/Str
   :dob (s/constrained s/Str valid-date?)
   :ssn s/Str})

(s/defschema LeadResponse
  {:first_name s/Str
   :last_name s/Str
   :dob (s/constrained s/Str valid-date?)
   :ssn s/Str
   :created_at java.sql.Timestamp
   :updated_at java.sql.Timestamp
   :lead_id s/Int})

(defn convert-lead-to-response [lead]
  (cond (some? lead) (ok lead)
        :else (not-found {:message "Lead with given id not found"})))


(def app
  (api
   {:swagger
    {:ui "/"
     :spec "/swagger.json"
     :data {:info {:title "My-api"
                   :description "Compojure Api example"}
            :tags [{:name "api", :description "some apis"}]
            :consumes ["application/json"]
            :produces ["application/json"]}}}

   (context "/api" []
     :tags ["api"]
     (GET "/lead/:id" []
       :path-params [id :- s/Int]
       :return s/Any
       :summary "Get a Lead by ID"
       (->> (my_api.db/get-lead id)
            (convert-lead-to-response)))
     (POST "/lead" []
       :return {:lead_id s/Int}
       :body [lead LeadRequest]
       :summary "Creates a Lead"
       (->>
        (my_api.db/create-lead lead)
        (assoc nil :lead_id)
        (ok))))))
