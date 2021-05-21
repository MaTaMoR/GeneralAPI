package me.matamor.generalapi.api.entries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.matamor.minesoundapi.MineSoundAPI;
import me.matamor.minesoundapi.storage.DataProvider;
import me.matamor.minesoundapi.data.PlayerData;

import java.util.logging.Level;

@AllArgsConstructor
public class RegisteredData {

    @Getter
    private final PlayerData parent;

    @Getter
    private final DataEntry dataEntry;

    @Getter
    private final DataProvider dataProvider;

    public void save() {
        try {
            this.dataProvider.getStorage().save(this.dataEntry);
        } catch (Exception e) {
            MineSoundAPI.getInstance().getLogger().log(Level.SEVERE, String.format("There was an error while saving the data %s from %s:",
                    this.parent.getIdentifier().toString(), this.dataEntry.getClass().getSimpleName()), e);
        }
    }

    public void saveAsync() {
        this.dataProvider.getStorage().saveAsync(this.dataEntry, (result, exception) -> {
            if (exception != null) {
                MineSoundAPI.getInstance().getLogger().log(Level.SEVERE, String.format("There was an error while saving the data %s from %s:",
                        this.parent.getIdentifier().toString(), this.dataEntry.getClass().getSimpleName()), exception);
            }
        });
    }
}
