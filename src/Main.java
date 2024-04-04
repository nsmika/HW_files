import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        StringBuilder log = new StringBuilder();
        List<String> directoriesToCreate = List.of(
                "T:/Games/",
                "T:/Games/src",
                "T:/Games/res",
                "T:/Games/savegames",
                "T:/Games/temp",
                "T:/Games/src/main",
                "T:/Games/src/test",
                "T:/Games/res/drawables",
                "T:/Games/res/vectors",
                "T:/Games/res/icons"
        );

        for (String directory : directoriesToCreate) {
            createDirectory(directory, log);
        }
        createFile("T:/Games/src/main/Main.java", log);
        createFile("T:/Games/src/main/Utils.java", log);
        createFile("T:/Games/temp/temp.txt", log);

        writeLogToFile("T:/Games/temp/temp.txt", log);


        GameProgress save1 = new GameProgress(100, 1, 5, 150);
        GameProgress save2 = new GameProgress(400, 3, 14, 200);
        GameProgress save3 = new GameProgress(400, 3, 14, 200);

        ArrayList<String> saveGames = new ArrayList<>();
        saveGames.add("T:/Games/savegames/save1.dat");
        saveGames.add("T:/Games/savegames/save2.dat");
        saveGames.add("T:/Games/savegames/save3.dat");

        saveGame("T:/Games/savegames/save1.dat", save1);
        saveGame("T:/Games/savegames/save2.dat", save2);
        saveGame("T:/Games/savegames/save3.dat", save3);

        ArrayList<String> filesToZip = new ArrayList<>();
        filesToZip.add("T:/Games/savegames/save1.dat");
        filesToZip.add("T:/Games/savegames/save2.dat");

        zipFiles("T:/Games/savegames/saves.zip", filesToZip);

        deleteFiles(saveGames);
    }


    private static void createDirectory(String path, StringBuilder log) {
        File directory = new File(path);
        if (directory.exists() || directory.mkdir()) {
            log.append("Directory created: ").append(path).append("\n");
        } else {
            log.append("Failed to create directory: ").append(path).append("\n");
        }
    }

    private static void createFile(String path, StringBuilder log) {
        File file = new File(path);
        try {
            if (file.createNewFile()) {
                log.append("File created: ").append(path).append("\n");
            } else {
                log.append("Failed to create file: ").append(path).append("\n");
            }
        } catch (IOException e) {
            log.append("Error creating file ").append(path).append(": ").append(e.getMessage()).append("\n");
        }
    }

    private static void writeLogToFile(String filePath, StringBuilder log) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(log.toString());
            System.out.println("Log written to file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing log to file: " + e.getMessage());
        }
    }

    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(gameProgress);
            System.out.println("Saving is done.");
        } catch (IOException e) {
            System.err.println("Saving failed: " + e.getMessage());
        }
    }

    private static void zipFiles(String zipFilePath, ArrayList<String> filesToZip) {
        try (FileOutputStream fos = new FileOutputStream(zipFilePath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (String filePath : filesToZip) {
                File file = new File(filePath);
                FileInputStream fis = new FileInputStream(file);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zos.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zos.write(bytes, 0, length);
                }
                fis.close();
                zos.closeEntry();
                System.out.println("File '" + file.getName() + "' add to archive.");
                if (file.delete()) {
                    System.out.println("File '" + file.getName() + "' deleted.");
                } else {
                    System.out.println("Deleted failed '" + file.getName() + "'.");
                }
            }
            System.out.println("File archiving completed successfully.");
        } catch (IOException e) {
            System.err.println("Error creating the archive: " + e.getMessage());
        }
    }

    private static void deleteFiles(ArrayList<String> filesToDelete) {
        for (String filePath : filesToDelete) {
            File file = new File(filePath);
            if (file.delete()) {
                System.out.println("File '" + file.getName() + "' deleted from directory.");
            }
        }
    }
}