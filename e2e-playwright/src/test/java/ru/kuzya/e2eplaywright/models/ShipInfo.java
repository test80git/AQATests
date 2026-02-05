package ru.kuzya.e2eplaywright.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShipInfo {
    private String firstName;
    private String lastName;
    private String zip;
}