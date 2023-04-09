package org.example.view;

import org.example.controller.SkillController;
import org.example.model.Skill;

import java.util.Scanner;

public class SkillView {
    private final SkillController  skillController = new SkillController();
    private final Scanner reader = new Scanner(System.in);

    public void handleRequest(){
        showListOfCommands();

        String command;

        while(true){
            command = reader.nextLine();
            switch (command){
                case "-c":
                    createSkill();
                    break;
                case "-r":
                    getSkillById();
                    break;
                case "-ra":
                    getAllSkills();
                    break;
                case "-u":
                    updateSkill();
                    break;
                case "-d":
                    deleteSkill();
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
        System.out.printf("\n***Вы в редакторе хранилища skills.json***\n\n" +
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

    public void createSkill(){
        System.out.print("Введите название: ");
        String name = reader.nextLine();

        Skill skill = skillController.save(name);
        if(skill == null)
            System.out.println("Неверный формат\n" +
                                "Убедитесь, что строка не пустая и не превышает 16 символов");
        else
            System.out.printf("Создан новый скил: %s\n", skill);
    }

    public void getSkillById(){
        int id = validateId();
        Skill skill = skillController.getById(id);
        if(skill == null)
            System.out.println("Скил с таким id не существует");
        else
            System.out.printf("%s\n", skill);

    }

    public void getAllSkills(){
        for(Skill s: skillController.getAll())
            System.out.println(s);
    }

    public void updateSkill(){
        int id = validateId();
        Skill skill = skillController.getById(id);
        if(skill == null)
            System.out.println("Скил с таким id не существует");
        else {
            System.out.print("Введите новое название: ");
            String name = reader.nextLine();

            skill.setName(name);
            if(skillController.update(skill) == null)
                System.out.println("Неверный формат\n" +
                                    "Убедитесь, что строка не пустая и не превышает 16 символов");
            else
                System.out.printf("Скил изменён: %s\n", skill);
        }
    }

    public void deleteSkill(){
        int id = validateId();
        if(skillController.deleteById(id))
            System.out.printf("Скил с id %d удалён\n", id);
        else
            System.out.println("Скил с таким id не существует");
    }
}
