import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GameProgress implements Serializable {
    private static final long serialVersionUID = 1L;

    private int health;
    private int weapons;
    private int lvl;
    private double distance;

    public GameProgress(int health, int weapons, int lvl, double distance) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }
    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(gameProgress);
            System.out.println("Сохранение выполнено.");
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    public static void zipFiles(String zipFilePath, ArrayList<String> filesToZip) {
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
                System.out.println("Файл '" + file.getName() + "' добавлен в архив.");
                if (file.delete()) {
                    System.out.println("Файл '" + file.getName() + "' успешно удален.");
                } else {
                    System.out.println("Ошибка при удалении файла '" + file.getName() + "'.");
                }
            }
            System.out.println("Архивация файлов успешно выполнена.");
        } catch (IOException e) {
            System.err.println("Ошибка при создании архива: " + e.getMessage());
        }
    }
}