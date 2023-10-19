package com.marbl.reservation.registry;

import com.marbl.reservation.exception.MarblError;
import com.marbl.reservation.exception.MarblException;
import com.marbl.reservation.reservation.Reservation;
import com.marbl.reservation.reservation.ReservationGetResponse;
import com.marbl.reservation.reservation.ReservationPostResponse;
import com.marbl.reservation.reservation.ReservationResponse;
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
@RequestMapping("/registry")
public class RegistryController {

    private final RegistryService registryService;

    @GetMapping
    @Operation(tags = "Registry", description = "Retrieve all registry", summary = "We are able to retrieve all registry")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RegistryResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegistryResponse.class)))})
    public ResponseEntity<RegistryResponse> getAllRegistry(HttpServletRequest httpServletRequest) {
        log.info("Inside [getAllRegistry] method of [RegistryController]");
        RegistryResponse response = new RegistryResponse();
        response.setTimestamp(OffsetDateTime.now());
        response.setStatus(HttpStatus.OK.value());
        response.setPath(httpServletRequest.getServletPath());
        List<Registry> result = registryService.getAllRegistry();

        response.setResult(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}")
    @Operation(tags = "Registry", description = "Retrieve the selected registry", summary = "We are able to retrieve the selected registry by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RegistryGetResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegistryGetResponse.class)))})
    public ResponseEntity<RegistryGetResponse> getRegistry(@PathVariable("id") Long registryId, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [getRegistry] method of [ReservationController]");
        RegistryGetResponse response = new RegistryGetResponse();
        response.setTimestamp(OffsetDateTime.now());
        response.setStatus(HttpStatus.OK.value());
        response.setPath(httpServletRequest.getServletPath());
        Registry result = registryService.getRegistry(registryId);

        response.setResult(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(tags = "Registry", description = "Save a new registry", summary = "We are able to store a new reservation")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RegistryPostResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegistryPostResponse.class)))})
    public ResponseEntity<RegistryPostResponse> saveRegistry(@Valid @RequestBody Registry registryRequest, HttpServletRequest httpServletRequest) {
        log.info("Inside [saveRegistry] method of [ReservationController]");
        RegistryPostResponse response = new RegistryPostResponse();
        response.setTimestamp(OffsetDateTime.now());
        response.setStatus(HttpStatus.OK.value());
        response.setPath(httpServletRequest.getServletPath());
        Registry result = registryService.saveNewRegistry(registryRequest);

        response.setRegistryId(result.getRegistryId());
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    @Operation(tags = "Registry", description = "Update a registry", summary = "We are able to update an already stored registry")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RegistryPostResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegistryPostResponse.class)))})
    public ResponseEntity<RegistryPostResponse> updateRegistry(@PathVariable("id") Long registryId, @Valid @RequestBody Registry registryRequest, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [updateRegistry] method of [ReservationController]");
        RegistryPostResponse response = new RegistryPostResponse();
        response.setTimestamp(OffsetDateTime.now());
        response.setStatus(HttpStatus.OK.value());
        response.setPath(httpServletRequest.getServletPath());

        Registry result = registryService.updateRegistry(registryId,registryRequest);
        response.setRegistryId(result.getRegistryId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(tags = "Registry", description = "Delete the selected registry", summary = "We are able to delete the selected registry by id")
    @ApiResponses(value =
            {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ReservationGetResponse.class))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationGetResponse.class)))})
    public ResponseEntity<RegistryPostResponse> deleteRegistry(@PathVariable("id") Long regestryId, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [deleteRegistry] method of [ReservationController]");
        RegistryPostResponse response = new RegistryPostResponse();
        response.setTimestamp(OffsetDateTime.now());
        response.setStatus(HttpStatus.OK.value());
        response.setPath(httpServletRequest.getServletPath());
        registryService.deleteRegistry(regestryId);

        return ResponseEntity.ok(response);
    }
}
