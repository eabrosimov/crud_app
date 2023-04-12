package org.example.repository.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.model.Skill;
import org.example.repository.SkillRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonSkillRepositoryImpl implements SkillRepository {
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final String SKILL_FILE_PATH = "src/main/resources/skills.json";

    @Override
    public Skill save(Skill skill) {
        List<Skill> allSkills = getAllInternal();
        try(FileWriter writer = new FileWriter(SKILL_FILE_PATH)){
            allSkills.add(skill);
            writer.write(GSON.toJson(allSkills));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return skill;
    }

    @Override
    public Skill getById(Integer id) {
        return getAllInternal().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Skill> getAll() {
        return getAllInternal();
    }

    @Override
    public Skill update(Skill skill) {
        List<Skill> allSkills = getAllInternal();
        allSkills.stream()
                .filter(s -> s.getId().equals(skill.getId()))
                .forEach(s -> s.setName(skill.getName()));

        writeToFile(allSkills);

        return skill;

    }

    @Override
    public boolean deleteById(Integer id) {
        List<Skill> allSkills = getAllInternal();
        boolean isDeleted = allSkills.removeIf(s -> s.getId().equals(id));

        if(isDeleted)
            writeToFile(allSkills);

        return isDeleted;
    }

    private List<Skill> getAllInternal(){
        List<Skill> allSkills;
        try(FileReader reader = new FileReader(SKILL_FILE_PATH)){
            Type targetClassType = new TypeToken<ArrayList<Skill>>() { }.getType();
            allSkills = GSON.fromJson(reader, targetClassType);
            if(allSkills == null)
                allSkills = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return allSkills;
    }

    private void writeToFile(List<Skill> allSkills){
        try(FileWriter writer = new FileWriter(SKILL_FILE_PATH)){
            writer.write(GSON.toJson(allSkills));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
