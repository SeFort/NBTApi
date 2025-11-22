package lol.sefort.nbtapi.tags;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort extends NBTTag {
    private short value;

    public NBTTagShort() { this((short) 0); }
    public NBTTagShort(short value) { this("", value); }
    public NBTTagShort(String name, short value) { super(name); this.value = value; }

    public short getValue() { return value; }
    public void setValue(short value) { this.value = value; }

    @Override public byte getTagId() { return TAG_SHORT; }
    @Override public void read(DataInput input) throws IOException { this.value = input.readShort(); }
    @Override public void write(DataOutput output) throws IOException { output.writeShort(value); }
    @Override public NBTTag copy() { return new NBTTagShort(getName(), value); }
    @Override public String toSNBT() { return value + "s"; }
}