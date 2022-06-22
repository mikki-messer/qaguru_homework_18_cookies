package com.mikkimesser.configuration;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties",
        "file:/tmp/secret.properties", //file is missing, just as an illustration
        "classpath:configuration/apiConfig.properties"})
public interface ApiConfig extends Config {
    @Key("demowebshop_login")
    String demowebshopLogin();

    @Key("demowebshop_password")
    String demowebshopPassword();
}
