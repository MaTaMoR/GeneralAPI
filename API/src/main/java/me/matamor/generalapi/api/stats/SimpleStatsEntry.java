package me.matamor.generalapi.api.stats;

import lombok.Getter;
import lombok.Setter;

public class SimpleStatsEntry implements StatsEntry {

    @Getter
    private final PlayerStats parent;

    @Getter
    private final String name;

    @Getter @Setter
    private int games;

    @Getter @Setter
    private int wins;

    @Getter @Setter
    private int loses;

    @Getter @Setter
    private int kills;

    @Getter @Setter
    private int deaths;

    @Getter @Setter
    private int brokenBlocks;

    @Getter @Setter
    private int placedBlocks;

    @Getter @Setter
    private int hits;

    @Getter @Setter
    private int shoots;

    @Getter @Setter
    private int arrowHits;

    @Getter @Setter
    private int walkedBlocks;

    @Getter @Setter
    private long timePlayed;

    public SimpleStatsEntry(PlayerStats parent, String name) {
        this.parent = parent;
        this.name = name;
    }
}
