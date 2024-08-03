package com.alianza.clients.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequestDTO {

    @NotBlank(message = "El campo nombre no puede estar vacio")
    @Size(min = 5, max = 80, message = "El campo nombre entre 5 a 80 caracteres")
    private String name;

    @NotBlank(message = "El correo electronico no puede estar vacio")
    @Email
    private String email;

    @NotBlank(message = "El telefono no puede estar vacío")
    private String phone;
}
