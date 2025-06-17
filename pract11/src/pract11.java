import java.io.*;
import java.util.Scanner;

public class pract11 {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Введіть ім'я файлу: ");
        String filename = scanner.nextLine();

        boolean running = true;
        while (running) {
            displayMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        writeToFile(filename);
                        break;
                    case 2:
                        readFile(filename);
                        break;
                    case 3:
                        System.out.println("До побачення");
                        running = false;
                        break;
                    default:
                        System.out.println("Спробуйте ще раз.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка: Введіть число від 1 до 3.");
            } catch (Exception e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("""
                РЕДАКТОР
                1. Записати до файлу
                2. Прочитати вміст файлу
                3. Вийти з редактора
                """);
        System.out.print("Оберіть: ");
    }

    private static void writeToFile(String filename) {
        System.out.print("Перезаписати файл (1) Додати в кінець (2)? ");
        String writeMode = scanner.nextLine();

        boolean append = !writeMode.equals("1");

        try {
            FileWriter fileWriter = new FileWriter(filename, append);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            System.out.println("Введіть текст (для завершення введення введіть '/'):");

            String[] lines = new String[100];
            int lineCount = 0;

            while (true) {
                String line = scanner.nextLine();
                if (line.equals("/")) {
                    break;
                }

                if (lineCount < lines.length) {
                    lines[lineCount] = line;
                    lineCount++;
                } else {
                    System.out.println("Досягнуто максимальної кількості рядків.");
                    break;
                }
            }

            for (int i = 0; i < lineCount; i++) {
                writer.write(lines[i]);
                writer.newLine();
            }

            writer.close();
            System.out.println("Текст записано у файл '" + filename + "'");
        } catch (IOException e) {
            System.out.println("Помилка при записі у файл: " + e.getMessage());
        }
    }

    private static void readFile(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                System.out.println("Помилка: Файл '" + filename + "' не знайдено.");
                return;
            }

            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String[] lines = new String[1000];
            int lineCount = 0;

            String line;
            while ((line = reader.readLine()) != null && lineCount < lines.length) {
                lines[lineCount] = line;
                lineCount++;
            }

            reader.close();

            if (lineCount > 0) {
                System.out.println("\nВміст файлу '" + filename + "':");
                System.out.println("-".repeat(30));

                for (int i = 0; i < lineCount; i++) {
                    System.out.println(lines[i]);
                }

                System.out.println("-".repeat(30));
            } else {
                System.out.println("Файл '" + filename + "' порожній.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл '" + filename + "' не знайдено.");
        } catch (IOException e) {
            System.out.println("Помилка при читанні файлу: " + e.getMessage());
        }
    }
}
