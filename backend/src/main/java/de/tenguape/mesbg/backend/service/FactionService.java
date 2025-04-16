package de.tenguape.mesbg.backend.service;

import de.tenguape.mesbg.backend.entity.Faction;
import de.tenguape.mesbg.backend.repository.FactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactionService {

    private final FactionRepository repo;

    public FactionService(FactionRepository repo) {
        this.repo = repo;
    }

    public List<Faction> getAll() {
        return repo.findAll();
    }

    public Faction getById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public Faction create(Faction faction) {
        return repo.save(faction);
    }

    public Faction update(Long id, Faction updated) {
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
