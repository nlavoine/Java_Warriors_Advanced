package warriors.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import warriors.contracts.Hero;

import java.io.IOException;

public class HeroSerializer extends StdSerializer<Hero> {

    public HeroSerializer(Class<Hero> t) {
        super(t);
    }

    public void serialize(Hero hero, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
            throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", hero.getName());
        jsonGenerator.writeNumberField("life", hero.getLife().asInt());
        jsonGenerator.writeNumberField("attack", hero.getAttackLevel().asInt());
        jsonGenerator.writeNumberField("index", hero.getName() == "Guerrier"? 0 : 1);
        jsonGenerator.writeEndObject();
    }
}