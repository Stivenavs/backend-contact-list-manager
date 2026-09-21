package com.contactmanager.infrastructure.adapters.in.rest.configuration;

import com.contactmanager.application.exceptions.PasswordErrorException;
import com.contactmanager.application.exceptions.UserNotFoundException;
import com.contactmanager.infrastructure.adapters.in.rest.controller.request.LoginRequest;
import com.contactmanager.infrastructure.adapters.in.rest.controller.response.UserResponse;
import com.contactmanager.infrastructure.config.OpenApiConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Tag(name ="Authentication")
@Validated
public interface AuthApi {
    @Operation(
            summary = "Valida el usuario y genera el token se sesion",
            description = "Servicio POST genera el token de sesión"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "usuario logueado con exito",
                            content = @Content(schema = @Schema(implementation = UserResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "El usuario no existe",
                            content = @Content(schema = @Schema(implementation = Class.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Contraseña incorrecta.",
                            content = @Content(schema = @Schema(implementation = Exception.class))
                    )
            }
    )
    @PostMapping("/login-email")
    ResponseEntity<UserResponse> login(@RequestBody LoginRequest loginRequest) throws UserNotFoundException, PasswordErrorException;

}
