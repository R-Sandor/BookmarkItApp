package dev.findfirst.core.model.jdbc;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Table("typesense_intialization")
public class TypesenseInitRecord { 
  @Id
  private Long Id; 
  private String scriptName;
  private boolean initialized; 
  private Date initDate;
}
