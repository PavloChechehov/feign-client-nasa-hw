package com.example.controller;

import com.example.service.NasaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.Objects;

@RestController
@RequestMapping("/mars")
@RequiredArgsConstructor
public class NasaController {

    private final NasaClient nasaClient;
    private static final String API_KEY = "DEMO_KEY";

    @GetMapping(value = "/pictures/largest", produces = "image/jpeg")
    public byte[] getLargestPicture(@RequestParam Integer sol,
                                    @RequestParam(required = false) String camera) {

        var restTemplate = new RestTemplate();
        var photos = nasaClient.getPhotos(sol, camera, API_KEY);
        var maxContentLengthHeaders = photos.getPhotos()
                .stream()
                .map(photo -> {
                    var imgSrc = photo.getImgSrc();
                    return restTemplate.headForHeaders(imgSrc);
                })
                .max(Comparator.comparingLong(HttpHeaders::getContentLength));


        return maxContentLengthHeaders
                .map(httpHeaders -> restTemplate.getForEntity(Objects.requireNonNull(httpHeaders.getLocation()), byte[].class).getBody())
                .orElseThrow(IllegalArgumentException::new);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> checkException(){
        return ResponseEntity.status(404).body("No pictures found");
    }
}

