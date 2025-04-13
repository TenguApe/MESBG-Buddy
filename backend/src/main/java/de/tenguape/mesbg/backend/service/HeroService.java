package de.tenguape.mesbg.backend.service;

import de.tenguape.mesbg.backend.entity.Hero;
import de.tenguape.mesbg.backend.model.OperationType;
import de.tenguape.mesbg.backend.model.PointType;
import de.tenguape.mesbg.backend.repository.HeroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeroService {
    private final HeroRepository repo;

    public HeroService(HeroRepository repo) {
        this.repo = repo;
    }

    public Hero adjustPoints(Long id, PointType type, OperationType operation) {
        Hero hero = getById(id);

        switch (type) {
            case MIGHT -> {
                int newValue = operation == OperationType.INCREMENT ?
                        hero.getMightPointsCurrent() + 1 :
                        hero.getMightPointsCurrent() - 1;
                hero.setMightPointsCurrent(Math.max(0, newValue));
            }
            case WILL -> {
                int newValue = operation == OperationType.INCREMENT ?
                        hero.getWillPointsCurrent() + 1 :
                        hero.getWillPointsCurrent() - 1;
                hero.setWillPointsCurrent(Math.max(0, newValue));
            }
            case FATE -> {
                int newValue = operation == OperationType.INCREMENT ?
                        hero.getFatePointsCurrent() + 1 :
                        hero.getFatePointsCurrent() - 1;
                hero.setFatePointsCurrent(Math.max(0, newValue));
            }
        }

        return repo.save(hero);
    }


    public List<Hero> getAll() {
        return repo.findAll();
    }

    public Hero getById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public Hero create(Hero hero) {
        // Set current values to max if not provided
        if (hero.getMightPointsCurrent() == 0) {
            hero.setMightPointsCurrent(hero.getMightPointsMax());
        }
        if (hero.getWillPointsCurrent() == 0) {
            hero.setWillPointsCurrent(hero.getWillPointsMax());
        }
        if (hero.getFatePointsCurrent() == 0) {
            hero.setFatePointsCurrent(hero.getFatePointsMax());
        }
        return repo.save(hero);
    }

    public Hero update(Long id, Hero updated) {
        return repo.findById(id)
                .map(hero -> {
                    hero.setName(updated.getName());
                    hero.setFaction(updated.getFaction());
                    hero.setMightPointsMax(updated.getMightPointsMax());
                    hero.setMightPointsCurrent(updated.getMightPointsCurrent());
                    hero.setWillPointsMax(updated.getWillPointsMax());
                    hero.setWillPointsCurrent(updated.getWillPointsCurrent());
                    hero.setFatePointsMax(updated.getFatePointsMax());
                    hero.setFatePointsCurrent(updated.getFatePointsCurrent());
                    hero.setSpecialRules(updated.getSpecialRules());
                    return repo.save(hero);
                }).orElseThrow();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
