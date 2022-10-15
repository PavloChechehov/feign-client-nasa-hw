package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Photo {

    @JsonProperty("img_src")
    private String imgSrc;
    private Integer sol;
    private Camera camera;

}
