package ru.kolesnev.controller;


import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import ru.kolesnev.dto.CalculateThicknessDto;
import ru.kolesnev.dto.TypeViewDto;
import ru.kolesnev.enums.ObjectType;
import ru.kolesnev.enums.SurfaceType;
import ru.kolesnev.service.CalculationService;
import ru.kolesnev.utils.ThermalCoefUtils;

import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor
@Path("/calculate")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Контроллер для расчеты толщин тепловой изоляции материала")
public class CalculateController {

    private final CalculationService calculationService;
    private final ThermalCoefUtils thermalCoefUtils;

    @POST
    @Operation(description = "Расчет толщины теплоизоляции по нормированной плотности теплопотока")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public String calculateThickness(@Valid @Parameter(description = "Параметры для расчета",
            name = "IsolationCreateDto") CalculateThicknessDto dto) {
        return calculationService.calculateThick(dto);
    }

    @GET
    @PermitAll
    @Operation(description = "Получение значение коэффициента тепловой отдачи поверхности")
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/property")
    public Integer getCoef(@QueryParam(value = "type-object") ObjectType objectType,
                           @QueryParam(value = "type-surface") SurfaceType surfaceType) {
        return thermalCoefUtils.getHeatTransferCoef(objectType, surfaceType);
    }

    @GET
    @PermitAll
    @Operation(description = "Получение списка возможных типов поверхностей")
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/surfaces")
    public List<TypeViewDto> getSurfaceTypes() {
        return Arrays.stream(SurfaceType.values())
                .map(type -> TypeViewDto.builder()
                        .code(type.name())
                        .title(type.getDescription())
                        .build())
                .toList();
    }

    @GET
    @PermitAll
    @Operation(description = "Получение списка возможных типов объектов")
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/objects")
    public List<TypeViewDto> getObjectTypes() {
        return Arrays.stream(ObjectType.values())
                .map(type -> TypeViewDto.builder()
                        .code(type.name())
                        .title(type.getName())
                        .build())
                .toList();
    }
}
