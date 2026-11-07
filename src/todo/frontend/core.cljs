

(ns todo.frontend.core
  (:require [reagent.core :as r]
            [reagent.dom.client :as rdom]))

;; Estado reativo
(defonce tarefas (r/atom []))
(defonce nova-tarefa (r/atom ""))
(defonce mensagem-erro (r/atom nil))

;; Busca as tarefas no backend
(defn carregar-tarefas []
  (-> (js/fetch "http://localhost:3000/api/todos")
      (.then (fn [response]
               (if (.-ok response)
                 (.json response)
                 (js/Promise.reject
                  (js/Error. "Erro ao buscar tarefas")))))
      (.then (fn [dados]
               (reset! tarefas
                       (js->clj dados :keywordize-keys true))
               (reset! mensagem-erro nil)))
      (.catch (fn [erro]
                (reset! mensagem-erro
                        "Nao foi possivel carregar as tarefas.")
                (js/console.error erro)))))

;; Envia uma nova tarefa ao backend
(defn adicionar-tarefa []
  (let [titulo (.trim @nova-tarefa)]
    (when (not-empty titulo)
      (-> (js/fetch
           "http://localhost:3000/api/todos"
           (clj->js
            {:method "POST"
             :headers {"Content-Type" "application/json"}
             :body (js/JSON.stringify
                    (clj->js {:title titulo}))}))
          (.then (fn [response]
                   (if (.-ok response)
                     (.json response)
                     (js/Promise.reject
                      (js/Error. "Erro ao criar tarefa")))))
          (.then (fn [_]
                   (reset! nova-tarefa "")
                   (carregar-tarefas)))
          (.catch (fn [erro]
                    (reset! mensagem-erro
                            "Nao foi possivel adicionar a tarefa.")
                    (js/console.error erro)))))))

;; Interface gráfica
(defn lista-tarefas []
  [:div
   [:h1 "Minha Todo List"]

   [:div
    [:input
     {:type "text"
      :placeholder "Digite uma tarefa..."
      :value @nova-tarefa
      :on-change
      #(reset! nova-tarefa
               (-> % .-target .-value))}]

    [:button
     {:on-click adicionar-tarefa}
     "Adicionar"]]

   (when @mensagem-erro
     [:p {:style {:color "red"}} @mensagem-erro])

   [:h2 "Tarefas cadastradas"]

   [:ul
    (for [{:keys [id title completed]} @tarefas]
      ^{:key id}
      [:li
       [:input
        {:type "checkbox"
         :checked (boolean completed)
         :readOnly true}]
       " " title])]])

;; Inicialização do Reagent
(defonce root (atom nil))

(defn ^:export init []
  (println "Frontend inicializado...")

  (when-not @root
    (reset! root
            (rdom/create-root
             (js/document.getElementById "app"))))

  (rdom/render @root [lista-tarefas])
  (carregar-tarefas))
