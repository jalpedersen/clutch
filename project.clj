(defproject com.ashafa/clutch "0.5.0-RC1"
  :description "A Clojure library for Apache CouchDB."
  :url "https://github.com/clojure-clutch/clutch/"
  :license {:name "BSD"
            :url "http://www.opensource.org/licenses/BSD-3-Clause"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-http "2.2.0"]
                 [cheshire "5.6.3"]
                 [commons-codec "1.9"]
                 [com.cemerick/url "0.1.1"]
                 
                 [org.clojure/clojurescript "0.0-2156" :optional true
                  :exclusions [com.google.code.findbugs/jsr305
                               com.googlecode.jarjar/jarjar
                               junit
                               org.apache.ant/ant
                               org.mozilla/rhino]]]
  :profiles {:dev {}
             :1.5.0 {:dependencies [[org.clojure/clojure "1.5.0"]]}
             :1.5.1 {:dependencies [[org.clojure/clojure "1.5.1"]]}}
  :aliases  {"all" ["with-profile" "dev:dev,1.5.0:dev,1.5.1"]}
  :min-lein-version "2.0.0"
  :test-selectors {:default #(not= 'test-docid-encoding (:name %))
                   :all (constantly true)})
