(ns com.ashafa.clutch.utils
  (:require [clojure.java.io :as io]
            [cemerick.url :as url])
  (:import java.net.URLEncoder
           java.lang.Class
           [java.io File]))


(defn- encode-parts [parts]
  "Encode url parts and make sure we do not url-encode the _design/ part
  as this causes a redirect in couchdb, which makes the subsequent authentication
  fail"
  (when-let [first-part (some-> (first parts) name)]
    (if (-> first-part (.startsWith "_design/"))
      ;;remove _design/ bit from the first part and cons "_design" to the map.
      ;;url/url will take care of the rest
      (cons "_design" (map (comp url/url-encode str) (cons (-> first-part (.substring 8)) (rest parts))))
      (map (comp url/url-encode str) parts))))

(defn url
  "Thin layer on top of cemerick.url/url that defaults otherwise unqualified
   database urls to use `http://localhost:5984` and url-encodes each URL part
   provided."
  [& [base & parts :as args]]
  (try
    (apply url/url base (encode-parts parts))
    (catch java.net.MalformedURLException e
      (apply url/url "http://localhost:5984" (encode-parts args)))))

(defn server-url
  [db]
  (assoc db :path nil :query nil))

(defn get-mime-type
  [^File file]
  (java.net.URLConnection/guessContentTypeFromName (.getName file)))

;; TODO should be replaced with a java.io.Closeable Seq implementation and used
;; in conjunction with with-open on the client side
(defn read-lines
  "Like clojure.core/line-seq but opens f with reader.  Automatically
  closes the reader AFTER YOU CONSUME THE ENTIRE SEQUENCE.

  Pulled from clojure.contrib.io so as to avoid dependency on the old io
  namespace."
  [f]
  (let [read-line (fn this [^java.io.BufferedReader rdr]
                    (lazy-seq
                     (if-let [line (.readLine rdr)]
                       (cons line (this rdr))
                       (.close rdr))))]
    (read-line (io/reader f))))


