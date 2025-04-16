package de.tenguape.mesbg.backend.repository;

import de.tenguape.mesbg.backend.entity.Faction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactionRepository extends JpaRepository<Faction, Long> {}