package org.example.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.model.Specialty;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonSpecialtyRepositoryImpl implements SpecialtyRepository{
    private Gson gson;
    private final String SPECIALTY_FILE_PATH = "src/main/resources/specialties.json";
    private List<Specialty> allSpecialties = getAll();

    @Override
    public Specialty save(Specialty specialty) {
        try(FileWriter writer = new FileWriter(SPECIALTY_FILE_PATH)){
            allSpecialties.add(specialty);
            gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(allSpecialties));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return specialty;
    }

    @Override
    public Specialty getById(Integer id) {
        for(Specialty s: getAll()){
            if(s.getId().equals(id))
                return s;
        }

        return null;
    }

    @Override
    public List<Specialty> getAll() {
        try(FileReader reader = new FileReader(SPECIALTY_FILE_PATH)){
            Type targetClassType = new TypeToken<ArrayList<Specialty>>() { }.getType();
            allSpecialties = new Gson().fromJson(reader, targetClassType);
            if(allSpecialties == null)
                allSpecialties = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return allSpecialties;
    }

    @Override
    public Specialty update(Specialty specialty) {
        for(int i = 0; i < allSpecialties.size(); i++) {
            if (allSpecialties.get(i).getId().equals(specialty.getId())) {
                allSpecialties.set(i, specialty);

                try (FileWriter writer = new FileWriter(SPECIALTY_FILE_PATH)) {
                    gson = new GsonBuilder().setPrettyPrinting().create();
                    writer.write(gson.toJson(allSpecialties));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return specialty;
    }

    @Override
    public boolean deleteById(Integer id) {
        for(int i = 0; i < allSpecialties.size(); i++){
            if(allSpecialties.get(i).getId().equals(id)){
                allSpecialties.remove(i);

                try(FileWriter writer = new FileWriter(SPECIALTY_FILE_PATH)){
                    gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
                    writer.write(gson.toJson(allSpecialties));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
        }

        return false;
    }
}
