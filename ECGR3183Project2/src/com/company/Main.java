package com.company;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    final static InstructionSet is = new InstructionSet();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int x =0;
        int count = 0;
        while(x != 5) {
            ArrayList<String> instructions = new ArrayList<>();
            if(count != 0) {
                System.out.println("");
            }
            System.out.println("Please Select one of the following options:");
            System.out.println("Run Test 1: (1)");
            System.out.println("Run Test 2: (2)");
            System.out.println("Run Test 3: (3)");
            System.out.println("Write your own commands: (4)");
            System.out.println("Exit: (5)");
            System.out.print("Selection: ");
            x = sc.nextInt();
            if(x != 5) {
                switch (x) {
                    case 1:
                        instructions = readFile("Test1.txt");
                        break;
                    case 2:
                        instructions = readFile("Test2.txt");
                        break;
                    case 3:
                        instructions = readFile("Test3.txt");
                        break;
                    case 4:
                        instructions = getInputFromUser();
                        break;
                }
            }
            printContents(instructions);
            runCommands(instructions);
            count++;
        }

        System.out.println("HAVE A GREAT DAY AND PLEASE GIVE US A 105%!!! :)");

    }

    public static void runCommands(ArrayList<String> instruc){
        for(int i=0; i < instruc.size(); i++) {
            is.assembly(instruc.get(i));
        }
    }

    public static void printContents(ArrayList<String> instruc){
        for(int i=0; i < instruc.size(); i++) {
            System.out.println(instruc.get(i));
        }
    }

    public static ArrayList<String> readFile(String fileName){
        ArrayList<String> instructions = new ArrayList<>();
        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                instructions.add(line);
            }
            fileReader.close();
        } catch (Exception e) {
            System.out.println("Failed to read the file!");
            e.printStackTrace();
        }
        return instructions;
    }

    public static ArrayList<String> getInputFromUser(){
        ArrayList<String> instruc = new ArrayList<>();
        Scanner x = new Scanner(System.in);
        String line = "";
        int lineCount = 0;
        System.out.println("Enter new Assembly commands and when done enter \"Q\"");
        while(!line.trim().equals("Q")){
            System.out.print("Line " + (lineCount + 1) +": ");
            line = x.nextLine();
            if(!line.trim().equals("Q")) {
                instruc.add(line.trim());
            }
            lineCount++;
        }
        return instruc;
    }

}





