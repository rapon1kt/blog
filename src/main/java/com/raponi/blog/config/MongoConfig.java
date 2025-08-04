package com.raponi.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig {

  public @Bean MongoClient mongoClient(@Value("${spring.data.mongodb.uri}") String mongoUri) {
    return MongoClients.create(mongoUri);
  }

}
