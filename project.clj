 (defproject my-api "0.1.0-SNAPSHOT"
   :description "FIXME: write description"
   :dependencies [[org.clojure/clojure "1.10.0"]
                  [metosin/compojure-api "2.0.0-alpha30"]
                  [toucan "1.18.0"]
                  [lein-postgres "0.1.6"]]
   :keep-non-project-classes true
   :clean-non-project-classes false
   :ring {:handler my-api.handler/app}
   :uberjar-name "server.jar"
   :main ^:skip-aot my-api.handler
   :profiles {:dev {:dependencies [[javax.servlet/javax.servlet-api "3.1.0"]]
                    :plugins [[lein-ring "0.12.5"]]}
              :uberjar {:aot :all}})
