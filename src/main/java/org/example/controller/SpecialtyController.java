package org.example.controller;

import org.example.model.Specialty;
import org.example.repository.gson.GsonSpecialtyRepositoryImpl;
import org.example.repository.SpecialtyRepository;

import java.util.List;

public class SpecialtyController {
    private final SpecialtyRepository specialtyRepository = new GsonSpecialtyRepositoryImpl();

    public Specialty save(String name){
        String clearedName = name.replaceAll("[^a-zA-Zа-яёА-ЯЁ ]", "");
        if(clearedName.isEmpty() || clearedName.isBlank())
            return null;
        if(clearedName.length() > 16)
            return null;

        Specialty specialty = new Specialty();
        specialty.setName(clearedName);

        List<Specialty> allSpecialties = getAll();
        if(allSpecialties.size() == 0)
            specialty.setId(1);
        else {
            int id = allSpecialties.get(allSpecialties.size() - 1).getId() + 1;
            specialty.setId(id);
        }

        specialtyRepository.save(specialty);

        return specialty;
    }

    public Specialty getById(Integer id){
        if(id <= 0)
            return null;

        return specialtyRepository.getById(id);
    }

    public List<Specialty> getAll(){
        return specialtyRepository.getAll();
    }

    public Specialty update(Specialty specialty){
        specialty.setName(specialty.getName().replaceAll("[^a-zA-Zа-яёА-ЯЁ ]", ""));
        if(specialty.getName().isEmpty() || specialty.getName().isBlank())
            return null;
        if(specialty.getName().length() > 16)
            return null;

        return specialtyRepository.update(specialty);
    }

    public boolean deleteById(Integer id){
        if(id <= 0)
            return false;

        return specialtyRepository.deleteById(id);
    }
}
