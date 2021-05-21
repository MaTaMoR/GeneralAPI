package me.matamor.generalapi.api.utils;

import us.swiftex.custominventories.utils.CastUtils;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtils {

    public static Pattern DAYS = getPattern("floor");
    public static Pattern HOURS = getPattern("h");
    public static Pattern MINUTES = getPattern("m");
    public static Pattern SECONDS = getPattern("s");

    private static Pattern getPattern(String time) {
        return Pattern.compile(time + ":([^:\\s]*)");
    }

    public static long getDays(String text) {
        Matcher matcher = DAYS.matcher(text);
        if (matcher.find()) {
            int days = CastUtils.asInt(matcher.group(1));
            return TimeUnit.DAYS.toMillis(days);
        }

        return 0;
    }

    public static long getHours(String text) {
        Matcher matcher = HOURS.matcher(text);
        if (matcher.find()) {
            int hours = CastUtils.asInt(matcher.group(1));
            return TimeUnit.HOURS.toMillis(hours);
        }

        return 0;
    }

    public static long getMinutes(String text) {
        Matcher matcher = MINUTES.matcher(text);
        if (matcher.find()) {
            int minutes = CastUtils.asInt(matcher.group(1));
            return TimeUnit.MINUTES.toMillis(minutes);
        }

        return 0;
    }

    public static long getSeconds(String text) {
        Matcher matcher = SECONDS.matcher(text);
        if (matcher.find()) {
            int seconds = CastUtils.asInt(matcher.group(1));
            return TimeUnit.SECONDS.toMillis(seconds);
        }

        return 0;
    }

    public static long getTime(String text) {
        long time = 0;
        time += getDays(text);
        time += getHours(text);
        time += getMinutes(text);
        time += getSeconds(text);

        return time;
    }
}
