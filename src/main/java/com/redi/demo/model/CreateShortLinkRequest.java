package com.redi.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;

public class CreateShortLinkRequest {
    public final URI originalUrl;

    @JsonCreator
    public CreateShortLinkRequest(@JsonProperty("originalUrl") final URI originalUrl) {
        this.originalUrl = originalUrl;
    }
}
