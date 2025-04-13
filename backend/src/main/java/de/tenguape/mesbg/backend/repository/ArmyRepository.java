package de.tenguape.mesbg.backend.repository;

import de.tenguape.mesbg.backend.entity.Army;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArmyRepository extends JpaRepository<Army, Long> {}