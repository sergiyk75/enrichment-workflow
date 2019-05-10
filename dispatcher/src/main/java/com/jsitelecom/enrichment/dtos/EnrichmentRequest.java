package com.jsitelecom.enrichment.dtos;

import java.util.Date;
import java.util.UUID;

@SuppressWarnings({"WeakerAccess", "unused"})
public class EnrichmentRequest extends BaseEnrichmentMessage
{
    public UUID requestID;
    public Date requestTime;
    public String data;

    public String toString()
    {
        return String.format("%s, requestID:%s, requestTime:%s, data:%s", super.toString(), requestID, requestTime, data);
    }
}
