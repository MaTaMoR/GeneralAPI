package me.matamor.generalapi.api.container.languages;

public enum Language {

    SPANISH("spanish", "es"),
    ENGLISH("english", "en");

    private final String name;
    private final String key;

    Language(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String replace(String text) {
        return text.replace("{language}", getName());
    }

    public static Language getLanguage(String text) {
        if (text == null) return null;

        for (Language language : values()) {
            if (language.getName().equalsIgnoreCase(text) || language.getKey().equalsIgnoreCase(text)) {
                return language;
            }
        }

        return null;
    }
}
