package backend.medapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record NewMedicineDto(@NotBlank String name, @NotEmpty String[] treatsFor, boolean needsPrescription) {
}
