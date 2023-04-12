package org.example.repository.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.model.Developer;
import org.example.model.Status;
import org.example.repository.DeveloperRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GsonDeveloperRepositoryImpl implements DeveloperRepository {
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    private final String DEVELOPER_FILE_PATH = "src/main/resources/developers.json";

    @Override
    public Developer save(Developer developer) {
        List<Developer> allDev = getAllInternal();
        try(FileWriter writer = new FileWriter(DEVELOPER_FILE_PATH)){
            allDev.add(developer);
            writer.write(GSON.toJson(allDev));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return developer;
    }

    @Override
    public Developer getById(Integer id) {
        return getAllInternal().stream()
                .filter(d -> d.getId().equals(id))
                .filter(d -> d.getStatus() == Status.ACTIVE)
                .findFirst()
                .orElse(null);

    }

    @Override
    public List<Developer> getAll() {
        return getAllInternal()
                .stream()
                .filter(d -> d.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList());
    }

    @Override
    public Developer update(Developer developer) {
        List<Developer> allDev = getAllInternal();
        allDev.stream()
                .filter(s -> s.getId().equals(developer.getId()))
                .filter(s -> s.getStatus() == Status.ACTIVE)
                .forEach(s -> allDev.set(allDev.indexOf(s), developer));

        writeToFile(allDev);

        return developer;
    }

    @Override
    public boolean deleteById(Integer id) {
        List<Developer> allDev = getAllInternal();
        boolean isPresent = allDev.stream()
                .filter(d -> d.getId().equals(id))
                .filter(d -> d.getStatus() == Status.ACTIVE)
                .findFirst()
                .map(d -> {
                    d.setStatus(Status.DELETED);
                    return d;
                }).isPresent();

        if(isPresent)
            writeToFile(allDev);

        return isPresent;
    }

    private List<Developer> getAllInternal(){
        List<Developer> allDev;
        try(FileReader reader = new FileReader(DEVELOPER_FILE_PATH)){
            Type targetClassType = new TypeToken<ArrayList<Developer>>() { }.getType();
            allDev = GSON.fromJson(reader, targetClassType);
            if(allDev == null)
                allDev = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return allDev;
    }

    private void writeToFile(List<Developer> allDev){
        try(FileWriter writer = new FileWriter(DEVELOPER_FILE_PATH)){
            writer.write(GSON.toJson(allDev));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
