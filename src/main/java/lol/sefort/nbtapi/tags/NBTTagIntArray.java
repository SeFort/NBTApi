package lol.sefort.nbtapi.tags;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagIntArray extends NBTTag {

    private int[] value;

    public NBTTagIntArray() {
        this(new int[0]);
    }

    public NBTTagIntArray(int[] value) {
        this("", value);
    }

    public NBTTagIntArray(String name) {
        this(name, new int[0]);
    }

    public NBTTagIntArray(String name, int[] value) {
        super(name);
        this.value = value;
    }

    public int[] getValue() {
        return value;
    }

    public void setValue(int[] value) {
        this.value = value;
    }

    @Override
    public byte getTagId() {
        return TAG_INT_ARRAY;
    }

    @Override
    public void read(DataInput input) throws IOException {
        int length = input.readInt();
        this.value = new int[length];
        for (int i = 0; i < length; i++) {
            value[i] = input.readInt();
        }
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(value.length);
        for (int i : value) {
            output.writeInt(i);
        }
    }

    @Override
    public NBTTag copy() {
        return new NBTTagIntArray(getName(), Arrays.copyOf(value, value.length));
    }

    @Override
    public String toSNBT() {
        StringBuilder sb = new StringBuilder("[I;");
        for (int i = 0; i < value.length; i++) {
            if (i > 0) sb.append(",");
            sb.append(value[i]);
        }
        sb.append("]");
        return sb.toString();
    }
}