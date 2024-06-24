package backend.medapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.medapi.models.Correlation;

public interface CorrelationRepo extends JpaRepository<Correlation, String> {
    Correlation findByMedicine(String name);

    Correlation[] findAllByMedicine(String name);

    Correlation findBySymptom(String name);

    Correlation[] findAllBySymptom(String name);
}
