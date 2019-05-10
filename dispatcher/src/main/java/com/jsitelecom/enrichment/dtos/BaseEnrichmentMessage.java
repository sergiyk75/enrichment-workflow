package com.jsitelecom.enrichment.dtos;

import java.util.UUID;

@SuppressWarnings({"WeakerAccess", "unused"})
public class BaseEnrichmentMessage
{
    public String apiVersion;
    public String collection;
    public String kind;
    public UUID itemID;

    public String toString()
    {
        return String.format("apiVersion:%s, collection:%s, kind: %s, itemID:%s", apiVersion, collection, kind, itemID);
    }
}
