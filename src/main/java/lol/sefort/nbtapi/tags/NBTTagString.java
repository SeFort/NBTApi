package lol.sefort.nbtapi.tags;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagString extends NBTTag {
    private String value;

    public NBTTagString() { this(""); }
    public NBTTagString(String value) { this("", value); }
    public NBTTagString(String name, String value) { super(name); this.value = value; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    @Override public byte getTagId() { return TAG_STRING; }

    @Override
    public void read(DataInput input) throws IOException {
        int length = input.readUnsignedShort();
        byte[] bytes = new byte[length];
        input.readFully(bytes);
        this.value = new String(bytes, "UTF-8");
    }

    @Override
    public void write(DataOutput output) throws IOException {
        byte[] bytes = value.getBytes("UTF-8");
        output.writeShort(bytes.length);
        output.write(bytes);
    }

    @Override public NBTTag copy() { return new NBTTagString(getName(), value); }

    @Override
    public String toSNBT() {
        return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }
}