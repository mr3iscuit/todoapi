package com.biscuit.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class SerializationDeserialization {
    @Autowired
    private JacksonTester<Todo> json;

    @Test
    void todoSerializationTest() throws IOException {
        Todo todo = new Todo(99L, "Test", false);

        // assertThat(json.write(todo)).isStrictlyEqualToJson("expected.json");

        assertThat(json.write(todo)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(todo)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(99); // TODO handle long values correctly

        assertThat(json.write(todo)).hasJsonPathStringValue("@.title");
        assertThat(json.write(todo)).extractingJsonPathStringValue("@.title")
                .isEqualTo("Test");

        assertThat(json.write(todo)).hasJsonPathBooleanValue("@.completed");
        assertThat(json.write(todo)).extractingJsonPathBooleanValue("@.completed")
                .isEqualTo(false);
    }

    @Test
    void todoDeserializationTest() throws IOException {
        Todo todo = new Todo(99L, "Hello", true);

        String expected = """
                {
                    "id":99,
                    "title":"Hello",
                    "completed":true
                }
                """;

        assertThat(json.parse(expected))
                .isEqualTo(todo);

        assertThat(json.parseObject(expected).completed()).isEqualTo(true);
    }
}
