package me.matamor.generalapi.api.reflections.conversion;

public class ConverterPair<A, B> {

    private final Converter<A> converterA;
    private final Converter<B> converterB;

    public ConverterPair(Converter<A> converterA, Converter<B> converterB) {
        this.converterA = converterA;
        this.converterB = converterB;
    }

    public Class<A> getOutputTypeA() {
        return this.converterA.getOutputType();
    }

    public Class<B> getOutputTypeB() {
        return this.converterB.getOutputType();
    }

    public Converter<A> getConverterA() {
        return converterA;
    }

    public Converter<B> getConverterB() {
        return converterB;
    }

    public A convertA(Object value) {
        return converterA.convert(value);
    }

    public B convertB(Object value) {
        return converterB.convert(value);
    }

    public A convertA(Object value, A def) {
        return converterA.convert(value, def);
    }

    public B convertB(Object value, B def) {
        return converterB.convert(value, def);
    }

    public ConverterPair<B, A> reverse() {
        return new ConverterPair<B, A>(converterB, converterA);
    }
}