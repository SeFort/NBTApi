package lol.sefort.nbtapi.tags;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagFloat extends NBTTag {
    private float value;

    public NBTTagFloat() { this(0.0f); }
    public NBTTagFloat(float value) { this("", value); }
    public NBTTagFloat(String name, float value) { super(name); this.value = value; }

    public float getValue() { return value; }
    public void setValue(float value) { this.value = value; }

    @Override public byte getTagId() { return TAG_FLOAT; }
    @Override public void read(DataInput input) throws IOException { this.value = input.readFloat(); }
    @Override public void write(DataOutput output) throws IOException { output.writeFloat(value); }
    @Override public NBTTag copy() { return new NBTTagFloat(getName(), value); }
    @Override public String toSNBT() { return value + "f"; }
}