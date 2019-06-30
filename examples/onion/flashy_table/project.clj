(defproject onion-flashy-table "0.1.0-SNAPSHOT"
  :description "A table that changes colour when clicked."
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.520"]
                 <<ONION_DEPENDENCY>>]
  :profiles {:dev {:dependencies [[com.bhauman/figwheel-main "0.2.0"]
                                  [com.bhauman/rebel-readline-cljs "0.1.4"]]
                   :resource-paths ["target"]
                   :aliases {"fig" ["trampoline" "run" "-m" "figwheel.main"]
                             "build-dev" ["trampoline" "run" "-m" "figwheel.main" "-b" "dev" "-r"]}}}
  :source-paths ["src"]
  :clean-targets ^{:protect false} ["target"])
