package com.duxsoftware.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @Schema(description = "Nombre de usuario", example = "test", required = true)
    private String username;

    @Schema(description = "Contraseña", example = "12345", required = true)
    private String password;
}
