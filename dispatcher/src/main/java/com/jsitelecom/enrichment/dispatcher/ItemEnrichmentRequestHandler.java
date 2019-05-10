package com.jsitelecom.enrichment.dispatcher;

import com.jsitelecom.enrichment.common.Enrichment;
import com.jsitelecom.enrichment.common.dtos.EnrichmentRequest;
import com.jsitelecom.enrichment.common.dtos.ItemEnrichmentRequest;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.UUID;

public class ItemEnrichmentRequestHandler implements Transformer<String, ItemEnrichmentRequest, Iterable<KeyValue<String, EnrichmentRequest>>>
{
    private static final Logger logger = LoggerFactory.getLogger(ItemEnrichmentRequestHandler.class);

    private KeyValueStore<UUID, String> itemStore;

    @Override
    @SuppressWarnings("unchecked")
    public void init(ProcessorContext context)
    {
        itemStore = (KeyValueStore<UUID, String>)context.getStateStore(Enrichment.Store.ITEM);
    }

    @Override
    public Iterable<KeyValue<String, EnrichmentRequest>> transform(String key, ItemEnrichmentRequest itemEnrichmentRequest)
    {
        logger.trace(String.format("ItemEnrichmentRequest: %s", itemEnrichmentRequest));

        try
        {
            // TODO handle item requests and return enrichment requests based on the graph
            return Collections.emptyList();
        }
        catch (Exception e)
        {
            logger.error(String.format("Failed to process item enrichment request: %s", itemEnrichmentRequest), e);
            return Collections.emptyList();
        }
    }

    @Override
    public void close()
    {
    }
}
