(ns clojure-filtering.core ;; namespace
  (:require [clojure.string :as str])
  (:require [clojure.data.json :as json])
  (:gen-class))


(defn -main []
  (println "Pick a Category:")
  (println "Furniture")
  (println "Alcohol")
  (println "Toiletries")
  (println "Shoes")
  (println "Food")
  (println "Jewelry")
  (let [purchases (slurp "purchases.csv") ;; stores file into purchases
        purchases (str/split-lines purchases) ;; splits purchases object by line
        purchases (map (fn [line] ;; pass purchases as param and remove commas
                         (str/split line #","))
                       purchases)
        header (first purchases)
        purchases (rest purchases)
        purchases (map (fn [line]
                         (zipmap header line))
                       purchases)
        category (read-line)
        purchases (filter (fn [line]
                            (= (get line "category") category))
                          purchases)
        file-json (json/write-str purchases)]
    (spit (str "filtered_purchases_" category ".json") file-json)))