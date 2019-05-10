package com.jsitelecom.enrichment.common;

import com.google.gson.Gson;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class JsonSerde<T> implements Deserializer<T>, Serializer<T>
{
    private final Gson gson = new Gson();
    private final Class<T> clazz;

    /**
     * Default constructor needed by Kafka
     */
    private JsonSerde(Class<T> clazz)
    {
        this.clazz = clazz;
    }

    @Override
    public void configure(Map<String, ?> props, boolean isKey)
    {
    }

    @Override
    public byte[] serialize(String topic, T data)
    {
        if (data == null)
            return null;

        try
        {
            return gson.toJson(data).getBytes();
        }
        catch (Exception e)
        {
            throw new SerializationException("Error serializing JSON message", e);
        }
    }

    @Override
    public T deserialize(String topic, byte[] bytes)
    {
        if (bytes == null)
            return null;

        T data;
        try
        {
            data = gson.fromJson(new String(bytes), clazz);
        }
        catch (Exception e)
        {
            throw new SerializationException(e);
        }

        return data;
    }

    @Override
    public void close()
    {

    }


    public static <T> Serde<T> getSerde(Class<T> clazz)
    {
        final JsonSerde<T> jsonSerde = new JsonSerde<>(clazz);
        return Serdes.serdeFrom(jsonSerde, jsonSerde);
    }
}
