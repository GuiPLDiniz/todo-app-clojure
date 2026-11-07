
(ns todo.backend.db
  "Gerencia os dados das tarefas em memória.")

;; Armazena as tarefas em um mapa.
(def todos-db (atom {}))

;; Controla os identificadores das tarefas.
(def next-id (atom 0))

(defn get-all-todos
  "Retorna todas as tarefas cadastradas."
  []
  (vec (vals @todos-db)))

(defn create-todo
  "Cria, armazena e retorna uma nova tarefa."
  [todo-data]
  (let [id (swap! next-id inc)
        new-todo (assoc todo-data
                        :id id
                        :completed false
                        :created-at
                        (str (java.time.Instant/now)))]
    (swap! todos-db assoc id new-todo)
    new-todo))
