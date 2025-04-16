package de.tenguape.mesbg.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.tenguape.mesbg.backend.entity.Faction;
import de.tenguape.mesbg.backend.entity.Hero;
import de.tenguape.mesbg.backend.model.OperationType;
import de.tenguape.mesbg.backend.model.PointAdjustmentRequest;
import de.tenguape.mesbg.backend.model.PointType;
import de.tenguape.mesbg.backend.repository.FactionRepository;
import de.tenguape.mesbg.backend.repository.HeroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HeroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private FactionRepository factionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        heroRepository.deleteAll();
        factionRepository.deleteAll();
    }

    @Test
    void testCreateAndGetHero() throws Exception {
        Faction faction = factionRepository.save(Faction.builder().name("Gondor").build());

        Hero hero = Hero.builder()
                .name("Aragorn")
                .faction(faction)
                .mightPointsMax(3).mightPointsCurrent(3)
                .willPointsMax(2).willPointsCurrent(2)
                .fatePointsMax(2).fatePointsCurrent(2)
                .build();

        mockMvc.perform(post("/api/heroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hero)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Aragorn"));

        mockMvc.perform(get("/api/heroes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Aragorn"));
    }

    @Test
    void testAdjustPoints() throws Exception {
        Faction faction = factionRepository.save(Faction.builder().name("Istari").build());

        Hero hero = heroRepository.save(Hero.builder()
                .name("Gandalf")
                .faction(faction)
                .mightPointsMax(3).mightPointsCurrent(3)
                .willPointsMax(6).willPointsCurrent(6)
                .fatePointsMax(3).fatePointsCurrent(3)
                .build());

        PointAdjustmentRequest request = new PointAdjustmentRequest();
        request.setType(PointType.FATE);
        request.setOperation(OperationType.DECREMENT);

        mockMvc.perform(patch("/api/heroes/" + hero.getId() + "/adjust-points")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fatePointsCurrent").value(2));
    }
}
