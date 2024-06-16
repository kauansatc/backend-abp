package backend.medapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.medapi.models.Medicine;

public interface MedicineRepo extends JpaRepository<Medicine, UUID> {
}
