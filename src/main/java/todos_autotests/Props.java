package todos_autotests;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:src/test/resources/test.properties"
})
public interface Props extends Config {

    @Key("todos_endpoint")
    String toDosEndpoint();


}
