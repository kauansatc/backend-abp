package backend.medapi.dtos;

import java.util.ArrayList;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record MedicineDtoReq(@NotBlank String name, @NotEmpty ArrayList<String> treatsFor,
        @Nullable Boolean needsPrescription, @Nullable Boolean handleNew) {
    // public MedicineDto {
    // if (name == null || name.isBlank()) {
    // throw new IllegalArgumentException("Name cannot be null or empty");
    // }
    // if (treatsFor == null || treatsFor.isEmpty()) {
    // throw new IllegalArgumentException("Treats for cannot be null or empty");
    // }
    // }
}