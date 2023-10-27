package com.marbl.reservation.registry;

import com.marbl.reservation.exception.MarblError;
import com.marbl.reservation.exception.MarblException;
import com.marbl.reservation.registry.response.AllRegistryResponse;
import com.marbl.reservation.registry.response.SaveRegistryResponse;
import com.marbl.reservation.registry.response.SingleRegistryResponse;
import com.marbl.reservation.shared.MarblResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/registry")
public class RegistryController {

    private final RegistryService registryService;

    @GetMapping
    @Operation(tags = "Registry", description = "Retrieve all registry", summary = "We are able to retrieve all registry")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AllRegistryResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AllRegistryResponse.class)))})
    public ResponseEntity<MarblResponse<List<Registry>>> getAllRegistry(HttpServletRequest httpServletRequest) {
        log.info("Inside [getAllRegistry] method of [RegistryController]");
        MarblResponse<List<Registry>> response = new MarblResponse<>(OffsetDateTime.now(), httpServletRequest.getServletPath(), HttpStatus.OK.value());
        List<Registry> result = registryService.getAllRegistry();

        response.setData(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}")
    @Operation(tags = "Registry", description = "Retrieve the selected registry", summary = "We are able to retrieve the selected registry by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SingleRegistryResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SingleRegistryResponse.class)))})
    public ResponseEntity<MarblResponse<Registry>> getRegistry(@PathVariable("id") Long registryId, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [getRegistry] method of [ReservationController]");
        MarblResponse<Registry> response = new MarblResponse<>(OffsetDateTime.now(), httpServletRequest.getServletPath(), HttpStatus.OK.value());
        Registry result = registryService.getRegistry(registryId);

        response.setData(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(tags = "Registry", description = "Save a new registry", summary = "We are able to store a new reservation")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SaveRegistryResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaveRegistryResponse.class)))})
    public ResponseEntity<MarblResponse<Long>> saveRegistry(@Valid @RequestBody Registry registryRequest, HttpServletRequest httpServletRequest) {
        log.info("Inside [saveRegistry] method of [ReservationController]");
        MarblResponse<Long> response = new MarblResponse<>(OffsetDateTime.now(), httpServletRequest.getServletPath(), HttpStatus.OK.value());
        Registry result = registryService.saveNewRegistry(registryRequest);

        response.setData(result.getRegistryId());
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    @Operation(tags = "Registry", description = "Update a registry", summary = "We are able to update an already stored registry")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SaveRegistryResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaveRegistryResponse.class)))})
    public ResponseEntity<MarblResponse<Long>> updateRegistry(@PathVariable("id") Long registryId, @Valid @RequestBody Registry registryRequest, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [updateRegistry] method of [ReservationController]");
        MarblResponse<Long> response = new MarblResponse<>(OffsetDateTime.now(), httpServletRequest.getServletPath(), HttpStatus.OK.value());
        Registry result = registryService.updateRegistry(registryId, registryRequest);

        response.setData(result.getRegistryId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(tags = "Registry", description = "Delete the selected registry", summary = "We are able to delete the selected registry by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarblResponse.class)))})
    public ResponseEntity<MarblResponse> deleteRegistry(@PathVariable("id") Long registryId, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [deleteRegistry] method of [ReservationController]");
        MarblResponse<?> response = new MarblResponse<>(OffsetDateTime.now(), httpServletRequest.getServletPath(), HttpStatus.OK.value());
        registryService.deleteRegistry(registryId);

        return ResponseEntity.ok(response);
    }
}
