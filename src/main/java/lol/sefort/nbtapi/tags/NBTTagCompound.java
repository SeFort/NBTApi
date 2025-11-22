package lol.sefort.nbtapi.tags;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class NBTTagCompound extends NBTTag {

    private Map<String, NBTTag> tags;

    public NBTTagCompound() {
        this("");
    }

    public NBTTagCompound(String name) {
        super(name);
        this.tags = new LinkedHashMap<>();
    }

    @Override
    public byte getTagId() {
        return TAG_COMPOUND;
    }

    public void setTag(String name, NBTTag tag) {
        tag.setName(name);
        tags.put(name, tag);
    }

    public NBTTag getTag(String name) {
        return tags.get(name);
    }

    public boolean hasKey(String name) {
        return tags.containsKey(name);
    }

    public void removeTag(String name) {
        tags.remove(name);
    }

    public Set<String> getKeys() {
        return tags.keySet();
    }

    public int size() {
        return tags.size();
    }

    public void setByte(String name, byte value) {
        setTag(name, new NBTTagByte(name, value));
    }

    public void setShort(String name, short value) {
        setTag(name, new NBTTagShort(name, value));
    }

    public void setInt(String name, int value) {
        setTag(name, new NBTTagInt(name, value));
    }

    public void setLong(String name, long value) {
        setTag(name, new NBTTagLong(name, value));
    }

    public void setFloat(String name, float value) {
        setTag(name, new NBTTagFloat(name, value));
    }

    public void setDouble(String name, double value) {
        setTag(name, new NBTTagDouble(name, value));
    }

    public void setString(String name, String value) {
        setTag(name, new NBTTagString(name, value));
    }

    public void setBoolean(String name, boolean value) {
        setByte(name, (byte) (value ? 1 : 0));
    }

    public byte getByte(String name) {
        NBTTag tag = getTag(name);
        return tag instanceof NBTTagByte ? ((NBTTagByte) tag).getValue() : 0;
    }

    public short getShort(String name) {
        NBTTag tag = getTag(name);
        return tag instanceof NBTTagShort ? ((NBTTagShort) tag).getValue() : 0;
    }

    public int getInt(String name) {
        NBTTag tag = getTag(name);
        return tag instanceof NBTTagInt ? ((NBTTagInt) tag).getValue() : 0;
    }

    public long getLong(String name) {
        NBTTag tag = getTag(name);
        return tag instanceof NBTTagLong ? ((NBTTagLong) tag).getValue() : 0;
    }

    public float getFloat(String name) {
        NBTTag tag = getTag(name);
        return tag instanceof NBTTagFloat ? ((NBTTagFloat) tag).getValue() : 0.0f;
    }

    public double getDouble(String name) {
        NBTTag tag = getTag(name);
        return tag instanceof NBTTagDouble ? ((NBTTagDouble) tag).getValue() : 0.0;
    }

    public String getString(String name) {
        NBTTag tag = getTag(name);
        return tag instanceof NBTTagString ? ((NBTTagString) tag).getValue() : "";
    }

    public boolean getBoolean(String name) {
        return getByte(name) != 0;
    }

    public NBTTagCompound getCompound(String name) {
        NBTTag tag = getTag(name);
        return tag instanceof NBTTagCompound ? (NBTTagCompound) tag : new NBTTagCompound();
    }

    public NBTTagList getList(String name) {
        NBTTag tag = getTag(name);
        return tag instanceof NBTTagList ? (NBTTagList) tag : new NBTTagList();
    }

    @Override
    public void read(DataInput input) throws IOException {
        tags.clear();

        while (true) {
            byte tagType = input.readByte();

            if (tagType == TAG_END) {
                break;
            }

            int nameLength = input.readUnsignedShort();
            byte[] nameBytes = new byte[nameLength];
            input.readFully(nameBytes);
            String tagName = new String(nameBytes, "UTF-8");

            NBTTag tag = createTagByType(tagType, tagName);
            if (tag != null) {
                tag.read(input);
                tags.put(tagName, tag);
            }
        }
    }

    @Override
    public void write(DataOutput output) throws IOException {
        for (NBTTag tag : tags.values()) {
            output.writeByte(tag.getTagId());

            byte[] nameBytes = tag.getName().getBytes("UTF-8");
            output.writeShort(nameBytes.length);
            output.write(nameBytes);

            tag.write(output);
        }

        output.writeByte(TAG_END);
    }

    @Override
    public NBTTag copy() {
        NBTTagCompound copy = new NBTTagCompound(getName());
        for (Map.Entry<String, NBTTag> entry : tags.entrySet()) {
            copy.setTag(entry.getKey(), entry.getValue().copy());
        }
        return copy;
    }

    @Override
    public String toSNBT() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, NBTTag> entry : tags.entrySet()) {
            if (!first) sb.append(",");
            first = false;
            sb.append(entry.getKey()).append(":").append(entry.getValue().toSNBT());
        }
        sb.append("}");
        return sb.toString();
    }

    private static NBTTag createTagByType(byte type, String name) {
        switch (type) {
            case TAG_BYTE: return new NBTTagByte(name, (byte) 0);
            case TAG_SHORT: return new NBTTagShort(name, (short) 0);
            case TAG_INT: return new NBTTagInt(name, 0);
            case TAG_LONG: return new NBTTagLong(name, 0L);
            case TAG_FLOAT: return new NBTTagFloat(name, 0.0f);
            case TAG_DOUBLE: return new NBTTagDouble(name, 0.0);
            case TAG_BYTE_ARRAY: return new NBTTagByteArray(name);
            case TAG_STRING: return new NBTTagString(name, "");
            case TAG_LIST: return new NBTTagList(name);
            case TAG_COMPOUND: return new NBTTagCompound(name);
            case TAG_INT_ARRAY: return new NBTTagIntArray(name);
            case TAG_LONG_ARRAY: return new NBTTagLongArray(name);
            default: return null;
        }
    }
}