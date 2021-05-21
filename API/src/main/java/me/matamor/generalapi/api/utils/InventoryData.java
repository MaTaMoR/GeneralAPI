package me.matamor.generalapi.api.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.matamor.custominventories.utils.CustomItem;

@AllArgsConstructor
public class InventoryData {

    @Getter
    private final String title;

    @Getter
    private final CustomItem icon;

}
