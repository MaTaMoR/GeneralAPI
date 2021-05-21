package me.matamor.generalapi.api.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class SimpleBoard {

    private final Scoreboard scoreboard;
    private final Objective objective;

    public SimpleBoard() {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = this.scoreboard.registerNewObjective("Board", "dummy");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public SimpleBoard(Player player) {
        if (hasScoreboard(player)) {
            this.scoreboard = player.getScoreboard();
            this.objective = this.scoreboard.getObjective(DisplaySlot.SIDEBAR);
        } else {
            this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            this.objective = this.scoreboard.registerNewObjective("Board", "dummy");
            this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
    }

    public void setEntry(int row, String text) {
        if (row < 0) {
            row = 0;
        } else if (row > 15) {
            row = 15;
        }

        String toDelete = null;

        //Antes de añadir algo nuevo al scoreboard hay que comprobar si ya existe la linea (int row) que queremos usar
        for (String entry : this.scoreboard.getEntries()) {
            //Comprobamos todas las Entries que hay en el scoreboard, y luego comprobamos si su score (row) es la misma que la nuestra
            if (this.objective.getScore(entry).getScore() == row) {
                //Si el texto es igual significa que no hace falta actualizar nada, ya es igual
                if (entry.equals(text)) {
                    return;
                }

                //El texto no es igual asi que hay que actualizar la linea (el break es para no seguir comprobando)
                toDelete = entry;
                break;
            }
        }

        //Comprobar si encontramos ya un texto en la misma posicion (row) y lo borramos
        if (toDelete != null) {
            this.scoreboard.resetScores(toDelete);
        }

        //Añadimos nuestro texto
        this.objective.getScore(text).setScore(row);
    }

    public boolean hasScoreboard(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        if (scoreboard == null) return false;

        Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
        return objective != null && objective.getName().equals("Board") && objective.getCriteria().equals("dummy");
    }
}
