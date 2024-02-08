package org.example.fileSystem;

import java.io.*;

public class FileSystem {
    public static void main(String[] args) throws IOException {
//        readDataFromCSVFile();
//        createFile("tmp.txt");
        append("tmp.txt", "hello world");
//        addAt("tmp.txt", 0, "hi, ");
//        deleteFile("tmp.txt");
    }

    private static void addAt(String fileName, int position, String inputString) {
        String path = "/Users/tanmeshnm/dev/java-basics/src/main/java/org/example/scratchpad/" + fileName;
        File file = new File(path);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();

            content.insert(position, inputString);

            // Write the updated content back to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void deleteFile(String fileName) {
        String path = "/Users/tanmeshnm/dev/java-basics/src/main/java/org/example/scratchpad/" + fileName;
        File file = new File(path);

        if (file.exists()) {
            file.delete();
        }
    }

    private static void append(String fileName, String inputText) throws IOException {
        String path = "/Users/tanmeshnm/dev/java-basics/src/main/java/org/example/scratchpad/" + fileName;
        File file = new File(path);
        if (!file.exists()) {
            createFile(fileName);
        }

        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(inputText);
        bufferedWriter.close();
    }

    private static void createFile(String fileName) throws IOException {
        String path = "/Users/tanmeshnm/dev/java-basics/src/main/java/org/example/scratchpad/" + fileName;

        File file = new File(path);

        file.createNewFile();
    }

    private static void readDataFromCSVFile() {
        String path = "/Users/tanmeshnm/dev/java-basics/src/main/java/org/example/scratchpad/tmp.csv";

        File file = new File(path);
        if (file.exists()) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                bufferedReader.readLine(); // Skip the header line

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split(",");
                    String name = data[0];
                    int age = Integer.parseInt(data[1]);
                    String city = data[2];

                    System.out.println("Name: " + name);
                    System.out.println("Age: " + age);
                    System.out.println("City: " + city);
                    System.out.println();
                }
            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Error reading the file: " + e.getMessage());
            }
        } else {
            System.out.println("File doesn't exist.");
        }
    }
}

