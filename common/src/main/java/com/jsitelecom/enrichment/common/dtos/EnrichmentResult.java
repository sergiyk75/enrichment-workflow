package com.jsitelecom.enrichment.common.dtos;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.UUID;

@SuppressWarnings({"WeakerAccess", "unused"})
public class EnrichmentResult extends BaseEnrichmentMessage
{
    public enum Status
    {
        @SerializedName("Success") SUCCESS,
        @SerializedName("Failed") FAILED,
        @SerializedName("Noop") NOOP
    }

    public UUID requestID;
    public Status status;
    public Date requestTime;
    public Date responseTime;
    public String data;

    public String toString()
    {
        return String.format("%s, requestID:%s, status:%s, requestTime:%s, responseTime:%s, data:%s", super.toString(), requestID, status, requestTime, responseTime, data);
    }
}
