package com.jsitelecom.enrichment.dtos;

import java.util.List;

/*
 * Data transfer object used to serialize messages to and from the Kafka term topic
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class ItemEnrichmentRequest extends BaseEnrichmentMessage
{
    public EnrichmentOptions enrichmentOptions;
    public String item;

    public String toString()
    {
        return String.format("%s, enrichmentOptions:%s, item:%s", super.toString(), enrichmentOptions, item);
    }

    public class EnrichmentOptions
    {
        public List<String> excludeStages;

        public String toString()
        {
            if (excludeStages == null)
                return null;
            return excludeStages.toString();
        }
    }
}
