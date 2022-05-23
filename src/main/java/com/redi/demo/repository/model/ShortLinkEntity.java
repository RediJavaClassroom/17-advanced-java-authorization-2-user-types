package com.redi.demo.repository.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "short_links")
public class ShortLinkEntity {

  @Id
  private String key;
  private String originalUrl;

  protected ShortLinkEntity() {}

  public ShortLinkEntity(final String key, final String originalUrl) {
    this.key = key;
    this.originalUrl = originalUrl;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getOriginalUrl() {
    return originalUrl;
  }

  public void setOriginalUrl(String originalUrl) {
    this.originalUrl = originalUrl;
  }
}
