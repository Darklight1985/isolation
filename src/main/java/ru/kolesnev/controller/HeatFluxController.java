package ru.kolesnev.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import ru.kolesnev.service.HeatFluxService;

import java.io.File;

@RequiredArgsConstructor
@Path("/heat_flux")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Контроллер для работы с марками изоляции и её свойствами")
public class HeatFluxController {

    private final HeatFluxService heatFluxService;

    @POST
    @RolesAllowed("admin")
    @Operation(description = "Задание нормированного теплового потока")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createIsolation(@QueryParam(value = "indoors") Boolean indoors, @QueryParam(value = "long_work") Boolean longWork,
                                    File file) {
        heatFluxService.readData(file, indoors, longWork);
        return Response.status(Response.Status.CREATED).build();
    }
}
