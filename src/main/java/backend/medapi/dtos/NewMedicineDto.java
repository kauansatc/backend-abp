package backend.medapi.dtos;

import java.util.ArrayList;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record NewMedicineDto(@NotBlank String name, @NotEmpty ArrayList<String> treatsFor, boolean needsPrescription,
        @Nullable boolean registerNewSymptons) {
}
