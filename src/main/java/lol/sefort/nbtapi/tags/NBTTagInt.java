package lol.sefort.nbtapi.tags;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInt extends NBTTag {
    private int value;

    public NBTTagInt() { this(0); }
    public NBTTagInt(int value) { this("", value); }
    public NBTTagInt(String name, int value) { super(name); this.value = value; }

    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }

    @Override public byte getTagId() { return TAG_INT; }
    @Override public void read(DataInput input) throws IOException { this.value = input.readInt(); }
    @Override public void write(DataOutput output) throws IOException { output.writeInt(value); }
    @Override public NBTTag copy() { return new NBTTagInt(getName(), value); }
    @Override public String toSNBT() { return String.valueOf(value); }
}