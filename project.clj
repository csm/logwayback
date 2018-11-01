(defproject com.github.csm/logwayback "0.1.0-SNAPSHOT"
  :description "Colorful, structured logback output"
  :url "https://github.com/csm/logwayback"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :java-source-paths ["src"]
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [ch.qos.logback/logback-core "1.1.8"]
                 [ch.qos.logback/logback-classic "1.1.8"]
                 [mvxcvi/puget "1.0.3"]
                 [environ "1.1.0"]]
  :release-tasks [["vcs" "assert-committed"]
                  ["change" "version"
                   "leiningen.release/bump-version" "release"]
                  ["vcs" "commit"]
                  ["vcs" "tag"]
                  ["deploy" "clojars"]
                  ["change" "version" "leiningen.release/bump-version"]
                  ["vcs" "commit"]
                  ["vcs" "push"]])
