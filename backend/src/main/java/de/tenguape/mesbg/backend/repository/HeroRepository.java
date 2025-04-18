package de.tenguape.mesbg.backend.repository;

import de.tenguape.mesbg.backend.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Long> {}