# Viewing GridGain metrics in Prometheus
## Overview

[Prometheus](https://prometheus.io/) is a popular monitoring tool supported
by the [Cloud Native Computing Foundation](https://www.cncf.io/), the same
people as Kubernetes. We often see clients trying to integrate it with
GridGain and Apache Ignite. This project presents a working example.

## Setup

Enable GridGain's Open Census support by:

1. Go into $IGNTITE_HOME/libs/optional
2. Move the ignite-opencensus directory up a level (`mv ignite-opencensus ..`)

(This is already the case for some recent versions of GridGain. It's still
worth checking.)

## Building

Build a copy of the "exporter":

```shell
mvn package
```

This is the code that links the Open Census metrics with Prometheus.

## Running

Start GridGain or Apache Ignite as follows:

```shell
export USER_LIBS=/full/path/to/ignite-prometheus-1.0-SNAPSHOT-jar-with-dependencies.jar
$IGNITE_HOME/bin/ignite.sh .../src/main/resources/ignite-prometheus.xml
```

You can then open http://localhost:9000 in your web browser to see the raw
statistics (wait a minute or two if only a few are present).

Start Prometheus with the following command:

```shell
prometheus --config.file=ignite-prometheus.yaml
```