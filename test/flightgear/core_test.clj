(ns flightgear.core-test
  (use clojure.test)
  (require [flightgear.core :as fg]))

(def property-list (str "<?xml version=\"1.0\"?>"
                        "<PropertyList>"
                        "  <longitude-deg type=\"double\">-122.3933408</longitude-deg>"
                        "  <latitude-deg type=\"double\">37.62871089</latitude-deg>"
                        "  <altitude-ft type=\"double\">6.017082594</altitude-ft>"
                        "  <ground-elev-m type=\"double\">0.4883526054</ground-elev-m>"
                        "  <ground-elev-ft type=\"double\">1.602206711</ground-elev-ft>"
                        "</PropertyList>"))

(deftest can-parse-property-list
  (let [properties (fg/property-list-to-map property-list)]
    (is (= (properties :longitude-deg) -122.3933408))))