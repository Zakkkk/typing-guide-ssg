(ns guidetyping.core
  (:gen-class)
  (:require [nextjournal.markdown :as md]
            [hiccup2.core :as h]
            [hiccup.page :as hp]
            [com.hypirion.clj-xchart :as c])
  (:import (java.awt Color)))

(def page-colors {:bg "#fbfbfb" :fg "#222"})

(defn generate-charts []
  (c/spit
   (c/category-chart
    {"#" {"0-1.9 Weeks" 2 "2-4 Weeks" 3 "1-1.9 Months" 6 "2-2.9 Months" 4 "3-4.9 Months" 1 "5-7.9 Months" 0 "8-11.9 Months" 1 "12+ Months" 1}}
    {:title "How long did it take to reach your old QWERTY speeds?"
     :legend {:visible? false}
     :theme :ggplot2
     :plot {:background-color (Color/decode "#eeeeee")
            :grid-lines {:visible? false}}
     :axis {:ticks {:labels {:color (Color/decode (:fg page-colors))}}}
     :chart {:font-color (Color/decode (:fg page-colors))
             :background-color (Color/decode (:bg page-colors))}
     ;; :series [{:color :red}]
     :y-axis {:tick-mark-spacing-hint 100}
     :x-axis {:label {:rotation 30}
              :order ["0-1.9 Weeks" "2-4 Weeks" "1-1.9 Months" "2-2.9 Months" "3-4.9 Months" "5-7.9 Months" "8-11.9 Months" "12+ Months"]}})
   "output/img/return-to-qwerty.svg"))

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
  (map (fn [element]
         [:a
          {:class (condp = (first element)
                    :h2 "nav-1"
                    :h3 "nav-2")
           :href (str "#" (:id (second element)))}
          (nth element 2)]) (get-headings md-contents))) ;; .nav-item should be fixed to add into side nav

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

(defn -main [mode]
  (condp some [mode]
    #{"charts" "all"} (generate-charts)
    #{"md" "all"} (create-html (rest (md/->hiccup markdown-file-contents)))))
