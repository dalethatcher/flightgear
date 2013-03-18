(ns flightgear.api
  (:require [flightgear.core :as core]))

(defn connect [host port]
  (core/connect host port))

