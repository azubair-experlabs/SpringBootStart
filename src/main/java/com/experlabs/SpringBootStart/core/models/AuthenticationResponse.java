package com.experlabs.SpringBootStart.core.models;

import com.experlabs.SpringBootStart.user.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationResponse {

  private User user;
  private String token;
}
