package me.matamor.generalapi.api.storage;

import me.matamor.minesoundapi.data.PlayerData;

public interface InstanceProvider<T> {

    T newInstance(PlayerData playerData);

}
