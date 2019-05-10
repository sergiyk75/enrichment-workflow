package com.jsitelecom.enrichment.nameextract;

import com.google.gson.Gson;

@SuppressWarnings({"unused"})
class Config
{
    String kafkaBootstrapServers;

    static Config fromJson(String configJson)
    {
        return new Gson().fromJson(configJson, Config.class);
    }

}
