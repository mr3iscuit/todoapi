package com.example.todo;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetTodoController {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldReturnATodoWhenDataIsSaved() {
        var requestedId = 99L;
        ResponseEntity<String> response = restTemplate.getForEntity(
            String.format("/todos/%d", requestedId),
            String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isNotNull();
        assertThat(id).isEqualTo((int) requestedId); // TODO handle Long correctly.
    }
}
