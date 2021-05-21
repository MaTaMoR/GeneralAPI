package me.matamor.generalapi.api.scoreboard.board.lines;

import me.matamor.minesoundapi.utils.Utils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AnimationBoardLine extends IntervalBoardLine {

    private final List<String> text;
    private final boolean fixed;

    private int maxLength;
    private int position;

    public AnimationBoardLine(List<String> text, int interval) {
        this(text, interval, false);
    }

    public AnimationBoardLine(List<String> text, int interval, boolean fixed) {
        super(interval);

        this.text = text;
        this.fixed = fixed;

        for(String line : text) {
            if(Utils.removeColor(line).length() > maxLength) {
                maxLength = line.length();
            }
        }
    }

    @Override
    public String getText() {
        String line;

        if(should()) {
            line = next();
        } else {
            line = text.get(position);
        }

        if(fixed) line = center(line);

        return Utils.color(line);
    }

    public boolean isFixed() {
        return fixed;
    }

    private String next() {
        position++;

        if(position >= text.size()) {
            position = 0;
        }

        lastUpdate = System.currentTimeMillis();
        return text.get(position);
    }

    private String center(String text) {
        if(fixed) {
            return Utils.repeat(' ', (maxLength / 2) - Utils.removeColor(text).length() / 2) + text;
        } else {
            return text;
        }
    }

    public Collection<String> getLines() {
        return Collections.unmodifiableList(text);
    }
}
