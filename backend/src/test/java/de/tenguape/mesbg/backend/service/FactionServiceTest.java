/*
package de.tenguape.mesbg.backend.service;

import de.tenguape.mesbg.backend.entity.Army;
import de.tenguape.mesbg.backend.entity.Hero;
import de.tenguape.mesbg.backend.repository.ArmyRepository;
import de.tenguape.mesbg.backend.repository.HeroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
public class ArmyServiceTest {

    @Autowired
    private ArmyService armyService;

    @Autowired
    private ArmyRepository armyRepo;

    @Autowired
    private HeroRepository heroRepo;

    @BeforeEach
    void setup() {
        armyRepo.deleteAll();
        heroRepo.deleteAll();
    }

    @Test
    void testCreateArmyWithHeroes() {
        Hero hero1 = heroRepo.save(Hero.builder().name("Aragorn").faction("Gondor").mightPointsMax(3).willPointsMax(3).fatePointsMax(3).build());
        Hero hero2 = heroRepo.save(Hero.builder().name("Gimli").faction("Dwarves").mightPointsMax(2).willPointsMax(1).fatePointsMax(2).build());

        Army army = Army.builder()
                .name("Fellowship")
                .heroes(List.of(hero1, hero2))
                .build();

        Army created = armyService.create(army);

        assertEquals("Fellowship", created.getName());
        assertEquals(2, created.getHeroes().size());
    }

    @Test
    void testUpdateArmyNameAndHeroes() {
        Hero hero1 = heroRepo.save(Hero.builder().name("Frodo").faction("Shire").mightPointsMax(1).willPointsMax(6).fatePointsMax(3).build());
        Army army = armyService.create(Army.builder().name("Shire").build());

        Hero hero2 = heroRepo.save(Hero.builder().name("Sam").faction("Shire").mightPointsMax(2).willPointsMax(3).fatePointsMax(2).build());

        Army updated = Army.builder()
                .name("Hobbit Army")
                .heroes(List.of(hero1, hero2))
                .build();

        Army result = armyService.update(army.getId(), updated);

        assertEquals("Hobbit Army", result.getName());
        assertEquals(2, result.getHeroes().size());
    }

    @Test
    void testDeleteArmy() {
        Army army = armyService.create(Army.builder().name("Elves").build());
        armyService.delete(army.getId());
        assertTrue(armyRepo.findById(army.getId()).isEmpty());
    }

    @Test
    void testGetAllArmies() {
        armyService.create(Army.builder().name("A").build());
        armyService.create(Army.builder().name("B").build());

        List<Army> armies = armyService.getAll();
        assertEquals(2, armies.size());
    }
}*/
