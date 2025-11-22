package lol.sefort.nbtapi.tags;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong extends NBTTag {
    private long value;

    public NBTTagLong() { this(0L); }
    public NBTTagLong(long value) { this("", value); }
    public NBTTagLong(String name, long value) { super(name); this.value = value; }

    public long getValue() { return value; }
    public void setValue(long value) { this.value = value; }

    @Override public byte getTagId() { return TAG_LONG; }
    @Override public void read(DataInput input) throws IOException { this.value = input.readLong(); }
    @Override public void write(DataOutput output) throws IOException { output.writeLong(value); }
    @Override public NBTTag copy() { return new NBTTagLong(getName(), value); }
    @Override public String toSNBT() { return value + "L"; }
}