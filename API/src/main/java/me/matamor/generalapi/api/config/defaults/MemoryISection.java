package me.matamor.generalapi.api.config.defaults;

import lombok.Getter;
import me.matamor.generalapi.api.config.ISection;

import java.util.*;

public class MemoryISection implements ISection {

    private static final char SEPARATOR = '.';

    protected final Map<String, Object> content = new LinkedHashMap<>();

    @Getter
    private final ISection parent;

    MemoryISection() {
        this(null, null);
    }

    MemoryISection(ISection parent, Map<String, Object> content) {
        this.parent = parent;

        if (content != null) {
            this.content.putAll(content);
        }
    }

    private ISection findSection(String path) {
        int index = path.indexOf(SEPARATOR);
        if (index == -1) {
            return this;
        }

        String root = path.substring(0, index);
        Object section = this.content.get(root);

        return (section instanceof ISection ? (ISection) section : null);
    }


    @Override
    public List<String> getKeys(boolean deep) {
        List<String> keys = new ArrayList<>();

        if (deep) {
            for (Map.Entry<String, Object> entry : this.content.entrySet()) {
                keys.add(entry.getKey());

                if (entry.getValue() instanceof ISection) {
                    for (String key : ((ISection) entry.getValue()).getKeys(true)) {
                        keys.add(entry.getKey() + "." + key);
                    }
                }
            }
        } else {
            keys.addAll(this.content.keySet());
        }

        return keys;
    }

    @Override
    public Map<String, Object> getValues(boolean deep) {
        Map<String, Object> values = new LinkedHashMap<>();

        if (deep) {
            for (Map.Entry<String, Object> entry : this.content.entrySet()) {
                if (entry.getValue() instanceof ISection) {
                    values.put(entry.getKey(), ((ISection) entry.getValue()).getValues(true));
                } else {
                    values.put(entry.getKey(), entry.getValue());
                }
            }
        } else {
            values.putAll(this.content);
        }

        return values;
    }

    @Override
    public boolean contains(String path) {
        int index = path.indexOf(SEPARATOR);
        if (index == -1) {
            return this.content.containsKey(path);
        } else {
            ISection section = getSection(path);
            String subPath = path.substring(index + 1, path.length());

            return section != null && section.contains(subPath);
        }
    }

    @Override
    public Object get(String path) {
        int index = path.indexOf(SEPARATOR);
        if (index == -1) {
            return this.content.get(path);
        } else {
            ISection section = getSection(path);
            String subPath = path.substring(index + 1, path.length());

            return (section == null ? null : section.get(subPath));
        }
    }

    @Override
    public Object get(String path, Object defaultValue) {
        Object object = get(path);
        return object == null ? defaultValue : object;
    }

    @Override
    public void set(String path, Object value) {
        int index = path.indexOf(SEPARATOR);
        if (index == -1) {
            this.content.put(path, value);
        } else {
            String root = path.substring(0, index);
            Object section = this.content.get(root);

            if (!(section instanceof ISection)) {
                section = new MemoryISection(this.parent, null);
                this.content.put(root, section);
            }

            String subPath = path.substring(index + 1, path.length());
            ((ISection) section).set(subPath, value);
        }
    }

    @Override
    public String getString(String path) {
        Object object = get(path);
        return (object instanceof String ? (String) object : null);
    }

    @Override
    public String getString(String path, String defaultValue) {
        Object object = get(path);
        return (object instanceof String ? (String) object : defaultValue);
    }

    @Override
    public int getInt(String path) {
        Object object = get(path);
        return (object instanceof Number ? ((Number) object).intValue() : 0);
    }

    @Override
    public int getInt(String path, int defaultValue) {
        Object object = get(path);
        return (object instanceof Number ? ((Number) object).intValue() : defaultValue);
    }

    @Override
    public boolean getBoolean(String path) {
        Object object = get(path);
        return (object instanceof Boolean ? (Boolean) object : false);
    }

    @Override
    public boolean getBoolean(String path, boolean defaultValue) {
        Object object = get(path);
        return (object instanceof Boolean ? (Boolean) object : defaultValue);
    }

    @Override
    public double getDouble(String path) {
        Object object = get(path);
        return (object instanceof Number ? ((Number) object).doubleValue() : 0);
    }

    @Override
    public double getDouble(String path, double defaultValue) {
        Object object = get(path);
        return (object instanceof Number ? ((Number) object).doubleValue() : defaultValue);
    }

    @Override
    public long getLong(String path) {
        Object object = get(path);
        return (object instanceof Number ? ((Number) object).longValue() : 0);
    }

    @Override
    public long getLong(String path, long defaultValue) {
        Object object = get(path);
        return (object instanceof Number ? ((Number) object).longValue() : defaultValue);
    }

    @Override
    public List<?> getList(String path) {
        Object object = get(path);
        return (object instanceof List ? (List) object : Collections.emptyList());
    }

    @Override
    public List<?> getList(String path, List<?> defaultValue) {
        Object object = get(path);
        return (object instanceof List ? (List) object : defaultValue);
    }

    @Override
    public List<String> getStringList(String path) {
        List<?> rawList = getList(path);
        List<String> list = new ArrayList<>();

        for (Object object : rawList) {
            if (object instanceof String) {
                list.add((String) object);
            }
        }

        return list;
    }

    @Override
    public List<Integer> getIntegerList(String path) {
        List<?> rawList = getList(path);
        List<Integer> list = new ArrayList<>();

        for (Object object : rawList) {
            if (object instanceof Number) {
                list.add(((Number) object).intValue());
            }
        }

        return list;
    }

    @Override
    public List<Boolean> getBooleanList(String path) {
        List<?> rawList = getList(path);
        List<Boolean> list = new ArrayList<>();

        for (Object object : rawList) {
            if (object instanceof Boolean) {
                list.add((Boolean) object);
            }
        }

        return list;
    }

    @Override
    public List<Double> getDoubleList(String path) {
        List<?> rawList = getList(path);
        List<Double> list = new ArrayList<>();

        for (Object object : rawList) {
            if (object instanceof Number) {
                list.add(((Number) object).doubleValue());
            }
        }

        return list;
    }

    @Override
    public List<Float> getFloatList(String path) {
        List<?> rawList = getList(path);
        List<Float> list = new ArrayList<>();

        for (Object object : rawList) {
            if (object instanceof Number) {
                list.add(((Number) object).floatValue());
            }
        }

        return list;
    }

    @Override
    public List<Long> getLongList(String path) {
        List<?> rawList = getList(path);
        List<Long> list = new ArrayList<>();

        for (Object object : rawList) {
            if (object instanceof Number) {
                list.add(((Number) object).longValue());
            }
        }

        return list;
    }

    @Override
    public List<Byte> getByteList(String path) {
        List<?> rawList = getList(path);
        List<Byte> list = new ArrayList<>();

        for (Object object : rawList) {
            if (object instanceof Number) {
                list.add(((Number) object).byteValue());
            }
        }

        return list;
    }

    @Override
    public List<Character> getCharacterList(String path) {
        List<?> rawList = getList(path);
        List<Character> list = new ArrayList<>();

        for (Object object : rawList) {
            if (object instanceof Character) {
                list.add((Character) object);
            }
        }

        return list;
    }

    @Override
    public List<Short> getShortList(String path) {
        List<?> rawList = getList(path);
        List<Short> list = new ArrayList<>();

        for (Object object : rawList) {
            if (object instanceof Number) {
                list.add(((Number) object).shortValue());
            }
        }

        return list;
    }

    @Override
    public ISection getSection(String path) {
        int index = path.indexOf(SEPARATOR);
        if (index == -1) {
            Object section = this.content.get(path);
            return (section instanceof ISection ? (ISection) section : null);
        }

        String root = path.substring(0, index);
        Object section = this.content.get(root);

        if (section instanceof ISection) {
            String subPath = path.substring(index + 1, path.length());
            return ((ISection) section).getSection(subPath);
        }

        return null;
    }

    @Override
    public boolean isSection(String path) {
        return getSection(path) != null;
    }
}
