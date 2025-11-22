package lol.sefort.nbtapi.world;

import lol.sefort.nbtapi.tools.NBTFile;
import lol.sefort.nbtapi.tags.*;
import java.io.File;
import java.io.IOException;

public class MinecraftWorld {

    private File worldFolder;
    private File levelDatFile;
    private NBTFile levelDat;
    private NBTTagCompound dataTag;

    public MinecraftWorld(File worldFolder) throws IOException {
        this.worldFolder = worldFolder;
        this.levelDatFile = new File(worldFolder, "level.dat");

        if (!levelDatFile.exists()) {
            throw new IOException("level.dat not found in folder: " + worldFolder.getAbsolutePath());
        }

        reload();
    }

    public void reload() throws IOException {
        this.levelDat = NBTFile.read(levelDatFile);
        this.dataTag = levelDat.getRoot().getCompound("Data");
    }

    public void save() throws IOException {
        levelDat.write(levelDatFile);
    }

    public File getWorldFolder() {
        return worldFolder;
    }

    public File getLevelDatFile() {
        return levelDatFile;
    }

    public NBTFile getLevelDat() {
        return levelDat;
    }

    public NBTTagCompound getDataTag() {
        return dataTag;
    }

    public String getWorldName() {
        return dataTag.getString("LevelName");
    }

    public void setWorldName(String name) {
        dataTag.setString("LevelName", name);
    }

    public int getGameType() {
        return dataTag.getInt("GameType");
    }

    public void setGameType(int gameType) {
        dataTag.setInt("GameType", gameType);
    }

    public String getGameTypeName() {
        switch (getGameType()) {
            case 0: return "Выживание";
            case 1: return "Творчество";
            case 2: return "Приключение";
            case 3: return "Наблюдатель";
            default: return "Неизвестно";
        }
    }

    public int getDifficulty() {
        return dataTag.getInt("Difficulty");
    }

    public void setDifficulty(int difficulty) {
        dataTag.setInt("Difficulty", difficulty);
    }

    public String getDifficultyName() {
        switch (getDifficulty()) {
            case 0: return "Мирный";
            case 1: return "Лёгкий";
            case 2: return "Нормальный";
            case 3: return "Сложный";
            default: return "Неизвестно";
        }
    }

    public long getSeed() {
        if (dataTag.hasKey("WorldGenSettings")) {
            NBTTagCompound settings = dataTag.getCompound("WorldGenSettings");
            return settings.getLong("seed");
        }
        return dataTag.getLong("RandomSeed");
    }

    public String getMinecraftVersion() {
        if (dataTag.hasKey("Version")) {
            NBTTagCompound version = dataTag.getCompound("Version");
            return version.getString("Name");
        }
        return "Неизвестно";
    }

    public int getDataVersion() {
        if (dataTag.hasKey("Version")) {
            NBTTagCompound version = dataTag.getCompound("Version");
            return version.getInt("Id");
        }
        return 0;
    }

    public long getTime() {
        return dataTag.getLong("Time");
    }

    public long getDayTime() {
        return dataTag.getLong("DayTime");
    }

    public int getSpawnX() {
        return dataTag.getInt("SpawnX");
    }

    public int getSpawnY() {
        return dataTag.getInt("SpawnY");
    }

    public int getSpawnZ() {
        return dataTag.getInt("SpawnZ");
    }

    public void setSpawnPoint(int x, int y, int z) {
        dataTag.setInt("SpawnX", x);
        dataTag.setInt("SpawnY", y);
        dataTag.setInt("SpawnZ", z);
    }

    public NBTTagCompound getGameRules() {
        return dataTag.getCompound("GameRules");
    }

    public String getGameRule(String ruleName) {
        NBTTagCompound rules = getGameRules();
        return rules.getString(ruleName);
    }

    public void setGameRule(String ruleName, String value) {
        NBTTagCompound rules = getGameRules();
        rules.setString(ruleName, value);
    }

    public NBTTagCompound getPlayer() {
        if (dataTag.hasKey("Player")) {
            return dataTag.getCompound("Player");
        }
        return null;
    }

    public boolean hasPlayer() {
        return dataTag.hasKey("Player");
    }

    public boolean isHardcore() {
        return dataTag.getBoolean("hardcore");
    }

    public void setHardcore(boolean hardcore) {
        dataTag.setBoolean("hardcore", hardcore);
    }

    public String getGeneratorName() {
        return dataTag.getString("generatorName");
    }

    @Override
    public String toString() {
        return "MinecraftWorld{" +
                "name='" + getWorldName() + '\'' +
                ", version='" + getMinecraftVersion() + '\'' +
                ", gameType=" + getGameTypeName() +
                ", difficulty=" + getDifficultyName() +
                ", seed=" + getSeed() +
                '}';
    }
}