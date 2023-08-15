package com.experlabs.SpringBootStart.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetaData {
  Long totalItems;
  Long pageSize;
  Long pageNumber;
  boolean hasMore;
}
