package dev.findfirst.core.repository.jdbc;


import org.springframework.data.repository.ListCrudRepository;

import dev.findfirst.core.model.jdbc.TypesenseInitRecord;

public interface TypsenseInitializationRepository extends ListCrudRepository<TypesenseInitRecord, Long> { 

   TypesenseInitRecord findByScriptName(String scriptName);

}
