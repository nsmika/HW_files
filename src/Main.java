import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        StringBuilder log = new StringBuilder();

        // Создание директорий
        File gamesDir = new File("T:/Games");
        File srcDir = new File(gamesDir, "src");
        boolean srcDirCreated = srcDir.mkdir();
        log.append("Папка src создана: ").append(srcDirCreated).append("\n");

        File resDir = new File(gamesDir, "res");
        boolean resDirCreated = resDir.mkdir();
        log.append("Папка res создана: ").append(resDirCreated).append("\n");

        File savegamesDir = new File(gamesDir, "savegames");
        boolean savegamesDirCreated = savegamesDir.mkdir();
        log.append("Папка savegames создана: ").append(savegamesDirCreated).append("\n");

        File tempDir = new File(gamesDir, "temp");
        boolean tempDirCreated = tempDir.mkdir();
        log.append("Папка temp создана: ").append(tempDirCreated).append("\n");

        // Создание файлов
        try {
            File mainDir = new File(srcDir, "main");
            boolean mainDirCreated = mainDir.mkdir();
            log.append("Папка main создана: ").append(mainDirCreated).append("\n");

            File testDir = new File(srcDir, "test");
            boolean testDirCreated = testDir.mkdir();
            log.append("Папка test создана: ").append(testDirCreated).append("\n");

            File mainJava = new File(mainDir, "Main.java");
            boolean mainJavaCreated = mainJava.createNewFile();
            log.append("Файл Main.java создан: ").append(mainJavaCreated).append("\n");

            File utilsJava = new File(mainDir, "Utils.java");
            boolean utilsJavaCreated = utilsJava.createNewFile();
            log.append("Файл Utils.java создан: ").append(utilsJavaCreated).append("\n");

            File drawablesDir = new File(resDir, "drawables");
            boolean drawablesDirCreated = drawablesDir.mkdir();
            log.append("Папка drawables создана: ").append(drawablesDirCreated).append("\n");

            File vectorsDir = new File(resDir, "vectors");
            boolean vectorsDirCreated = vectorsDir.mkdir();
            log.append("Папка vectors создана: ").append(vectorsDirCreated).append("\n");

            File iconsDir = new File(resDir, "icons");
            boolean iconsDirCreated = iconsDir.mkdir();
            log.append("Папка icons создана: ").append(iconsDirCreated).append("\n");

            File tempFile = new File(tempDir, "temp.txt");
            boolean tempFileCreated = tempFile.createNewFile();
            log.append("Файл temp.txt создан: ").append(tempFileCreated).append("\n");

            // Запись лога в файл temp.txt
            FileWriter writer = new FileWriter(tempFile);
            writer.write(log.toString());
            writer.close();
        } catch (IOException e) {
            System.err.println("Ошибка при создании файла: " + e.getMessage());
        }
        GameProgress save1 = new GameProgress(100, 1, 5, 150);
        GameProgress save2 = new GameProgress(400, 3, 14, 200);
        GameProgress save3 = new GameProgress(400, 3, 14, 200);
        GameProgress.saveGame("T:/Games/savegames/save1.dat",save1);
        GameProgress.saveGame("T:/Games/savegames/save2.dat",save2);
        GameProgress.saveGame("T:/Games/savegames/save3.dat",save3);
        ArrayList<String> filesToZip = new ArrayList<>();
        filesToZip.add("T:/Games/savegames/save1.dat");
        filesToZip.add("T:/Games/savegames/save2.dat");
        GameProgress.zipFiles("T:/Games/savegames/saves.zip",filesToZip);
    }
}