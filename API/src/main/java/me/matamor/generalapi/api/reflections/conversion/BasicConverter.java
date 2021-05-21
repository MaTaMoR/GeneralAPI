package me.matamor.generalapi.api.reflections.conversion;

public abstract class BasicConverter<T> implements Converter<T> {

    private final Class<T> outputType;

    public BasicConverter(Class<T> outputType) {
        this.outputType = outputType;
    }

    @Override
    public Class<T> getOutputType() {
        return outputType;
    }

    protected abstract T convertSpecial(Object value, Class<?> valueType, T def);

    @Override
    @SuppressWarnings("unchecked")
    public final T convert(Object value, T def) {
        if (value == null) {
            return def;
        } else if (getOutputType().isInstance(value)) {
            return (T) value;
        } else {
            return convertSpecial(value, value.getClass(), def);
        }
    }

    @Override
    public final T convert(Object value) {
        return convert(value, null);
    }

    @Override
    public boolean isCastingSupported() {
        return false;
    }

    @Override
    public boolean isRegisterSupported() {
        return true;
    }
}
