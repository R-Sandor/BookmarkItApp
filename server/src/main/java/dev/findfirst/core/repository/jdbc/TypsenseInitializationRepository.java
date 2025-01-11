package dev.findfirst.core.repository.jdbc;


import dev.findfirst.core.model.jdbc.TypesenseInitRecord;

import org.springframework.data.repository.ListCrudRepository;

public interface TypsenseInitializationRepository
    extends ListCrudRepository<TypesenseInitRecord, Long> {

  TypesenseInitRecord findByScriptName(String scriptName);

}
