(defproject com.taoensso/sente "1.16.2"
  :author "Peter Taoussanis <https://www.taoensso.com>"
  :description "Realtime web comms for Clojure/Script"
  :url "https://github.com/ptaoussanis/sente"
  :license {:name         "Eclipse Public License"
            :url          "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments     "Same as Clojure"}
  :min-lein-version "2.3.3"
  :global-vars {*warn-on-reflection* true
                *assert*             true}

  :dependencies
  [[org.clojure/core.async "1.3.622"]
   [com.taoensso/encore "3.19.0"]
   [org.java-websocket/Java-WebSocket "1.5.2"]
   [org.clojure/tools.reader "1.3.6"]
   [com.taoensso/timbre "5.1.2"]]

  :plugins
  [[lein-pprint "1.3.2"]
   [lein-ancient "0.7.0"]
   [lein-codox "0.10.8"]
   [lein-cljsbuild "1.1.8"]]

  :profiles
  {;; :default [:base :system :user :provided :dev]
   :server-jvm {:jvm-opts ^:replace ["-server"]}
   :provided   {:dependencies [[org.clojure/clojure "1.10.3"]
                               [org.clojure/clojurescript "1.10.879"]]}
   :1.8        {:dependencies [[org.clojure/clojure "1.10.3"]]}
   :1.9        {:dependencies [[org.clojure/clojure "1.10.3"]]}
   :1.10       {:dependencies [[org.clojure/clojure "1.10.3"]]}
   ;; :depr     {:jvm-opts ["-Dtaoensso.elide-deprecated=true"]}
   :dev        [:1.10 :test :server-jvm :depr]
   :test       {:dependencies
                [[com.cognitect/transit-clj "1.0.324"]
                 [com.cognitect/transit-cljs "0.8.269"]
                 [org.clojure/test.check "1.1.0"]
                 [http-kit "2.5.3"]
                 [org.immutant/web "2.1.10"]
                 [nginx-clojure "0.5.2"]
                 [aleph "0.4.6"]
                 [macchiato/core "0.2.22"]
                 [luminus/ring-undertow-adapter "1.2.3"]
                 [info.sunng/ring-jetty9-adapter "0.15.3"]]}}

  :cljsbuild
  {:test-commands {"node"    ["node" :node-runner "target/main.js"]
                   "phantom" ["phantomjs" :runner "target/main.js"]}
   :builds
                  [{:id           :main
                    :source-paths ["src" "test"]
                    :compiler     {:output-to     "target/main.js"
                                   :optimizations :advanced
                                   :pretty-print  false}}]}

  :test-paths ["test" "src"]

  :aliases
  {"start-dev"  ["with-profile" "+dev" "repl" ":headless"]
   "build-once" ["cljsbuild" "once"]
   "deploy-lib" ["do" "build-once," "deploy" "clojars," "install"]
   "test-all"   ["do" "clean,"
                 "with-profile" "+1.10:+1.9:+1.8" "test,"
                 "with-profile" "+test" "cljsbuild" "test"]}

  :repositories
  {"sonatype-oss-public"
   "https://oss.sonatype.org/content/groups/public/"})
