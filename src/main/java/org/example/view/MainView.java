package org.example.view;

import java.util.Scanner;

public class MainView {
    private final DeveloperView developerView = new DeveloperView();
    private final SkillView skillView = new SkillView();
    private final SpecialtyView specialtyView = new SpecialtyView();
    private final Scanner reader = new Scanner(System.in);

    public void handleStartRequest(){
        showListOfCommands();

        String command;

        while(true){
            command = reader.nextLine();

            switch (command){
                case "1":
                    developerView.handleRequest();
                    break;
                case "2":
                    skillView.handleRequest();
                    break;
                case "3":
                    specialtyView.handleRequest();
                    break;
                case "-help":
                    showListOfCommands();
                    break;
                case "-exit":
                    System.out.println("Завершение программы");
                    return;
                default:
                    System.out.println("Вы ввели неверную команду\n" +
                                        "список команд: -help");
            }

        }
    }

    private static void showListOfCommands(){
        System.out.printf("\n***Вы в главном меню***\n\n" +
                "Команды для работы с хранилищем данных:\n" +
                "%-19s 1\n" +
                "%-19s 2\n" +
                "%-19s 3\n" +
                "%-18s -help\n" +
                "%-18s -exit\n", "developers.json:", "skills.json:", "specialties.json:", "список команд:", "завершить работу:");
    }
}

