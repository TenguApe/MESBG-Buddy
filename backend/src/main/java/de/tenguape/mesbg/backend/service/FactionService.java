package de.tenguape.mesbg.backend.service;

import de.tenguape.mesbg.backend.entity.Army;
import de.tenguape.mesbg.backend.repository.ArmyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArmyService {

    private final ArmyRepository repo;

    public ArmyService(ArmyRepository repo) {
        this.repo = repo;
    }

    public List<Army> getAll() {
        return repo.findAll();
    }

    public Army getById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public Army create(Army army) {
        return repo.save(army);
    }

    public Army update(Long id, Army updated) {
        return repo.findById(id)
                .map(army -> {
                    army.setName(updated.getName());
                    army.setHeroes(updated.getHeroes());
                    return repo.save(army);
                }).orElseThrow();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
