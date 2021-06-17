package homework4;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:ServerConfig.properties")
public interface ServerConfig extends Config {
    String homepage();

    @DefaultValue("chrome")
    String browser();

    String email();

    String password();
}
