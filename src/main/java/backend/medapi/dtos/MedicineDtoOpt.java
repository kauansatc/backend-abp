package backend.medapi.dtos;

import java.util.ArrayList;

import io.micrometer.common.lang.Nullable;

public record MedicineDtoOpt(@Nullable String name, @Nullable ArrayList<String> treatsFor,
        @Nullable Boolean needsPrescription) {
    public MedicineDtoOpt {
        if (name != null && name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (treatsFor != null && treatsFor.isEmpty()) {
            throw new IllegalArgumentException("Needs to treat for at least one symptom");
        }
    }
}