package me.matamor.generalapi.api.scoreboard.reflection;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class IScoreboard {

    private final Map<String, ITeam> teams = new HashMap<>();

    private IObjective[] objectives = new IObjective[3];
    private final WeakReference<Player> player;

    protected IScoreboard(Player player) {
        this.player = new WeakReference<>(player);

        for(int i = 0; i < 3; i++) {
            Display display = Display.fromInt(i);
            objectives[i] = new IObjective(this, display);
        }
    }

    public Player getPlayer() {
        Player player = this.player.get();
        if (player == null) {
            throw new RuntimeException("The Player referenced by this Scoreboard is no longer online/available");
        }

        return player;
    }

    public IObjective getObjective(Display display) {
        return this.objectives[display.getId()];
    }

    public ITeam registerNewTeam(String name) {
        return this.teams.computeIfAbsent(name, ITeam::new);
    }

    public ITeam[] getTeams() {
        return this.teams.values().toArray(new ITeam[this.teams.values().size()]);
    }

    public ITeam getTeam(String name) {
        return this.teams.get(name);
    }

    public enum Display {

        LIST(0, "list", "List"),
        SIDEBAR(1, "sidebar", "SideBar"),
        BELOW_NAME(2, "belowname", "BelowName");

        @Getter
        private int id;

        @Getter
        private String name;

        @Getter
        private String displayName;

        Display(int id, String name, String displayName) {
            this.id = id;
            this.name = name;
            this.displayName = displayName;
        }

        public static Display fromInt(int from) {
            for (Display display : values()) {
                if(display.id == from) {
                    return display;
                }
            }

            return null;
        }
    }
}