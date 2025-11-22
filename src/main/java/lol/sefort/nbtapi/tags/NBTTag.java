package lol.sefort.nbtapi.tags;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTTag {

    private String name;

    public NBTTag() {
        this("");
    }

    public NBTTag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract byte getTagId();

    public abstract void read(DataInput input) throws IOException;

    public abstract void write(DataOutput output) throws IOException;

    public abstract NBTTag copy();

    public abstract String toSNBT();

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(\"" + name + "\"): " + toSNBT();
    }

    public static final byte TAG_END = 0;
    public static final byte TAG_BYTE = 1;
    public static final byte TAG_SHORT = 2;
    public static final byte TAG_INT = 3;
    public static final byte TAG_LONG = 4;
    public static final byte TAG_FLOAT = 5;
    public static final byte TAG_DOUBLE = 6;
    public static final byte TAG_BYTE_ARRAY = 7;
    public static final byte TAG_STRING = 8;
    public static final byte TAG_LIST = 9;
    public static final byte TAG_COMPOUND = 10;
    public static final byte TAG_INT_ARRAY = 11;
    public static final byte TAG_LONG_ARRAY = 12;
}