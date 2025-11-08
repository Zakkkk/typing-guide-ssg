(defproject guidetyping "0.1.0-SNAPSHOT"
  :description "A pre processor for my typing guide."
  :url "http://typing.zakventer.com/"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [io.github.nextjournal/markdown "0.7.196"]
                 [hiccup "2.0.0"]
                 [com.hypirion/clj-xchart "0.2.0"]]
  :main ^:skip-aot guidetyping.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
