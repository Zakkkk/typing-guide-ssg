(ns guidetyping.chart
  (:require [com.hypirion.clj-xchart :as c])
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
