package org.example.controller;

import org.example.model.Skill;
import org.example.repository.gson.GsonSkillRepositoryImpl;
import org.example.repository.SkillRepository;

import java.util.List;

public class SkillController {
    private final SkillRepository skillRepository = new GsonSkillRepositoryImpl();

    public Skill save(String name){
        String clearedName = name.replaceAll("[^a-zA-Zа-яёА-ЯЁ ]", "");
        if(clearedName.isEmpty() || clearedName.isBlank())
            return null;
        if(clearedName.length() > 16)
            return null;

        Skill skill = new Skill();
        skill.setName(clearedName);

        List<Skill> allSkills = skillRepository.getAll();
        if(allSkills.size() == 0)
            skill.setId(1);
        else {
            int id = allSkills.get(allSkills.size() - 1).getId() + 1;
            skill.setId(id);
        }

        skillRepository.save(skill);

        return skill;
    }

    public Skill getById(Integer id){
        if(id <= 0)
            return null;

        return skillRepository.getById(id);
    }

    public List<Skill> getAll(){
        return skillRepository.getAll();
    }

    public Skill update(Skill skill){
        skill.setName(skill.getName().replaceAll("[^a-zA-Zа-яёА-ЯЁ ]", ""));
        if(skill.getName().isEmpty() || skill.getName().isBlank())
            return null;
        if(skill.getName().length() > 16)
            return null;

        return skillRepository.update(skill);
    }

    public boolean deleteById(Integer id){
        if(id <= 0)
            return false;

        return skillRepository.deleteById(id);
    }
}
