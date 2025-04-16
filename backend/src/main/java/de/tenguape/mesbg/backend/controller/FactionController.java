package de.tenguape.mesbg.backend.controller;

import de.tenguape.mesbg.backend.entity.Faction;
import de.tenguape.mesbg.backend.service.FactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factions")
public class FactionController {

    private final FactionService service;

    public FactionController(FactionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Faction> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Faction getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Faction create(@RequestBody Faction faction) {
        return service.create(faction);
    }

    @PutMapping("/{id}")
    public Faction update(@PathVariable Long id, @RequestBody Faction updated) {
        return service.update(id, updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}