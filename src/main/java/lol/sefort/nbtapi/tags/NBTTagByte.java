package lol.sefort.nbtapi.tags;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByte extends NBTTag {

    private byte value;

    public NBTTagByte() {
        this((byte) 0);
    }

    public NBTTagByte(byte value) {
        this("", value);
    }

    public NBTTagByte(String name, byte value) {
        super(name);
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    @Override
    public byte getTagId() {
        return TAG_BYTE;
    }

    @Override
    public void read(DataInput input) throws IOException {
        this.value = input.readByte();
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeByte(value);
    }

    @Override
    public NBTTag copy() {
        return new NBTTagByte(getName(), value);
    }

    @Override
    public String toSNBT() {
        return value + "b";
    }
}