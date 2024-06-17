package backend.medapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.medapi.models.Sympton;

public interface SymptonRepo extends JpaRepository<Sympton, String> {
    Sympton findByName(String name);
}
