package lol.sefort.nbtapi.tags;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagByteArray extends NBTTag {

    private byte[] value;

    public NBTTagByteArray() {
        this(new byte[0]);
    }

    public NBTTagByteArray(byte[] value) {
        this("", value);
    }

    public NBTTagByteArray(String name) {
        this(name, new byte[0]);
    }

    public NBTTagByteArray(String name, byte[] value) {
        super(name);
        this.value = value;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    @Override
    public byte getTagId() {
        return TAG_BYTE_ARRAY;
    }

    @Override
    public void read(DataInput input) throws IOException {
        int length = input.readInt();
        this.value = new byte[length];
        input.readFully(value);
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(value.length);
        output.write(value);
    }

    @Override
    public NBTTag copy() {
        return new NBTTagByteArray(getName(), Arrays.copyOf(value, value.length));
    }

    @Override
    public String toSNBT() {
        StringBuilder sb = new StringBuilder("[B;");
        for (int i = 0; i < value.length; i++) {
            if (i > 0) sb.append(",");
            sb.append(value[i]).append("b");
        }
        sb.append("]");
        return sb.toString();
    }
}