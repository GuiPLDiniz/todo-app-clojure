
(ns todo.frontend.core
  (:require [reagent.core :as r]
            [reagent.dom.client :as rdom]))

;; Estado reativo da aplicação
(defonce tarefas (r/atom []))
(defonce nova-tarefa (r/atom ""))

(defn adicionar-tarefa []
  (let [titulo (.trim @nova-tarefa)]
    (when (not-empty titulo)
      (swap! tarefas conj
             {:id (random-uuid)
              :title titulo
              :completed false})
      (reset! nova-tarefa ""))))

(defn lista-tarefas []
  [:div
   [:h2 "Minhas tarefas"]

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

   [:ul
    (for [{:keys [id title completed]} @tarefas]
      ^{:key (str id)}
      [:li
       [:input
        {:type "checkbox"
         :checked completed
         :readOnly true}]
       " " title])]])

(defonce root (atom nil))

(defn ^:export init []
  (println "Frontend inicializado...")
  (when-not @root
    (reset! root
            (rdom/create-root
             (js/document.getElementById "app"))))
  (rdom/render @root [lista-tarefas]))
