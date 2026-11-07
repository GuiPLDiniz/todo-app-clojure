
(ns todo.backend.core
  (:require [ring.adapter.jetty :as jetty]
            [reitit.ring :as ring]
            [muuntaja.core :as m]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [todo.backend.handler :as handler]
            [todo.backend.db :as db])
  (:gen-class))

(def app-routes
  (ring/router
   [["/api/hello"
     {:get {:handler handler/hello-handler}}]

    ["/api/todos"
     {:get {:handler handler/list-todos-handler}
      :post {:handler handler/create-todo-handler}}]]

   {:data {:muuntaja m/instance
           :middleware [muuntaja/format-middleware]}}))


(defn wrap-cors [handler]
  (fn [request]
    (let [headers {"Access-Control-Allow-Origin"
                   "http://localhost:8000"
                   "Access-Control-Allow-Methods"
                   "GET, POST, PUT, DELETE, OPTIONS"
                   "Access-Control-Allow-Headers"
                   "Content-Type"}]
      (if (= :options (:request-method request))
        {:status 204
         :headers headers
         :body ""}
        (update (handler request)
                :headers
                #(merge % headers))))))

(def app
  (wrap-cors
   (ring/ring-handler
    app-routes
    (ring/create-default-handler))))



(defn start-server [port]
  (db/init-db!)
  (println "Banco SQLite inicializado.")
  (println (str "Servidor iniciado na porta " port))
  (jetty/run-jetty #'app
                   {:port port
                    :join? false}))


(defn -main [& args]
  (let [port (Integer/parseInt (or (first args) "3000"))]
    (start-server port)))
