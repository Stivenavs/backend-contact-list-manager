package com.contactmanager.infrastructure.adapters.in.rest.configuration;

import com.contactmanager.infrastructure.adapters.in.rest.controller.request.UserRequest;
import com.contactmanager.infrastructure.adapters.in.rest.controller.response.UserResponse;
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

@Tag(name ="Users")
@Validated
public interface UserApi {
    @Operation(
            summary = "Crea un usuario nuevo",
            description = "Servicio POST genera un registro de usuario"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario creado con exito",
                            content = @Content(schema = @Schema(implementation = UserResponse.class))
                    )
            }
    )
    @PostMapping("/create")
    ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest);
}
