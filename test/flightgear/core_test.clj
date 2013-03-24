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

(def ls-output (str "serviceable =\t'true'\t(bool)\r\n"
                    "indicated-speed-kt =\t'2.136597038'\t(double)\r\n"
                    "true-speed-kt =\t'2.155335677'\t(double)\r\n"
                    "indicated-mach =\t'0.003230377164'\t(double)\r\n"))

(deftest can-parse-ls-output
  (let [properties (fg/ls-output-to-map ls-output)]
    (is (= (properties :indicated-speed-kt) 2.136597038))))