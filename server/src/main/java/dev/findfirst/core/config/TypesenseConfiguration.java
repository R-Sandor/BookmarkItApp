package dev.findfirst.core.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.typesense.api.Client;
import org.typesense.resources.Node;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class TypesenseConfiguration {

  @Value("${typesense_api_key}")
  String typesSenseApiKey;

  @Bean
  public Client typesenseClient() {
    log.debug(typesSenseApiKey);
    List<Node> nodes = new ArrayList<>();
    nodes.add(
        new Node(
            "http", // For Typesense Cloud use https
            "localhost", // For Typesense Cloud use xxx.a1.typesense.net
            "8108" // For Typesense Cloud use 443
        ));
    org.typesense.api.Configuration configuration = new org.typesense.api.Configuration(nodes, Duration.ofSeconds(2),
        typesSenseApiKey);
    return new Client(configuration);
  }

}
