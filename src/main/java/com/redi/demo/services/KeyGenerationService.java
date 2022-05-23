package com.redi.demo.services;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class KeyGenerationService {
  public String generateKey() {
    return UUID.randomUUID().toString().substring(0, 8);
  }
}
