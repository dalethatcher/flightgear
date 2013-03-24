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

(defn indicated-airspeed-kt [] "indicated airspeed in knots"
  (core/indicated-airspeed-kt))

(defn indicated-altitude-ft [] "indicated altitude in feet"
  (core/indicated-altitude-ft))

(defn indicated-attitude [] "map of attitude in degrees, with keys :indicated-roll-deg and :indicated-pitch-deg"
  (core/indicated-attitude))

(defn indicated-heading-deg [] "indicated heading in degrees from magnetic compass"
  (core/indicated-heading-deg))

(defn engine-running? [] "true if the engine has started"
  (core/engine-running?))