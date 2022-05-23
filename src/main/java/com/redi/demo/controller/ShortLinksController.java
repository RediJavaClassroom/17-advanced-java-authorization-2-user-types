package com.redi.demo.controller;

import com.redi.demo.model.CreateShortLinkRequest;
import com.redi.demo.model.ShortLink;
import com.redi.demo.services.ShortLinksService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortLinksController {

  private final ShortLinksService shortLinksService;

  @Autowired
  public ShortLinksController(final ShortLinksService shortLinksService) {
    this.shortLinksService = shortLinksService;
  }

  @PostMapping("links")
  public ShortLink createLink(final @RequestBody CreateShortLinkRequest request) {
    return shortLinksService.createShortLink(request);
  }

  @GetMapping("/l/{key}")
  public void expandLink(
      final @PathVariable(value = "key") String key,
      final HttpServletResponse httpServletResponse) {
    httpServletResponse.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
    httpServletResponse.setHeader(
        HttpHeaders.LOCATION, shortLinksService.expandShortLink(key).toString());
  }
}
