package backend.medapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.medapi.models.Medicine;
import backend.medapi.models.Correlation;

public interface CorrelationRepo extends JpaRepository<Correlation, String> {
    Correlation findByMedicine(String name);

    Correlation[] findAllByMedicine(String name);

    Correlation findBySympton(String name);

    Correlation[] findAllBySympton(String name);
}
