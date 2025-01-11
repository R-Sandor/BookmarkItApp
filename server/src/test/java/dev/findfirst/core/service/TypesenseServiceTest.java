package dev.findfirst.core.service;

import static org.mockito.Mockito.when;

import java.util.Date;

import dev.findfirst.core.model.jdbc.TypesenseInitRecord;
import dev.findfirst.core.repository.jdbc.TypsenseInitializationRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests for typsense operations such as intitilization, queries/imports.
 */
@ExtendWith(MockitoExtension.class)
class TypesenseServiceTest {
  @Mock
  private TypsenseInitializationRepository typesenseRepo;

  @InjectMocks
  private TypesesenseService typsense;

  @Test
  void alreadyInitialized() { 
    when(typesenseRepo.findByScriptName("init")).thenReturn(new TypesenseInitRecord(1, "init", null, true, new Date()));
  }
  // Use the file to test typesense service.
  // Mock saving the initialized file.
}
