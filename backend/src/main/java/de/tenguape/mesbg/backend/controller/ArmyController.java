package de.tenguape.mesbg.backend.controller;

import de.tenguape.mesbg.backend.entity.Army;
import de.tenguape.mesbg.backend.service.ArmyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/armies")
public class ArmyController {

    private final ArmyService service;

    public ArmyController(ArmyService service) {
        this.service = service;
    }

    @GetMapping
    public List<Army> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Army getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Army create(@RequestBody Army army) {
        return service.create(army);
    }

    @PutMapping("/{id}")
    public Army update(@PathVariable Long id, @RequestBody Army updated) {
        return service.update(id, updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}