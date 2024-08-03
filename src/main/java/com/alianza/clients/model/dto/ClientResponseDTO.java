package com.alianza.clients.model.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDTO {
    private Long id;

    private String sharedKey;

    private String name;

    private String email;

    private String phone;

    private LocalDate dataAdded;
}
