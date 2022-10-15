package com.example.service;

import com.example.model.NasaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "nasa-client", url = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos")
public interface NasaClient {

    @GetMapping
    NasaResponse getPhotos(@RequestParam Integer sol,
                           @RequestParam(required = false) String camera,
                           @RequestParam("api_key") String apiKey);

}
