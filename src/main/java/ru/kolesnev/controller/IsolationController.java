package ru.kolesnev.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import ru.kolesnev.domain.Isolation;
import ru.kolesnev.dto.IsolationCreateDto;
import ru.kolesnev.dto.IsolationUpdateDto;
import ru.kolesnev.dto.PropertyDto;
import ru.kolesnev.dto.PropertyViewDto;
import ru.kolesnev.dto.ThermalPropertyDeleteDto;
import ru.kolesnev.dto.ThermalPropertyDto;
import ru.kolesnev.service.IsolationService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Path("/isolation")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Контроллер для работы с марками изоляции и её свойствами")
public class IsolationController {

    private final IsolationService isolationService;


    @GET
    @Path("/{id}")
    @Operation(description = "Получение тепловых свойств изоляции по её идентификатору")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PropertyDto> getProperty(@PathParam("id") @Parameter(description = "Идентификатор изоляции") UUID id) {
        return isolationService.getProperty(id);
    }

    @GET
    @Path("/property")
    @Operation(description = "Получение тепловых свойств изоляции по списку идентифкаторов")
    @Produces(MediaType.APPLICATION_JSON)
    public PropertyViewDto getPropertys(@QueryParam("ids") @Parameter(description = "Список идентификаторов") List<UUID> ids) {
       return isolationService.getPropertys(ids);
    }

    @GET
    @Operation(description = "Получение списка всех марок теплоизоляции")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Isolation> getIsolations() {
        return isolationService.getIsolations();
    }

    @POST
    @Operation(description = "Задание новой марки теплоизоляционного материала")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createIsolation(@Valid @Parameter(description = "Параметры для задания материала изоляции",
            name = "IsolationCreateDto") IsolationCreateDto dto) {
        isolationService.createIsolation(dto);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Operation(description = "Задание тепловых свойств для выбранного материала теплоизоляции")
    @Path("/property")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createProperty(@Parameter(description = "Параметры для задания тепловых свойств материала",
            name = "ThermalPropertyDto") @Valid ThermalPropertyDto dto) {
        isolationService.createProperty(dto);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Operation(description = "Обновление марки тепловой изоляции")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateIsolation(@Valid @Parameter(description = "Параметры для обновления материала изоляции",
                                            name = "IsolationUpdateDto") IsolationUpdateDto dto) {
        isolationService.updateIsolation(dto);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @DELETE
    @Operation(description = "Удаление марки тепловой изоляции")
    @Path("/property/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteIsolation(@PathParam("id") @Parameter(description = "Идентификатор изоляции") UUID id) {
        isolationService.deleteIsolation(id);
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Operation(description = "Удаление тепловых свойств для выбранного материала теплоизоляции")
    @Path("/property")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProperty(@Parameter(description = "Параметры для удаления тепловых свойств материала",
            name = "ThermalPropertyDto") @Valid ThermalPropertyDeleteDto dto) {
        isolationService.deleteProperty(dto);
        return Response.status(Response.Status.OK).build();
    }
}
