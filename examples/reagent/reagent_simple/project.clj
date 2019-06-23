(defproject onion-reagent-simple "0.1.0-SNAPSHOT"
  :description "A simple example of using onion with reagent"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.520"]
                 [onion-simple "0.1.0-SNAPSHOT"]
                 [onion-reagent "0.1.0-SNAPSHOT"]
                 [reagent "0.8.1"]]
  :profiles {:dev {:dependencies [[com.bhauman/figwheel-main "0.2.0"]
                                  [com.bhauman/rebel-readline-cljs "0.1.4"]]
                   :resource-paths ["target" "resources"]
                   :aliases {"fig" ["trampoline" "run" "-m" "figwheel.main"]
                             "build-dev" ["trampoline" "run" "-m" "figwheel.main" "-b" "dev" "-r"]}}}
  :clean-targets ^{:protect false} ["target"])
