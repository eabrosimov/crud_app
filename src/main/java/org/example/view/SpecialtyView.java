package org.example.view;

import org.example.controller.SpecialtyController;
import org.example.model.Specialty;

import java.util.Scanner;

public class SpecialtyView {
    private final SpecialtyController specialtyController = new SpecialtyController();
    private final Scanner reader = new Scanner(System.in);

    public void handleRequest(){
        showListOfCommands();

        String command;

        while(true){
            command = reader.nextLine();
            switch (command){
                case "-c":
                    createSpecialty();
                    break;
                case "-r":
                    getSpecialtyById();
                    break;
                case "-ra":
                    getAllSpecialties();
                    break;
                case "-u":
                    updateSpecialty();
                    break;
                case "-d":
                    deleteSpecialty();
                    break;
                case "-help":
                    showListOfCommands();
                    break;
                case "-exit":
                    System.out.println("Выход в главное меню");
                    return;
                default:
                    System.out.println("Вы ввели неверную команду\n" +
                                        "список команд: -help");
            }
        }
    }

    private void showListOfCommands(){
        System.out.printf("\n***Вы в редакторе хранилища specialties.json***\n\n" +
                "Список команд:\n" +
                "%-15s -c\n" +
                "%-15s -r\n" +
                "%-15s -ra\n" +
                "%-15s -u\n" +
                "%-15s -d\n" +
                "%-15s -help\n" +
                "%-15s -exit\n", "добавить:", "получить:", "получить всех:", "изменить:", "удалить:", "список команд:", "главное меню:");
    }

    private int validateId(){
        int id;
        while (true) {
            System.out.print("Введите id: ");
            if (reader.hasNextInt()) {
                id = reader.nextInt();
                break;
            } else {
                System.out.println("id не может содержать буквы или символы");
                reader.nextLine();
            }
        }
        reader.nextLine();

        return id;
    }

    public void createSpecialty(){
        System.out.print("Введите название: ");
        String name = reader.nextLine();

        Specialty specialty = specialtyController.save(name);
        if(specialty == null)
            System.out.println("Неверный формат\n" +
                                "Убедитесь, что строка не пустая и не превышает 16 символов");
        else
            System.out.printf("Создана новая специальность: %s\n", specialty);
    }

    public void getSpecialtyById(){
        int id = validateId();
        Specialty specialty = specialtyController.getById(id);
        if(specialty == null)
            System.out.println("Специальность с таким id не существует");
        else
            System.out.printf("%s\n", specialty);

    }

    public void getAllSpecialties(){
        for(Specialty s: specialtyController.getAll())
            System.out.println(s);
    }

    public void updateSpecialty(){
        int id = validateId();
        Specialty specialty = specialtyController.getById(id);
        if(specialty == null)
            System.out.println("Скил с таким id не существует");
        else {
            System.out.print("Введите новое название: ");
            String name = reader.nextLine();

            specialty.setName(name);
            if(specialtyController.update(specialty) == null)
                System.out.println("Неверный формат\n" +
                                    "Убедитесь, что строка не пустая и не превышает 16 символов");
            else
                System.out.printf("Специальность изменена: %s\n", specialty);
        }
    }

    public void deleteSpecialty(){
        int id = validateId();
        if(specialtyController.deleteById(id))
            System.out.printf("Специальность с id %d удалена\n", id);
        else
            System.out.println("Специальность с таким id не существует");
    }
}
