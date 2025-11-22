package lol.sefort.nbtapi.world;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorldScanner {

    private File savesFolder;
    private List<MinecraftWorld> worlds;

    public WorldScanner(File savesFolder) {
        this.savesFolder = savesFolder;
        this.worlds = new ArrayList<>();
    }

    public static WorldScanner createDefault() {
        return new WorldScanner(getDefaultSavesFolder());
    }

    public static File getMinecraftRoot() {
        String userDir = System.getProperty("user.dir");
        File currentDir = new File(userDir);

        if (isMinecraftRoot(currentDir)) {
            return currentDir;
        }

        String minecraftHome = System.getenv("MINECRAFT_HOME");
        if (minecraftHome != null && !minecraftHome.isEmpty()) {
            File dir = new File(minecraftHome);
            if (isMinecraftRoot(dir)) {
                return dir;
            }
        }

        return currentDir;
    }

    private static boolean isMinecraftRoot(File dir) {
        if (!dir.exists() || !dir.isDirectory()) {
            return false;
        }

        File saves = new File(dir, "saves");

        return saves.exists();
    }

    public static File getDefaultSavesFolder() {
        File root = getMinecraftRoot();
        return new File(root, "saves");
    }

    public List<MinecraftWorld> scan() {
        worlds.clear();

        if (!savesFolder.exists() || !savesFolder.isDirectory()) {
            System.err.println("The saves folder was not found: " + savesFolder.getAbsolutePath());
            return worlds;
        }

        File[] folders = savesFolder.listFiles();
        if (folders == null) {
            return worlds;
        }

        for (File folder : folders) {
            if (!folder.isDirectory()) {
                continue;
            }

            File levelDat = new File(folder, "level.dat");
            if (!levelDat.exists()) {
                continue;
            }

            try {
                MinecraftWorld world = new MinecraftWorld(folder);
                worlds.add(world);
            } catch (IOException e) {
                System.err.println("Failed to load world from folder: " + folder.getName());
                e.printStackTrace();
            }
        }

        return worlds;
    }

    public List<MinecraftWorld> getWorlds() {
        return worlds;
    }

    public MinecraftWorld findWorldByName(String name) {
        for (MinecraftWorld world : worlds) {
            if (world.getWorldName().equals(name)) {
                return world;
            }
        }
        return null;
    }

    public MinecraftWorld findWorldByFolder(String folderName) {
        for (MinecraftWorld world : worlds) {
            if (world.getWorldFolder().getName().equals(folderName)) {
                return world;
            }
        }
        return null;
    }

    public File getSavesFolder() {
        return savesFolder;
    }

    public int getWorldCount() {
        return worlds.size();
    }

    @Override
    public String toString() {
        return "WorldScanner{" +
                "savesFolder=" + savesFolder.getAbsolutePath() +
                ", worlds=" + worlds.size() +
                '}';
    }
}