package de.tenguape.mesbg.backend.service;

import de.tenguape.mesbg.backend.entity.Faction;
import de.tenguape.mesbg.backend.entity.Hero;
import de.tenguape.mesbg.backend.model.OperationType;
import de.tenguape.mesbg.backend.model.PointType;
import de.tenguape.mesbg.backend.repository.FactionRepository;
import de.tenguape.mesbg.backend.repository.HeroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class HeroServiceTest {

    @Autowired
    private HeroService service;

    @Autowired
    private HeroRepository repo;

    @Autowired
    private FactionRepository factionRepo;

    private Faction faction;

    @BeforeEach
    void setup() {
        repo.deleteAll();
        factionRepo.deleteAll();
        faction = factionRepo.save(Faction.builder().name("Default Faction").build());
    }

    @Test
    void testCreateSetsCurrentPointsToMaxIfZero() {
        Hero hero = Hero.builder()
                .name("Gimli")
                .faction(faction)
                .mightPointsMax(3)
                .willPointsMax(1)
                .fatePointsMax(2)
                .build();

        Hero created = service.create(hero);

        assertEquals(3, created.getMightPointsCurrent());
        assertEquals(1, created.getWillPointsCurrent());
        assertEquals(2, created.getFatePointsCurrent());
    }

    @Test
    void testCreateKeepsProvidedCurrentPoints() {
        Hero hero = Hero.builder()
                .name("Legolas")
                .faction(faction)
                .mightPointsMax(3).mightPointsCurrent(2)
                .willPointsMax(3).willPointsCurrent(1)
                .fatePointsMax(3).fatePointsCurrent(3)
                .build();

        Hero created = service.create(hero);

        assertEquals(2, created.getMightPointsCurrent());
        assertEquals(1, created.getWillPointsCurrent());
        assertEquals(3, created.getFatePointsCurrent());
    }

    @Test
    void testUpdateChangesFields() {
        Hero hero = service.create(Hero.builder()
                .name("Frodo")
                .faction(faction)
                .mightPointsMax(1)
                .willPointsMax(6)
                .fatePointsMax(3)
                .build());

        Faction updatedFaction = factionRepo.save(Faction.builder().name("Fellowship").build());

        Hero updated = Hero.builder()
                .name("Frodo Baggins")
                .faction(updatedFaction)
                .mightPointsMax(2).mightPointsCurrent(1)
                .willPointsMax(5).willPointsCurrent(3)
                .fatePointsMax(2).fatePointsCurrent(2)
                .specialRules("Ringbearer")
                .build();

        Hero result = service.update(hero.getId(), updated);

        assertEquals("Frodo Baggins", result.getName());
        assertEquals(1, result.getMightPointsCurrent());
        assertEquals("Ringbearer", result.getSpecialRules());
        assertEquals("Fellowship", result.getFaction().getName());
    }

    @Test
    void testDeleteHero() {
        Hero hero = service.create(Hero.builder()
                .name("Boromir")
                .faction(faction)
                .mightPointsMax(3)
                .willPointsMax(3)
                .fatePointsMax(3)
                .build());

        service.delete(hero.getId());

        assertTrue(repo.findById(hero.getId()).isEmpty());
    }

    @Test
    void testGetAllReturnsList() {
        service.create(Hero.builder().name("A").faction(faction).mightPointsMax(1).willPointsMax(1).fatePointsMax(1).build());
        service.create(Hero.builder().name("B").faction(faction).mightPointsMax(2).willPointsMax(2).fatePointsMax(2).build());

        List<Hero> heroes = service.getAll();
        assertEquals(2, heroes.size());
    }

    @Test
    void testAdjustPoints_decrementFate() {
        Hero gandalf = repo.save(Hero.builder()
                .name("Gandalf")
                .faction(faction)
                .mightPointsMax(3).mightPointsCurrent(3)
                .willPointsMax(6).willPointsCurrent(6)
                .fatePointsMax(3).fatePointsCurrent(3)
                .build());

        Hero updated = service.adjustPoints(
                gandalf.getId(),
                PointType.FATE,
                OperationType.DECREMENT
        );

        assertEquals(2, updated.getFatePointsCurrent());
        assertEquals(3, updated.getFatePointsMax());
        assertEquals(6, updated.getWillPointsCurrent());
    }

    @Test
    void testAdjustPoints_doesNotGoBelowZero() {
        Hero hero = repo.save(Hero.builder()
                .name("Test")
                .faction(faction)
                .mightPointsMax(1).mightPointsCurrent(0)
                .willPointsMax(1).willPointsCurrent(1)
                .fatePointsMax(1).fatePointsCurrent(1)
                .build());

        Hero result = service.adjustPoints(hero.getId(), PointType.MIGHT, OperationType.DECREMENT);

        assertEquals(0, result.getMightPointsCurrent());
    }
}
