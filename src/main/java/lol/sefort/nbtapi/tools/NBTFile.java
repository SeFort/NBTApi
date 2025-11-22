package lol.sefort.nbtapi.tools;

import lol.sefort.nbtapi.tags.*;
import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class NBTFile {

    private NBTTagCompound rootTag;
    private String rootName;
    private boolean compressed;

    public NBTFile() {
        this("", new NBTTagCompound());
    }

    public NBTFile(String rootName, NBTTagCompound rootTag) {
        this.rootName = rootName;
        this.rootTag = rootTag;
        this.compressed = true;
    }

    public static NBTFile read(File file) throws IOException {
        return read(file, true);
    }

    public static NBTFile read(File file, boolean compressed) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            return read(fis, compressed);
        }
    }

    public static NBTFile read(InputStream inputStream, boolean compressed) throws IOException {
        DataInputStream dis;

        if (compressed) {
            dis = new DataInputStream(new GZIPInputStream(inputStream));
        } else {
            dis = new DataInputStream(inputStream);
        }

        NBTFile nbtFile = new NBTFile();
        nbtFile.compressed = compressed;

        byte tagType = dis.readByte();

        if (tagType != NBTTag.TAG_COMPOUND) {
            throw new IOException();
        }

        int nameLength = dis.readUnsignedShort();
        byte[] nameBytes = new byte[nameLength];
        dis.readFully(nameBytes);
        nbtFile.rootName = new String(nameBytes, "UTF-8");

        nbtFile.rootTag = new NBTTagCompound(nbtFile.rootName);
        nbtFile.rootTag.read(dis);

        return nbtFile;
    }

    public void write(File file) throws IOException {
        write(file, compressed);
    }

    public void write(File file, boolean compressed) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            write(fos, compressed);
        }
    }

    public void write(OutputStream outputStream, boolean compressed) throws IOException {
        DataOutputStream dos;

        if (compressed) {
            dos = new DataOutputStream(new GZIPOutputStream(outputStream));
        } else {
            dos = new DataOutputStream(outputStream);
        }

        dos.writeByte(NBTTag.TAG_COMPOUND);

        byte[] nameBytes = rootName.getBytes("UTF-8");
        dos.writeShort(nameBytes.length);
        dos.write(nameBytes);

        rootTag.write(dos);

        dos.close();
    }

    public NBTTagCompound getRoot() {
        return rootTag;
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
        this.rootTag.setName(rootName);
    }

    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    @Override
    public String toString() {
        return "NBTFile{" +
                "rootName='" + rootName + '\'' +
                ", compressed=" + compressed +
                ", rootTag=" + rootTag.toSNBT() +
                '}';
    }
}