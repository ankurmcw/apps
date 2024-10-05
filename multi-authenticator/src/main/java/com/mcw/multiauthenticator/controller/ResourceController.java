package com.mcw.multiauthenticator.controller;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class ResourceController {

    @GetMapping(path = "/{platform}/api/v1/resource", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getResources(@PathVariable("platform") String platform) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of(
                        "platform", platform,
                        "message", "Hello World!"
                ));
    }
}
