package com.epam.vital.gym_crm.helpers.json_deserialization;

import com.epam.vital.gym_crm.model.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class UserDeserializer extends StdDeserializer<User> {

    public UserDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public User deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        Long id = node.get("id").asLong();
        String firstName = node.get("firstName").asText();
        String lastName = node.get("lastName").asText();

        return User.builder().id(id).firstName(firstName).lastName(lastName).build();
    }
}