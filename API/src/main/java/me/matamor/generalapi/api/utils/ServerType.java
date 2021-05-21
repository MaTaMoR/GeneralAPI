package me.matamor.generalapi.api.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ServerType {

    GLOBAL("Global"),
    SKY_BLOCK("SkyBlock"),
    SURVIVAL("Survival"),
    SKY_WARS("SkyWars"),
    EGG_WARS("EggWars"),
    COD("COD"),
    PVP_GAMES("PvPGames"),
    FACTIONS("Factions"),
    UHC("UHC"),
    LOBBY("Lobby"),
    BEDWARDS("BedWars");

    @Getter
    private final String name;

    public boolean isSame(ServerType serverType) {
        return this == serverType || this == GLOBAL;
    }
}
