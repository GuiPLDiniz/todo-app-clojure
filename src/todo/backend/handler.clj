
(ns todo.backend.handler
  (:require [todo.backend.db :as db]))

(defn hello-handler [_request]
  {:status 200
   :body "Hello, World!"})

(defn list-todos-handler [_request]
  {:status 200
   :body (db/get-all-todos)})

(defn create-todo-handler [request]
  (let [todo-data (:body-params request)
        title (:title todo-data)]
    (if (and (string? title)
             (not (clojure.string/blank? title)))
      {:status 201
       :body (db/create-todo {:title title})}
      {:status 400
       :body {:error "O titulo da tarefa e obrigatorio"}})))
