package com.gridgain.ecosystem.prometheus;

import io.opencensus.exporter.stats.prometheus.PrometheusStatsCollector;
import io.prometheus.client.exporter.HTTPServer;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.lifecycle.LifecycleBean;
import org.apache.ignite.lifecycle.LifecycleEventType;
import org.apache.ignite.resources.IgniteInstanceResource;

import java.io.IOException;

public class Exporter implements LifecycleBean {
    private int port;
    public Exporter(int port) {
        this.port = port;
    }
    private HTTPServer server;

    @IgniteInstanceResource
    Ignite ignite;

    @Override
    public void onLifecycleEvent(LifecycleEventType evt) throws IgniteException {
        if (evt == LifecycleEventType.BEFORE_NODE_START) {

            ignite.log().info("Starting Prometheus collector");

            PrometheusStatsCollector.createAndRegister();
            try {
                server = new HTTPServer("localhost", port, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (evt == LifecycleEventType.BEFORE_NODE_STOP) {
            if (server != null) {
                server.stop();
                server = null;
            }
        }
    }
}
