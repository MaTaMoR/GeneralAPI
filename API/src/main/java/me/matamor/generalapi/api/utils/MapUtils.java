package me.matamor.generalapi.api.utils;

import java.util.*;

public class MapUtils {

    public static <K extends Comparable<? super K>, V> Map<K, V> sortAscendant(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o2.getKey()).compareTo(o1.getKey());
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put( entry.getKey(), entry.getValue() );
        }

        return result;
    }

    public static List<Integer> valuesUnder(Map<Integer, ?> map, int number) {
        List<Integer> values = new ArrayList<>();

        for(Integer integer : map.keySet()) {
            if(number > integer) {
                values.add(integer);
            }
        }

        return Collections.unmodifiableList(values);
    }
}
