package com.marbl.reservation.registry;

import com.marbl.reservation.exception.MarblException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class RegistryService {

    private static final String NOT_FOUND = "Reservation is not found.";
    private final RegistryRepository registryRepository;

    @Transactional(readOnly = true)
    public List<Registry> getAllRegistry() {
        return registryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Registry getRegistry(Long registryId) throws MarblException {
        Optional<Registry> reservation = registryRepository.findById(registryId);
        if (reservation.isEmpty()) {
            throw new MarblException(NOT_FOUND);
        }
        return reservation.get();
    }

    @Transactional
    public Registry saveNewRegistry(Registry registryRequest) {
        return registryRepository.save(registryRequest);
    }

    @Transactional
    public Registry updateRegistry(Long registryId, Registry registryRequest) throws MarblException {
        Optional<Registry> registry = registryRepository.findById(registryId);
        if (registry.isEmpty()) {
            throw new MarblException(NOT_FOUND);
        }
        BeanUtils.copyProperties(registryRequest, registry, "id");
        return registryRepository.save(registry.get());
    }

    @Transactional
    public void deleteRegistry(Long registryId) throws MarblException {
        Optional<Registry> reservation = registryRepository.findById(registryId);
        if (reservation.isEmpty()) {
            throw new MarblException(NOT_FOUND);
        }
        registryRepository.deleteById(registryId);
    }
}
