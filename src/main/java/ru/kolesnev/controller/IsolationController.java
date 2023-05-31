package ru.kolesnev.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import ru.kolesnev.domain.Isolation;
import ru.kolesnev.domain.IsolationCreateDto;
import ru.kolesnev.domain.PropertyDto;
import ru.kolesnev.domain.PropertyViewDto;
import ru.kolesnev.domain.ThermalPropertyDto;
import ru.kolesnev.service.IsolationService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Path("/isolation")
@Produces(MediaType.APPLICATION_JSON)
public class IsolationController {
    private final IsolationService isolationService;


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PropertyDto> getProperty(@PathParam("id") UUID id) {
        return isolationService.getProperty(id);
    }

    @GET
    @Path("/property")
    @Produces(MediaType.APPLICATION_JSON)
    public PropertyViewDto getPropertys(@QueryParam("ids") List<UUID> ids) {
       return isolationService.getPropertys(ids);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Isolation> getIsolations() {
        return isolationService.getIsolations();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createIsolation(@Valid IsolationCreateDto dto) {
        isolationService.createIsolation(dto);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/property")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createProperty(ThermalPropertyDto dto) {
        isolationService.createProperty(dto);
        return Response.status(Response.Status.CREATED).build();
    }
}
