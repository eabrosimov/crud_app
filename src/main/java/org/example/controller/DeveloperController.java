package org.example.controller;

import org.example.model.Developer;
import org.example.repository.DeveloperRepository;
import org.example.repository.gson.GsonDeveloperRepositoryImpl;

import java.util.List;

public class DeveloperController {
    private final DeveloperRepository developerRepository = new GsonDeveloperRepositoryImpl();

    public Developer save(String firstName, String lastName){
        String clearedFirstName = firstName.replaceAll("[^a-zA-Zа-яёА-ЯЁ]", "");
        String clearedLastName = lastName.replaceAll("[^a-zA-Zа-яёА-ЯЁ]", "");
        if(clearedFirstName.isEmpty() || clearedLastName.isEmpty())
            return null;
        if(clearedFirstName.length() > 16 || clearedLastName.length() > 16)
            return null;

        Developer developer = new Developer();
        developer.setFirstName(clearedFirstName);
        developer.setLastName(clearedLastName);

        List<Developer> allDev = developerRepository.getAll();
        if(allDev.size() == 0)
            developer.setId(1);
        else{
            int id = allDev.get(allDev.size() - 1).getId() + 1;
            developer.setId(id);
        }

        developerRepository.save(developer);

        return developer;
    }

    public Developer getById(Integer id){
        if (id <= 0)
            return null;

        return developerRepository.getById(id);
    }

    public List<Developer> getAll(){
        return developerRepository.getAll();
    }

    public Developer update(Developer developer) {
        Developer outdatedDeveloper = getById(developer.getId());

        if (!developer.getFirstName().equals(outdatedDeveloper.getFirstName())) {
            String clearedFirstName = developer.getFirstName().replaceAll("[^a-zA-Zа-яёА-ЯЁ]", "");
            developer.setFirstName(clearedFirstName);
            if (developer.getFirstName().isEmpty())
                return null;
            if (developer.getFirstName().length() > 16)
                return null;
        }

        if (!developer.getLastName().equals(outdatedDeveloper.getLastName())) {
            String clearedLastName = developer.getLastName().replaceAll("[^a-zA-Zа-яёА-ЯЁ]", "");
            developer.setLastName(clearedLastName);
            if (developer.getLastName().isEmpty())
                return null;
            if (developer.getLastName().length() > 16)
                return null;
        }

        return developerRepository.update(developer);
    }

    public boolean deleteById(Integer id){
        if(id <= 0)
            return false;

        return developerRepository.deleteById(id);
    }
}
