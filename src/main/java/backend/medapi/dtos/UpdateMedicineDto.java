package backend.medapi.dtos;

import io.micrometer.common.lang.Nullable;

public record UpdateMedicineDto(@Nullable String name, @Nullable String[] treatsFor,
        @Nullable Boolean needsPrescription,
        @Nullable Boolean registerNewDiseases) {
}
