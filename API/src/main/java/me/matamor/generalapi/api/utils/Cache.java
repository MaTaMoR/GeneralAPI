package me.matamor.generalapi.api.utils;

public abstract class Cache<E> {

    private volatile E value;
    private final long timeMS;
    private long nextCache = -1;

    public Cache(long timeMS) {
        this(timeMS, false);
    }

    public Cache(long timeMS, boolean forceRefresh) {
        this.timeMS = timeMS;
        if (forceRefresh) {
            this.forceRefresh();
        }
    }

    public final E get() {
        this.checkCache();
        return this.value;
    }

    private synchronized void checkCache() {
        if (this.nextCache < 0 || System.currentTimeMillis() > this.nextCache) {
            this.setNextCache();
        }
    }

    private void setNextCache() {
        this.update();
        this.nextCache = System.currentTimeMillis() + this.timeMS;
    }

    protected abstract void update();

    protected E getCurrentValue() {
        return this.value;
    }

    protected void setCurrentValue(E value) {
        this.value = value;
    }

    public final void forceRefresh() {
        this.setNextCache();
    }
}