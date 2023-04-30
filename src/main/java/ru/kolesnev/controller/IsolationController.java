package ru.kolesnev.controller;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import ru.kolesnev.domain.Isolation;
import ru.kolesnev.domain.IsolationCreateDto;
import ru.kolesnev.domain.ThermalProperty;
import ru.kolesnev.domain.ThermalPropertyDto;
import ru.kolesnev.domain.ThermalPropertyId;
import ru.kolesnev.repository.IsolationRepository;
import ru.kolesnev.repository.ThermalPropertyRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Path("/isolation")
@Produces(MediaType.APPLICATION_JSON)
public class IsolationController {
    private final IsolationRepository isolationRepository;
    private final ThermalPropertyRepository thermalPropertyRepository;


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ThermalProperty> getProperty(@PathParam("id") UUID id) {
        return thermalPropertyRepository.findAllThermalPropertyByIsolationID(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createIsolation(IsolationCreateDto dto) {
        Isolation isolation = Isolation.builder()
                .mark(dto.getMark())
//                .thermalProperties(new ArrayList<>())
                .build();
        isolationRepository.save(isolation);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/property")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createProperty(ThermalPropertyDto dto) {
        Isolation isolation = isolationRepository.findById(dto.getIsolation()).orElseThrow();
        ThermalProperty thermalProperty = ThermalProperty.builder()
                .conductivity(dto.getConductivity())
                .density(dto.getDensity())
                .thermalPropertyId(new ThermalPropertyId(isolation, dto.getTemperature()))
                .build();
        thermalPropertyRepository.save(thermalProperty);
        return Response.status(Response.Status.CREATED).build();
    }
}
