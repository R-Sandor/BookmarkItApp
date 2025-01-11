package dev.findfirst.core.model.jdbc;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@Table("typesense_intialization")
public class TypesenseInitRecord {
  @Id
  private Integer id;
  private String scriptName;
  private String path;
  private boolean initialized;
  private Date initDate;
}
