/*
package de.tenguape.mesbg.backend.controller;

import de.tenguape.mesbg.backend.entity.Army;
import de.tenguape.mesbg.backend.entity.Hero;
import de.tenguape.mesbg.backend.repository.ArmyRepository;
import de.tenguape.mesbg.backend.repository.HeroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ArmyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArmyRepository armyRepository;

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        armyRepository.deleteAll();
        heroRepository.deleteAll();
    }

    @Test
    void testCreateAndGetArmy() throws Exception {
        Hero hero = heroRepository.save(Hero.builder()
                .name("Legolas")
                .faction("Elves")
                .mightPointsMax(3).mightPointsCurrent(3)
                .willPointsMax(2).willPointsCurrent(2)
                .fatePointsMax(2).fatePointsCurrent(2)
                .build());

        Army army = Army.builder()
                .name("Lothlorien")
                .heroes(List.of(hero))
                .build();

        mockMvc.perform(post("/api/armies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(army)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Lothlorien"))
                .andExpect(jsonPath("$.heroes[0].name").value("Legolas"));

        mockMvc.perform(get("/api/armies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Lothlorien"));
    }
}*/
