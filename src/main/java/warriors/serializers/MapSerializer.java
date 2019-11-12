package warriors.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import warriors.contracts.Map;

import java.io.IOException;

public class MapSerializer extends StdSerializer<Map> {

    public MapSerializer(Class<Map> t) {
        super(t);
    }

    public void serialize(Map map, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
            throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", map.getName());
        jsonGenerator.writeNumberField("numberOfCases", map.getNumberOfCase());
        jsonGenerator.writeNumberField("index", 0);
        jsonGenerator.writeEndObject();
    }
}