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


(s/defschema Lead
  {:first_name s/Str
   :last_name s/Str
   :dob (s/constrained s/Str valid-date?)
   :ssn s/Str
   (s/optional-key :lead_id) int})


(def app
  (api
   {:swagger
    {:ui "/"
     :spec "/swagger.json"
     :data {:info {:title "My-api"
                   :description "Compojure Api example"}
            :tags [{:name "api", :description "some apis"}]}}}

   (context "/api" []
     :tags ["api"]
     (GET "/lead/:id" []
       :path-params [id :- s/Int]
       :return Lead
       :summary "Get a Lead by ID"
       (->> (my_api.db/get-lead id)
            (ok)))
     (POST "/lead" []
       :return s/Any
       :body [lead Lead]
       :summary "Creates a Lead"
       (->>
        (my_api.db/create-lead lead)
        (assoc nil :lead_id)
        (ok))))))
