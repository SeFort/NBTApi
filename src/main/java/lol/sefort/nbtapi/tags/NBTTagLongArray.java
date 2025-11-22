package lol.sefort.nbtapi.tags;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagLongArray extends NBTTag {

    private long[] value;

    public NBTTagLongArray() {
        this(new long[0]);
    }

    public NBTTagLongArray(long[] value) {
        this("", value);
    }

    public NBTTagLongArray(String name) {
        this(name, new long[0]);
    }

    public NBTTagLongArray(String name, long[] value) {
        super(name);
        this.value = value;
    }

    public long[] getValue() {
        return value;
    }

    public void setValue(long[] value) {
        this.value = value;
    }

    @Override
    public byte getTagId() {
        return TAG_LONG_ARRAY;
    }

    @Override
    public void read(DataInput input) throws IOException {
        int length = input.readInt();
        this.value = new long[length];
        for (int i = 0; i < length; i++) {
            value[i] = input.readLong();
        }
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(value.length);
        for (long l : value) {
            output.writeLong(l);
        }
    }

    @Override
    public NBTTag copy() {
        return new NBTTagLongArray(getName(), Arrays.copyOf(value, value.length));
    }

    @Override
    public String toSNBT() {
        StringBuilder sb = new StringBuilder("[L;");
        for (int i = 0; i < value.length; i++) {
            if (i > 0) sb.append(",");
            sb.append(value[i]).append("L");
        }
        sb.append("]");
        return sb.toString();
    }
}