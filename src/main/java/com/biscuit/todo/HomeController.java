package com.biscuit.todo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.ResponseEntity;

@RestController("/")
public class HomeController {

    @RequestMapping(value = "home", method = RequestMethod.GET)
    ResponseEntity<String> home() {
        return ResponseEntity.ok("Hey!");
    }

}
