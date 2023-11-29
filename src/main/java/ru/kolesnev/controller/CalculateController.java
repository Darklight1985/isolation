package ru.kolesnev.controller;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import ru.kolesnev.domain.CalculateThicknessDto;
import ru.kolesnev.domain.IsolationCreateDto;
import ru.kolesnev.enums.ObjectType;
import ru.kolesnev.enums.SurfaceType;
import ru.kolesnev.service.CalculationService;
import ru.kolesnev.utils.ThermalCoefUtils;


@RequiredArgsConstructor
@Path("/calculate")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Контроллер для расчеты толщин тепловой изоляции материала")
public class CalculateController {

    private final CalculationService calculationService;
    private final ThermalCoefUtils thermalCoefUtils;

    @POST
    @Operation(description = "Расчет толщины теплоизоляции для плоской стенки")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public String calculateThickness(@Valid @Parameter(description = "Параметры для расчета",
            name = "IsolationCreateDto") CalculateThicknessDto dto) {
        return calculationService.getPropertys(dto);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/property")
    public Integer getCoef() {
        return thermalCoefUtils.getHeatTransferCoef(ObjectType.FLAT_WALL, SurfaceType.INNER_HIGH_RADIATION);
    }
}
