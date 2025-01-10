package dev.findfirst.core.service;

import org.springframework.stereotype.Service;
import org.typesense.api.Client;
import org.typesense.api.FieldTypes;
import org.typesense.model.CollectionResponse;
import org.typesense.model.CollectionSchema;
import org.typesense.model.Field;

import dev.findfirst.core.repository.jdbc.TypsenseInitializationRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TypsesenseService {

  private final TypsenseInitializationRepository initRepo;
  
  private final Client client;

  @PostConstruct
  void createSchema() throws Exception {
    
    var q = initRepo.findByScriptName("init");
    if (q == null) {
      CollectionSchema collectionSchema = new CollectionSchema();

      collectionSchema.name("bookmark")
          .addFieldsItem(new Field().name("title").type(FieldTypes.STRING))
          .addFieldsItem(new Field().name("text").type(FieldTypes.STRING));

      CollectionResponse collectionResponse = client.collections().create(collectionSchema);
      log.debug(collectionResponse.toString());

    }

  }

}
