(ns flightgear.core
  (:require [clojure.java.io :as io])
  (:require [clojure.zip :as zip])
  (:require [clojure.xml :as xml])
  (:import [java.net Socket]
           [java.io PrintWriter ByteArrayInputStream StringReader]))

(def connection (atom nil))
(def in (atom nil))
(def out (atom nil))

(defn send-message [msg]
  (doto @out
    (.print (str msg "\r\n"))
    (.flush))
  nil)
  
(defn connect [host port]
  (println "Connecting to" host "on" port)
  (let [s (Socket. host port)]
    (reset! connection s)
    (reset! in (io/reader (.getInputStream s)))
    (reset! out (PrintWriter. (.getOutputStream s)))
    (send-message "data")
    true))

(defn get-property-list []
  (apply str (concat
               (take-while #(not (= "</PropertyList>" %)) (repeatedly #(.readLine @in)))
               "</PropertyList>")))

(defn property-node-to-entry [node]
  (let [tag (node :tag)
        type ((node :attrs) :type)
        content (first (node :content))]
    (cond
      (= type "double") [tag (read-string content)]
      :default [tag content])))

(defn property-list-to-map [property-list]
  (let [list-bytes (ByteArrayInputStream. (.getBytes property-list))
        tree (zip/xml-zip (xml/parse list-bytes))
        properties (zip/children tree)]
    (reduce conj {} (map property-node-to-entry properties))))

(defn request-property-list [property]
  (send-message (str "dump " property))
  (property-list-to-map (get-property-list)))

(defn set-property [property value]
  (send-message (str "set " property " " value)))

(defn read-to-prompt []
  (let [prompt (seq "/> ")
        initial-acc (take (count prompt) (repeatedly #(char (.read @in))))]
    (loop [result []
           acc initial-acc]
      (if (= prompt acc)
        (apply str result)
        (recur (conj result (first acc))
               (conj (vec (rest acc)) (char (.read @in))))))))

(defn ls-output-line-to-entry [line]
  (let [[_ name value type] (re-find #"([a-z-]+).*'([0-9a-z.-]*)'.*\(([a-z]+)\)" line)]
    (if (= "double" type)
      [(keyword name) (read-string value)]
      nil)))

(defn ls-output-to-map [ls-output]
  (with-open [in (io/reader (StringReader. ls-output))]
    (reduce conj {} (map ls-output-line-to-entry (line-seq in)))))

(defn read-properties [directory]
  (send-message "prompt")
  (read-to-prompt)
  (send-message (str "ls " directory))
  (let [raw-result (read-to-prompt)]
    (send-message "data")
    (ls-output-to-map raw-result)))

(defn position []
  (request-property-list "/position"))

(defn orientation []
  (request-property-list "/orientation"))

(defn velocities []
  (request-property-list "/velocities"))

(defn starter! [value]
  (set-property "/controls/switches/starter" (if value "true" "false")))

(defn aileron! [value]
  (set-property "/controls/flight/aileron" value))

(defn elevator! [value]
  (set-property "/controls/flight/elevator" value))

(defn rudder![value]
  (set-property "/controls/flight/rudder" value))

(defn flaps! [value]
  (set-property "/controls/flight/flaps" value))

(defn throttle! [value]
  (set-property "/controls/engines/engine/throttle" value))

(defn indicated-airspeed-kt []
  ((read-properties "/instrumentation/airspeed-indicator") :indicated-speed-kt))

(defn indicated-altitude-ft []
  ((read-properties "/instrumentation/altimeter") :indicated-altitude-ft))

(defn indicated-attitude []
  (let [properties (read-properties "/instrumentation/attitude-indicator")]
    {:indicated-roll-deg (properties :indicated-roll-deg)
     :indicated-pitch-deg (properties :indicated-pitch-deg)}))

(defn indicated-heading-deg []
    ((read-properties "/instrumentation/magnetic-compass") :indicated-heading-deg))