package com.jsitelecom.enrichment;

import com.google.inject.AbstractModule;


/**
 * Used to provide dependency injection
 */
class DependencyModule extends AbstractModule
{
    private final Config config;

    DependencyModule(Config config)
    {
        this.config = config;
    }

    @Override
    protected void configure()
    {
        bind(Config.class).toInstance(config);
    }
}
