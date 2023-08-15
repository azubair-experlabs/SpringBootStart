package com.experlabs.SpringBootStart.core.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseBody {

  private int status;
  private String message;

  public ResponseBody(int status, String message) {
    this.status = status;
    this.message = message;
  }
}
