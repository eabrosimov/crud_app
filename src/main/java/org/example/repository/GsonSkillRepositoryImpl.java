package org.example.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.model.Skill;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonSkillRepositoryImpl implements SkillRepository{
    private Gson gson;
    private final String SKILL_FILE_PATH = "src/main/resources/skills.json";
    private List<Skill> allSkills = getAll();

    @Override
    public Skill save(Skill skill) {
        try(FileWriter writer = new FileWriter(SKILL_FILE_PATH)){
            allSkills.add(skill);
            gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(allSkills));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return skill;
    }

    @Override
    public Skill getById(Integer id) {
        for(Skill s: getAll()){
            if(s.getId().equals(id))
                return s;
        }

        return null;
    }

    @Override
    public List<Skill> getAll() {
        try(FileReader reader = new FileReader(SKILL_FILE_PATH)){
            Type targetClassType = new TypeToken<ArrayList<Skill>>() { }.getType();
            allSkills = new Gson().fromJson(reader, targetClassType);
            if(allSkills == null)
                allSkills = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return allSkills;
    }

    @Override
    public Skill update(Skill skill) {
        for(int i = 0; i < allSkills.size(); i++) {
            if (allSkills.get(i).getId().equals(skill.getId())) {
                allSkills.set(i, skill);

                try (FileWriter writer = new FileWriter(SKILL_FILE_PATH)) {
                    gson = new GsonBuilder().setPrettyPrinting().create();
                    writer.write(gson.toJson(allSkills));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return skill;
    }

    @Override
    public boolean deleteById(Integer id) {
        for(int i = 0; i < allSkills.size(); i++){
            if(allSkills.get(i).getId().equals(id)){
                allSkills.remove(i);

                try(FileWriter writer = new FileWriter(SKILL_FILE_PATH)){
                    gson = new GsonBuilder().setPrettyPrinting().create();
                    writer.write(gson.toJson(allSkills));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
        }

        return false;
    }
}
