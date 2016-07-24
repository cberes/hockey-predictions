# hockey-predictions-service

A web service that provides predictions of hockey games. Written in Clojure, built on Ring and Compojure.

## Usage

Source configuration variables, then run

    lein ring server-headless

or

    lein ring uberjar
    java -jar target/hockey-predictions-service-0.1.0-SNAPSHOT-standalone.jar

## License

Copyright © 2016 Corey Beres

Distributed under the GNU General Public License either version 3.0 or (at
your option) any later version.
