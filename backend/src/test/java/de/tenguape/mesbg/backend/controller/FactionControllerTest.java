package de.tenguape.mesbg.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.tenguape.mesbg.backend.entity.Faction;
import de.tenguape.mesbg.backend.entity.Hero;
import de.tenguape.mesbg.backend.repository.FactionRepository;
import de.tenguape.mesbg.backend.repository.HeroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FactionRepository factionRepository;

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        heroRepository.deleteAll();
        factionRepository.deleteAll();
    }

    @Test
    void testCreateAndGetArmy() throws Exception {
        Faction savedFaction = factionRepository.save(Faction.builder()
                .name("Lothlorien")
                .build());

        Hero hero = heroRepository.save(Hero.builder()
                .name("Legolas")
                .faction(savedFaction)
                .mightPointsMax(3).mightPointsCurrent(3)
                .willPointsMax(2).willPointsCurrent(2)
                .fatePointsMax(2).fatePointsCurrent(2)
                .build());

        savedFaction.setHeroes(List.of(hero));
        factionRepository.save(savedFaction); // update mit Helden

        mockMvc.perform(post("/api/factions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedFaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Lothlorien"))
                .andExpect(jsonPath("$.heroes[0].name").value("Legolas"));

        mockMvc.perform(get("/api/factions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Lothlorien"));
    }
}
