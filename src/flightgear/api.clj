(ns flightgear.api
  (:require [flightgear.core :as core]))

(defn connect [host port]
  (core/connect host port))

(defn position []
  (core/position))

(defn orientation []
  (core/orientation))

(defn velocities []
  (core/velocities))

(defn starter! [value]
  (core/starter! value))

(defn aileron! [value]
  (core/aileron! value))

(defn elevator! [value]
  (core/elevator! value))

(defn rudder![value]
  (core/rudder! value))

(defn flaps! [value]
  (core/flaps! value))

(defn throttle! [value]
  (core/throttle! value))