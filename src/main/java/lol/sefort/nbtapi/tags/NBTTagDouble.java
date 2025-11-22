package lol.sefort.nbtapi.tags;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagDouble extends NBTTag {
    private double value;

    public NBTTagDouble() { this(0.0); }
    public NBTTagDouble(double value) { this("", value); }
    public NBTTagDouble(String name, double value) { super(name); this.value = value; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    @Override public byte getTagId() { return TAG_DOUBLE; }
    @Override public void read(DataInput input) throws IOException { this.value = input.readDouble(); }
    @Override public void write(DataOutput output) throws IOException { output.writeDouble(value); }
    @Override public NBTTag copy() { return new NBTTagDouble(getName(), value); }
    @Override public String toSNBT() { return value + "d"; }
}