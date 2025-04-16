package de.tenguape.mesbg.backend.service;

import de.tenguape.mesbg.backend.entity.Faction;
import de.tenguape.mesbg.backend.entity.Hero;
import de.tenguape.mesbg.backend.repository.FactionRepository;
import de.tenguape.mesbg.backend.repository.HeroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FactionServiceTest {

    @Autowired
    private FactionService factionService;

    @Autowired
    private FactionRepository factionRepo;

    @Autowired
    private HeroRepository heroRepo;

    @BeforeEach
    void setup() {
        heroRepo.deleteAll();
        factionRepo.deleteAll();
    }

    @Test
    void testCreateFactionWithHeroes() {
        Faction fellowship = factionRepo.save(Faction.builder().name("Fellowship").build());

        Hero hero1 = heroRepo.save(Hero.builder()
                .name("Aragorn")
                .faction(fellowship)
                .mightPointsMax(3).mightPointsCurrent(3)
                .willPointsMax(3).willPointsCurrent(3)
                .fatePointsMax(3).fatePointsCurrent(3)
                .build());

        Hero hero2 = heroRepo.save(Hero.builder()
                .name("Gimli")
                .faction(fellowship)
                .mightPointsMax(2).mightPointsCurrent(2)
                .willPointsMax(1).willPointsCurrent(1)
                .fatePointsMax(2).fatePointsCurrent(2)
                .build());

        fellowship.setHeroes(List.of(hero1, hero2));
        Faction created = factionService.update(fellowship.getId(), fellowship);

        assertEquals("Fellowship", created.getName());
        assertEquals(2, created.getHeroes().size());
    }

    @Test
    void testUpdateFactionNameAndHeroes() {
        Faction shire = factionRepo.save(Faction.builder().name("Shire").build());

        Hero hero1 = heroRepo.save(Hero.builder()
                .name("Frodo")
                .faction(shire)
                .mightPointsMax(1).mightPointsCurrent(1)
                .willPointsMax(6).willPointsCurrent(6)
                .fatePointsMax(3).fatePointsCurrent(3)
                .build());

        Hero hero2 = heroRepo.save(Hero.builder()
                .name("Sam")
                .faction(shire)
                .mightPointsMax(2).mightPointsCurrent(2)
                .willPointsMax(3).willPointsCurrent(3)
                .fatePointsMax(2).fatePointsCurrent(2)
                .build());

        Faction updated = Faction.builder()
                .name("Hobbit Army")
                .heroes(List.of(hero1, hero2))
                .build();

        Faction result = factionService.update(shire.getId(), updated);

        assertEquals("Hobbit Army", result.getName());
        assertEquals(2, result.getHeroes().size());
    }

    @Test
    void testDeleteFaction() {
        Faction faction = factionService.create(Faction.builder().name("Elves").build());
        factionService.delete(faction.getId());
        assertTrue(factionRepo.findById(faction.getId()).isEmpty());
    }

    @Test
    void testGetAllFactions() {
        factionService.create(Faction.builder().name("A").build());
        factionService.create(Faction.builder().name("B").build());

        List<Faction> factions = factionService.getAll();
        assertEquals(2, factions.size());
    }
}
