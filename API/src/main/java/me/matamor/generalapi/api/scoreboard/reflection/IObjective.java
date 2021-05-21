package me.matamor.generalapi.api.scoreboard.reflection;

import me.matamor.minesoundapi.reflections.Packet;
import me.matamor.minesoundapi.reflections.PacketType;
import me.matamor.minesoundapi.reflections.PacketUtil;
import me.matamor.minesoundapi.scoreboard.reflection.IScoreboard.Display;
import us.swiftex.custominventories.utils.Reflections;

import java.util.*;
import java.util.Map.Entry;

public class IObjective {

    private final Object[] ENUM_ACTION = Reflections.getMinecraftClass("IScoreboardCriteria$EnumScoreboardHealthDisplay").getEnumConstants();

    private Display display;
    private IScoreboard scoreboard;
    private String name;
    private String displayName;
    private boolean displayed;
    private Map<String, IScore> scores = new HashMap<>();

    protected IObjective(IScoreboard scoreboard, Display display) {
        this.display = display;
        this.scoreboard = scoreboard;
        this.name = display.getName();
        this.displayName = display.getDisplayName();
    }

    public String getName() {
        return this.name;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Display getDisplay() {
        return this.display;
    }

    public void setDisplayName(String value) {
        if (!Objects.equals(this.displayName, value)) {
            this.displayName = value;
            update();
        }
    }

    public Collection<IScore> getScores() {
        return this.scores.values();
    }

    public Set<Entry<String, IScore>> getEntries() {
        return this.scores.entrySet();
    }

    public IScore getScore(String name) {
        return this.scores.computeIfAbsent(name, s -> {
            IScore score = new IScore(this.scoreboard, name, this.name);
            score.create();
            return score;
        });
    }

    public void removeScore(String name) {
        IScore score = this.scores.remove(name);
        if (score != null) {
            score.remove();
        }
    }

    public void clearScores() {
        this.scores.values().forEach(IScore::remove);
        this.scores.clear();
    }

    public void update() {
        if (!this.displayed) {
            return;
        }

        this.handle(2);
    }

    public void show() {
        if (!this.displayed) {
            this.handle(0);
        }

        this.display();
        this.displayed = true;
    }

    public void hide() {
        if (!this.displayed) {
            return;
        }

        this.handle(1);
        this.displayed = false;
    }

    private void handle(int type) {
        Packet packet = PacketType.OUT_SCOREBOARD_OBJECTIVE.newInstance();

        packet.write(PacketType.OUT_SCOREBOARD_OBJECTIVE.name, this.name);
        packet.write(PacketType.OUT_SCOREBOARD_OBJECTIVE.displayName, this.displayName);
        packet.write(PacketType.OUT_SCOREBOARD_OBJECTIVE.action, ENUM_ACTION[0]);
        packet.write(PacketType.OUT_SCOREBOARD_OBJECTIVE.extra, type);

        PacketUtil.sendPacket(this.scoreboard.getPlayer(), packet);
    }

    private void display() {
        Packet packet = PacketType.OUT_SCOREBOARD_DISPLAY_OBJECTIVE.newInstance();

        packet.write(PacketType.OUT_SCOREBOARD_DISPLAY_OBJECTIVE.name, this.name);
        packet.write(PacketType.OUT_SCOREBOARD_DISPLAY_OBJECTIVE.display, this.display.getId());

        PacketUtil.sendPacket(this.scoreboard.getPlayer(), packet);
    }
}