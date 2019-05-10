/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jsitelecom.enrichment.nameextract;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jsitelecom.enrichment.common.Enrichment;
import com.jsitelecom.enrichment.common.JsonSerde;
import com.jsitelecom.enrichment.common.dtos.EnrichmentRequest;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * In this example, we implement a simple LineSplit program using the high-level Streams DSL
 * that reads from a source topic "streams-plaintext-input", where the values of messages represent lines of text,
 * and writes the messages as-is into a sink topic "streams-pipe-output".
 */
class Bootstrap
{
    public static void main(String[] args) throws IOException
    {
        String configPath = args.length > 0 ? args[0] : "local-run-config.json";
        String configJson = new String(Files.readAllBytes(Paths.get(configPath)));
        Config config = Config.fromJson(configJson);
        Injector injector = Guice.createInjector(new DependencyModule(config));

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "enrichment-name-extract");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, config.kafkaBootstrapServers);

        final StreamsBuilder builder = new StreamsBuilder();

        builder.stream(Enrichment.Topic.ENRICHMENT_REQUESTS, Consumed.with(Serdes.String(), JsonSerde.getSerde(EnrichmentRequest.class)))
               .transformValues(() -> injector.getInstance(EnrichmentRequestHandler.class))
               .to(Enrichment.Topic.ENRICHMENT_RESULTS);

        final Topology topology = builder.build();
        System.out.println(topology.describe());

        final KafkaStreams streams = new KafkaStreams(topology, props);

        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook")
        {
            @Override
            public void run()
            {
                streams.close();
                latch.countDown();
            }
        });

        try
        {
            streams.start();
            latch.await();
        }
        catch (Throwable e)
        {
            System.exit(1);
        }
        System.exit(0);
    }
}
