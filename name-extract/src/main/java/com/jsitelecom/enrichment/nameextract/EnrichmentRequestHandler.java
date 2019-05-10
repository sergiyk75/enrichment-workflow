package com.jsitelecom.enrichment.nameextract;

import com.jsitelecom.enrichment.common.dtos.EnrichmentRequest;
import com.jsitelecom.enrichment.common.dtos.EnrichmentResult;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnrichmentRequestHandler implements ValueTransformer<EnrichmentRequest, EnrichmentResult>
{
    private static final Logger logger = LoggerFactory.getLogger(EnrichmentRequestHandler.class);

    @Override
    public void init(ProcessorContext context)
    {
    }

    @Override
    public EnrichmentResult transform(EnrichmentRequest request)
    {
        logger.trace(String.format("request: %s", request));

        try
        {
            // TODO handle enrichment request and return enrichment results
            return null;
        }
        catch (Exception e)
        {
            // TODO handle errors
            logger.error(String.format("Failed to process enrichment request: %s", request), e);
            return null;
        }
    }

    @Override
    public void close()
    {
    }
}
