package com.redi.demo.repository;

import com.redi.demo.repository.model.ShortLinkEntity;
import org.springframework.data.repository.CrudRepository;

public interface ShortLinkRepository extends CrudRepository<ShortLinkEntity, String> {
  ShortLinkEntity findByKey(final String key);
}
