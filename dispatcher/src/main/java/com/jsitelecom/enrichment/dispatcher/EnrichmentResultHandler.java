package com.jsitelecom.enrichment.dispatcher;

import com.jsitelecom.enrichment.common.Enrichment;
import com.jsitelecom.enrichment.common.dtos.EnrichmentRequest;
import com.jsitelecom.enrichment.common.dtos.EnrichmentResult;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.UUID;

public class EnrichmentResultHandler implements Transformer<String, EnrichmentResult, Iterable<KeyValue<String, EnrichmentRequest>>>
{
    private static final Logger logger = LoggerFactory.getLogger(EnrichmentResultHandler.class);

    private KeyValueStore<UUID, String> itemStore;

    @Override
    @SuppressWarnings("unchecked")
    public void init(ProcessorContext context)
    {
        itemStore = (KeyValueStore<UUID, String>)context.getStateStore(Enrichment.Store.ITEM);
    }

    @Override
    public Iterable<KeyValue<String, EnrichmentRequest>> transform(String value, EnrichmentResult enrichmentResult)
    {
        logger.trace(String.format("enrichmentResult: %s", enrichmentResult));

        try
        {
            // TODO handle enrichment results and return enrichment requests based on the graph
            return Collections.emptyList();
        }
        catch (Exception e)
        {
            // TODO handle errors
            logger.error(String.format("Failed to process enrichment result: %s", enrichmentResult), e);
            return Collections.emptyList();
        }
    }

    @Override
    public void close()
    {
    }
}
