package com.mikkimesser.configuration;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties",
        "file:/tmp/secret.properties", //file is missing, just as an illustration
        "classpath:configuration/${env}.properties"})
public interface WebConfig extends Config {
    @Key("browser")
    String browser();

    @Key("browserVersion")
    String browserVersion();

    @Key("remoteWebDriver")
    String remoteWebDriver();

    @Key("selenoid_login")
    String selenoidLogin();

    @Key("selenoid_password")
    String selenoidPassword();
}
