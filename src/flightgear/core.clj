(ns flightgear.core
  (:require [clojure.java.io :as io])
  (:import [java.net Socket]
           [java.io PrintWriter]))

(def connection (atom nil))
(def in (atom nil))
(def out (atom nil))

(defn send-message [msg]
  (doto @out
    (.print msg "\r\n")
    (.flush)))
  
(defn connect [host port]
  (println "Connecting to" host "on" port)
  (let [s (Socket. host port)]
    (reset! connection s)
    (reset! in (io/reader (.getInputStream s)))
    (reset! out (PrintWriter. (.getOutputStream s)))
    (send-message "data")
    true))

(defn get-property-list []
  (take-while #(not (= "</PropertyList>" %)) (repeatedly #(.readLine @in))))

(defn position []
  (send-message "dump /position")
  (get-property-list))