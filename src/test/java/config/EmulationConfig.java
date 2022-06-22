package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/emulation.properties")
public interface EmulationConfig extends Config{

    @Key("deviceHost")
    String deviceHost();

    @Key("platformName")
    String platformName();

    @Key("deviceName")
    String deviceName();

    @Key("platformVersion")
    String platformVersion();

    @Key("appPackage")
    String appPackage();

    @Key("appActivity")
    String appActivity();
}
