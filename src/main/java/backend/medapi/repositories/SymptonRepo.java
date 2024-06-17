package backend.medapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.medapi.models.Sympton;

public interface SymptonRepo extends JpaRepository<Sympton, UUID> {
    Sympton findByName(String name);
}
