package lol.sefort.nbtapi.tags;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NBTTagList extends NBTTag {

    private List<NBTTag> tags;
    private byte listType;

    public NBTTagList() {
        this("");
    }

    public NBTTagList(String name) {
        super(name);
        this.tags = new ArrayList<>();
        this.listType = TAG_END; // Пустой список
    }

    public NBTTagList(String name, byte listType) {
        super(name);
        this.tags = new ArrayList<>();
        this.listType = listType;
    }

    @Override
    public byte getTagId() {
        return TAG_LIST;
    }

    public byte getListType() {
        return listType;
    }

    public void add(NBTTag tag) {
        if (tags.isEmpty()) {
            listType = tag.getTagId();
        } else if (tag.getTagId() != listType) {
            throw new IllegalArgumentException("Попытка добавить тег типа " + tag.getTagId() +
                    " в список типа " + listType);
        }
        tags.add(tag);
    }

    public NBTTag get(int index) {
        return tags.get(index);
    }

    public int size() {
        return tags.size();
    }

    public NBTTag remove(int index) {
        return tags.remove(index);
    }

    public void clear() {
        tags.clear();
        listType = TAG_END;
    }

    public byte getByteAt(int index) {
        NBTTag tag = get(index);
        return tag instanceof NBTTagByte ? ((NBTTagByte) tag).getValue() : 0;
    }

    public int getIntAt(int index) {
        NBTTag tag = get(index);
        return tag instanceof NBTTagInt ? ((NBTTagInt) tag).getValue() : 0;
    }

    public String getStringAt(int index) {
        NBTTag tag = get(index);
        return tag instanceof NBTTagString ? ((NBTTagString) tag).getValue() : "";
    }

    public NBTTagCompound getCompoundAt(int index) {
        NBTTag tag = get(index);
        return tag instanceof NBTTagCompound ? (NBTTagCompound) tag : new NBTTagCompound();
    }

    @Override
    public void read(DataInput input) throws IOException {
        tags.clear();

        this.listType = input.readByte();

        int length = input.readInt();

        for (int i = 0; i < length; i++) {
            NBTTag tag = createTagByType(listType);
            if (tag != null) {
                tag.read(input);
                tags.add(tag);
            }
        }
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeByte(listType);

        output.writeInt(tags.size());

        for (NBTTag tag : tags) {
            tag.write(output);
        }
    }

    @Override
    public NBTTag copy() {
        NBTTagList copy = new NBTTagList(getName(), listType);
        for (NBTTag tag : tags) {
            copy.add(tag.copy());
        }
        return copy;
    }

    @Override
    public String toSNBT() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (NBTTag tag : tags) {
            if (!first) sb.append(",");
            first = false;
            sb.append(tag.toSNBT());
        }
        sb.append("]");
        return sb.toString();
    }

    private static NBTTag createTagByType(byte type) {
        switch (type) {
            case TAG_BYTE: return new NBTTagByte();
            case TAG_SHORT: return new NBTTagShort();
            case TAG_INT: return new NBTTagInt();
            case TAG_LONG: return new NBTTagLong();
            case TAG_FLOAT: return new NBTTagFloat();
            case TAG_DOUBLE: return new NBTTagDouble();
            case TAG_BYTE_ARRAY: return new NBTTagByteArray();
            case TAG_STRING: return new NBTTagString();
            case TAG_LIST: return new NBTTagList();
            case TAG_COMPOUND: return new NBTTagCompound();
            case TAG_INT_ARRAY: return new NBTTagIntArray();
            case TAG_LONG_ARRAY: return new NBTTagLongArray();
            default: return null;
        }
    }
}