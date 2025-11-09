(ns guidetyping.core
  (:gen-class)
  (:require [nextjournal.markdown :as md]
            [hiccup2.core :as h]
            [hiccup.page :as hp]
            [guidetyping.chart]))

(def markdown-file-contents
  (apply str (map slurp ["markdown/intro.md"
                         "markdown/basics.md"
                         "markdown/alt.md"
                         "markdown/beginner.md"
                         "markdown/inter.md"
                         "markdown/general.md"])))

(defn get-headings [md-contents]
  (filter (comp #{:h2 :h3} first) md-contents))

(defn get-heading-links [md-contents]
  (for [[tag properties text] (get-headings md-contents)]
    [:a
     {:class ({:h2 "nav-1"
               :h3 "nav-2"} tag)
      :href (str "#" (:id properties))}
     text]))

(defn create-nav [md-contents]
  [:nav [:b "Contents"] [:hr]
   (get-heading-links md-contents)])

(defn create-mobile-headings [md-contents]
  [:div.mobile-headings {:tabindex "1"}
   [:i.db {:tabindex "1"}]
   [:a.button.dropdown-button
    [:span.material-symbols-outlined "toc"]
    "View Contents"
    [:span.material-symbols-outlined "arrow_drop_down"]]
   [:div.dropdown-content
    (get-heading-links md-contents)]])

(defn site-html [md-contents]
  (h/html
   (h/raw "<!doctype html>")
   [:html
    [:head
     [:title "Zak's Typing Guide"]
     [:link {:rel "stylesheet" :href "https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"}]
     [:meta {:charset "utf-8"}]
     [:meta {:name "viewport"
             :content "width=device-width, initial-scale=1.0"}]
     (hp/include-css "css/normalize.css" "css/style.css")]
    [:body
     [:main
      (create-mobile-headings md-contents)
      (create-nav md-contents)
      [:article md-contents]]]]))

(defn create-html [html]
  (spit "output/index.html"
        (str (site-html html))))

(defn -main [& [mode]]
  (let [commands {"charts" guidetyping.chart/generate-charts
                  "md" #(create-html (rest (md/->hiccup markdown-file-contents)))}]
    (doseq [[command action] commands]
      (when (or (= mode command)
                (= mode "all"))
        (action)))))
