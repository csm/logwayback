(ns logwayback.core
  (:import [ch.qos.logback.classic.spi ILoggingEvent]
           [java.util Date]
           [java.text SimpleDateFormat]))

(def date-format (SimpleDateFormat. "yyyy-MM-dd HH:mm:ss"))

(defn encode-log-event
  [^ILoggingEvent event]
  (with-out-str
    (puget/with-options
      {:print-color (some? (#{"true" "yes" "t"} (get env :colored-logging)))
       :width 100
       :sort-keys false
       :color-scheme {:delimiter       [:white]
                      :tag             [:yellow]
                      :nil             [:bold :white]
                      :boolean         [:green]
                      :number          [:cyan]
                      :string          [:bold :magenta]
                      :character       [:bold :magenta]
                      :keyword         [:white]
                      :symbol          [:white]
                      :function-symbol [:bold :blue]
                      :class-delimiter [:blue]
                      :class-name      [:bold :blue]
                      :error-level     [:bold :bg-red :black]
                      :warn-level      [:bold :bg-yellow :black]
                      :other-level     [:cyan]
                      :exception       [:bold :red]
                      :exception-msg   [:bold :yellow]}}
      (let [fmt-date (.format date-format (Date. (.getTimeStamp event)))
            fmt-level (str " " (.getLevel event) " ")
            pfx-width (dec (+ (count fmt-date) (count fmt-level) (count (.getLoggerName event)) (* 3 3)))]
        (print fmt-date)
        (print " - ")
        (condp = (.getLevel event)
          Level/ERROR (print (color/text puget/*options* :error-level fmt-level))
          Level/WARN (print (color/text puget/*options* :warn-level fmt-level))
          (print (color/text puget/*options* :other-level fmt-level)))
        (print " - ")
        (print (color/text puget/*options* :symbol (.getLoggerName event)))
        (print " - ")
        (let [message (.getFormattedMessage event)]
          (if-let [data (when (= \{ (first message))
                          (try (edn/read-string message)
                               (catch Exception _ nil)))]
            (do (let [msg (string/split-lines (puget/pprint-str data))
                      prefix (string/join (repeat pfx-width \space))]
                  (println (first msg))
                  (doseq [m (rest msg)] (println prefix m))))
            (println message)))
        (when-let [e ^IThrowableProxy (.getThrowableProxy event)]
          (prn)
          (print (color/text puget/*options* :exception (.getClassName e)))
          (when-let [message (.getMessage e)]
            (print ": ")
            (println (color/text puget/*options* :exception-msg message)))
          (doseq [element (.getStackTraceElementProxyArray e)]
            (println "  " (color/text puget/*options* :exception-msg (str element)))))))))