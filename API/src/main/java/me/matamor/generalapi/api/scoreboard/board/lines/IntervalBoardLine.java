package me.matamor.generalapi.api.scoreboard.board.lines;

import me.matamor.minesoundapi.scoreboard.board.BoardLine;

public abstract class IntervalBoardLine implements BoardLine {

    private final int interval;
    private final long millisInterval;

    protected long lastUpdate;

    public IntervalBoardLine(int interval) {
        this.interval = interval;
        this.millisInterval = interval * 50;
    }

    public int getInterval() {
        return interval;
    }

    public long getMillisInterval() {
        return millisInterval;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public boolean should() {
        return (millisInterval + lastUpdate) - System.currentTimeMillis() <= 0;
    }
}
