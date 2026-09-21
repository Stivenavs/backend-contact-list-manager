package com.contactmanager.infrastructure.adapters.in.rest.configuration;

import com.contactmanager.infrastructure.adapters.in.rest.controller.request.ContactRequest;
import com.contactmanager.infrastructure.adapters.in.rest.controller.response.ContactResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Contacts")
@Validated
public interface ContactApi {

    @Operation(
            summary = "Crea un contacto nuevo",
            description = "Servicio POST genera un registro de contacto"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Contacto creado con exito",
                            content = @Content(schema = @Schema(implementation = ContactResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos de entrada invalidos",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Ya existe un contacto con ese email",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @PostMapping
    ResponseEntity<ContactResponse> create(@Valid @RequestBody ContactRequest contactRequest);

    @Operation(
            summary = "Lista los contactos",
            description = "Servicio GET retorna todos los contactos registrados"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Listado de contactos",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ContactResponse.class)))
                    )
            }
    )
    @GetMapping
    ResponseEntity<List<ContactResponse>> findAll();

    @Operation(
            summary = "Consulta un contacto por id",
            description = "Servicio GET retorna un contacto segun su identificador"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Contacto encontrado",
                            content = @Content(schema = @Schema(implementation = ContactResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Contacto no encontrado",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<ContactResponse> findById(@PathVariable("id") Integer id);

    @Operation(
            summary = "Actualiza un contacto",
            description = "Servicio PUT actualiza los datos de un contacto existente"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Contacto actualizado con exito",
                            content = @Content(schema = @Schema(implementation = ContactResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos de entrada invalidos",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Contacto no encontrado",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Ya existe otro contacto con ese email",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @PutMapping("/{id}")
    ResponseEntity<ContactResponse> update(@PathVariable("id") Integer id,
                                           @Valid @RequestBody ContactRequest contactRequest);

    @Operation(
            summary = "Elimina un contacto",
            description = "Servicio DELETE elimina un contacto segun su identificador"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Contacto eliminado con exito"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Contacto no encontrado",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Integer id);
}
