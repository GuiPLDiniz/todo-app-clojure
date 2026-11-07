
(ns todo.backend.handler
  "Funcoes responsaveis pelas respostas HTTP.")

(defn hello-handler
  "Retorna uma resposta simples de teste."
  [_request]
  {:status 200
   :body "Hello, World!"})
