package com.experlabs.SpringBootStart.user.models;

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
