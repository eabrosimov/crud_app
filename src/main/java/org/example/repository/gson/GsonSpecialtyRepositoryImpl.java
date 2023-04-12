package org.example.repository.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.model.Specialty;
import org.example.repository.SpecialtyRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonSpecialtyRepositoryImpl implements SpecialtyRepository {
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final String SPECIALTY_FILE_PATH = "src/main/resources/specialties.json";

    @Override
    public Specialty save(Specialty specialty) {
        List<Specialty> allSpecialties = getAllInternal();
        try(FileWriter writer = new FileWriter(SPECIALTY_FILE_PATH)){
            allSpecialties.add(specialty);
            writer.write(GSON.toJson(allSpecialties));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return specialty;
    }

    @Override
    public Specialty getById(Integer id) {
        return getAllInternal().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Specialty> getAll() {
        return getAllInternal();
    }

    @Override
    public Specialty update(Specialty specialty) {
        List<Specialty> allSpecialties = getAllInternal();
        allSpecialties.stream()
                .filter(s -> s.getId().equals(specialty.getId()))
                .forEach(s -> s.setName(specialty.getName()));

        writeToFile(allSpecialties);

        return specialty;
    }

    @Override
    public boolean deleteById(Integer id) {
        List<Specialty> allSpecialties = getAllInternal();
        boolean isDeleted = allSpecialties.removeIf(s -> s.getId().equals(id));

        if(isDeleted)
            writeToFile(allSpecialties);

        return isDeleted;
    }

    private List<Specialty> getAllInternal(){
        List<Specialty> allSpecialties;
        try(FileReader reader = new FileReader(SPECIALTY_FILE_PATH)){
            Type targetClassType = new TypeToken<ArrayList<Specialty>>() { }.getType();
            allSpecialties = GSON.fromJson(reader, targetClassType);
            if(allSpecialties == null)
                allSpecialties = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return allSpecialties;
    }

    private void writeToFile(List<Specialty> allSpecialties){
        try(FileWriter writer = new FileWriter(SPECIALTY_FILE_PATH)){
            writer.write(GSON.toJson(allSpecialties));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
