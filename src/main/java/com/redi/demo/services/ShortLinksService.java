package com.redi.demo.services;

import com.redi.demo.model.CreateShortLinkRequest;
import com.redi.demo.model.ShortLink;
import com.redi.demo.repository.ShortLinkRepository;
import com.redi.demo.repository.model.ShortLinkEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class ShortLinksService {

  final private KeyGenerationService keyGenerationService;

  private final String BASE_URL = "http://localhost:8080";
  private final ShortLinkRepository repository;

  @Autowired
  public ShortLinksService(final KeyGenerationService keyGenerationService, final ShortLinkRepository repository) {
    this.keyGenerationService = keyGenerationService;
    this.repository = repository;
  }

  @PreAuthorize("hasAuthority('short-links.create')")
  public ShortLink createShortLink(final CreateShortLinkRequest request) {
    final String key = keyGenerationService.generateKey();
    final ShortLinkEntity entity = new ShortLinkEntity(key, request.originalUrl.toString());
    repository.save(entity);
    final URI uri = URI.create(String.format("%s/l/%s", BASE_URL, key));
    return new ShortLink(uri);
  }

  public URI expandShortLink(final String key) {
    final ShortLinkEntity entity = repository.findByKey(key);
    return URI.create(entity.getOriginalUrl());
  }
}
