package com.experlabs.SpringBootStart.user.dto;

import com.experlabs.SpringBootStart.user.models.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UsersDto {
  MetaData metaData;
  List<User> user;
}
