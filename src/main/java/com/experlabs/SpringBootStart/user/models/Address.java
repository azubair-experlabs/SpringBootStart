package com.experlabs.SpringBootStart.user.models;

import com.experlabs.SpringBootStart.core.models.AddressType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;
    @Column(
            nullable = false
    )
    private AddressType type;
    @Column(
            nullable = false
    )
    private String streetAddress;

    @Column(
            nullable = false
    )
    private String city;

    @Column(
            nullable = false
    )
    private String country;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    @JsonIgnore
    private User user;
}
