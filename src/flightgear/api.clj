(ns flightgear.api
  (:require [flightgear.core :as core]))

(defn connect "connect to the running flight gear using the telnet api" 
  [host port]
  (core/connect host port))

(defn position "current position data as a map"
  []
  (core/position))

(defn orientation "current orientation data as a map" 
  []
  (core/orientation))

(defn velocities "current velocity data as a map" 
  []
  (core/velocities))

(defn starter! "true/false to turn the starter key" 
  [value]
  (core/starter! value))

(defn aileron! "-1 to 1 to set aileron" 
  [value]
  (core/aileron! value))

(defn elevator! "-1 to 1 to set elevator" 
  [value]
  (core/elevator! value))

(defn rudder! "-1 to 1 to set rudder"
  [value]
  (core/rudder! value))

(defn flaps! "0 to 1 to set flaps" 
  [value]
  (core/flaps! value))

(defn throttle! "0 to 1 to set throttle"
  [value]
  (core/throttle! value))