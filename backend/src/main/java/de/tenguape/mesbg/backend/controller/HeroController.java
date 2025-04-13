package de.tenguape.mesbg.backend.controller;

import de.tenguape.mesbg.backend.entity.Hero;
import de.tenguape.mesbg.backend.model.PointAdjustmentRequest;
import de.tenguape.mesbg.backend.service.HeroService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/heroes")
public class HeroController {

    private final HeroService service;

    public HeroController(HeroService service) {
        this.service = service;
    }

    @PatchMapping("/{id}/adjust-points")
    public Hero adjustPoints(@PathVariable Long id, @RequestBody PointAdjustmentRequest request) {
        return service.adjustPoints(id, request.getType(), request.getOperation());
    }

    @GetMapping
    public List<Hero> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Hero getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Hero create(@RequestBody Hero hero) {
        return service.create(hero);
    }

    @PutMapping("/{id}")
    public Hero update(@PathVariable Long id, @RequestBody Hero updated) {
        return service.update(id, updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}