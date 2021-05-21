package me.matamor.generalapi.api.utils.variables;

import me.matamor.minesoundapi.utils.Name;

public interface Variable<T> extends Name {

    String getReplacement(T object);

}
