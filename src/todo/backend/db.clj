

(ns todo.backend.db
  (:require [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]))

;; Configuracao do banco SQLite
(def db-spec
  {:dbtype "sqlite"
   :dbname "prod.db"})

;; Cria a tabela caso ela ainda nao exista
(defn init-db! []
  (jdbc/execute!
   db-spec
   ["CREATE TABLE IF NOT EXISTS todos (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       title TEXT NOT NULL,
       completed INTEGER NOT NULL DEFAULT 0,
       created_at TEXT NOT NULL
     )"]))

;; Converte o resultado SQL para o formato da API
(defn format-todo [todo]
  {:id (:id todo)
   :title (:title todo)
   :completed (= 1 (:completed todo))
   :created-at (:created_at todo)})

;; Lista todas as tarefas
(defn get-all-todos []
  (->> (jdbc/execute!
        db-spec
        ["SELECT id, title, completed, created_at
          FROM todos ORDER BY id"]
        {:builder-fn rs/as-unqualified-lower-maps})
       (mapv format-todo)))

;; Cria uma nova tarefa

(defn create-todo [todo-data]
  (let [title (:title todo-data)
        created-at (str (java.time.Instant/now))]
    (with-open [conn (jdbc/get-connection db-spec)]
      (jdbc/execute!
       conn
       ["INSERT INTO todos
         (title, completed, created_at)
         VALUES (?, ?, ?)"
        title 0 created-at])

      (let [todo
            (jdbc/execute-one!
             conn
             ["SELECT id, title, completed, created_at
               FROM todos
               WHERE id = last_insert_rowid()"]
             {:builder-fn rs/as-unqualified-lower-maps})]
        (format-todo todo)))))


;; Alterna o estado de conclusao de uma tarefa
(defn toggle-todo [id]
  (jdbc/execute!
   db-spec
   ["UPDATE todos
     SET completed = CASE
       WHEN completed = 0 THEN 1
       ELSE 0
     END
     WHERE id = ?"
    id])

  (when-let [todo
             (jdbc/execute-one!
              db-spec
              ["SELECT id, title, completed, created_at
                FROM todos
                WHERE id = ?"
               id]
              {:builder-fn rs/as-unqualified-lower-maps})]
    (format-todo todo)))

;; Exclui uma tarefa pelo ID
(defn delete-todo [id]
  (let [resultado
        (jdbc/execute-one!
         db-spec
         ["DELETE FROM todos WHERE id = ?" id])]
    (pos? (:next.jdbc/update-count resultado))))
