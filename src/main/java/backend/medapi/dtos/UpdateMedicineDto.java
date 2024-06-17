package backend.medapi.dtos;

import java.util.ArrayList;

import io.micrometer.common.lang.Nullable;

public record UpdateMedicineDto(@Nullable String name, @Nullable ArrayList<String> treatsFor,
        @Nullable Boolean needsPrescription,
        @Nullable Boolean registerNewSymptons) {
}
