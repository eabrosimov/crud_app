package org.example.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.model.Developer;
import org.example.model.Status;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GsonDeveloperRepositoryImpl implements DeveloperRepository{
    private Gson gson;
    private final String DEVELOPER_FILE_PATH = "src/main/resources/developers.json";
    private List<Developer> allDevelopers = getAll();

    @Override
    public Developer save(Developer developer) {
        try(FileWriter writer = new FileWriter(DEVELOPER_FILE_PATH)){
            allDevelopers.add(developer);
            gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            writer.write(gson.toJson(allDevelopers));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return developer;
    }

    @Override
    public Developer getById(Integer id) {
        for(Developer d: getAll()){
            if(d.getId().equals(id))
                return d;
        }

        return null;
    }

    @Override
    public List<Developer> getAll() {
        try(FileReader reader = new FileReader(DEVELOPER_FILE_PATH)){
            Type targetClassType = new TypeToken<ArrayList<Developer>>() { }.getType();
            allDevelopers = new Gson().fromJson(reader, targetClassType);
            if(allDevelopers == null)
                allDevelopers = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return allDevelopers.stream().filter(d -> d.getStatus() == Status.ACTIVE).collect(Collectors.toList());
    }

    @Override
    public Developer update(Developer developer) {
        for(int i = 0; i < allDevelopers.size(); i++) {
            if (allDevelopers.get(i).getId().equals(developer.getId())) {
                allDevelopers.set(i, developer);

                try (FileWriter writer = new FileWriter(DEVELOPER_FILE_PATH)) {
                    gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
                    writer.write(gson.toJson(allDevelopers));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return developer;
    }

    @Override
    public boolean deleteById(Integer id) {
        for (int i = 0; i < allDevelopers.size(); i++) {
            if(allDevelopers.get(i).getId().equals(id) && allDevelopers.get(i).getStatus() == Status.ACTIVE){
                allDevelopers.get(i).setStatus(Status.DELETED);

                try(FileWriter writer = new FileWriter(DEVELOPER_FILE_PATH)){
                    gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
                    writer.write(gson.toJson(allDevelopers));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
        }

        return false;
    }
}
