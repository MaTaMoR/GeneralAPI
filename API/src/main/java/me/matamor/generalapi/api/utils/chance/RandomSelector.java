package me.matamor.generalapi.api.utils.chance;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomSelector {

    public static <T extends Chance> T getRandom(Collection<T> values) {
        List<T> valuesCopy = new ArrayList<>(values);
        double random = ThreadLocalRandom.current().nextDouble(100);

        while (!valuesCopy.isEmpty()) {
            T value = valuesCopy.remove(ThreadLocalRandom.current().nextInt(valuesCopy.size()));

            if (value.getChance() >= random) {
                return value;
            }
        }

        return getRandom(values);
    }
}



