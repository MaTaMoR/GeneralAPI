package me.matamor.generalapi.api.utils.requirements;

import me.matamor.custominventories.permissions.IPermission;
import me.matamor.custominventories.requirements.PermissionRequirement;

public class SimplePermissionRequirement extends PermissionRequirement {

    public SimplePermissionRequirement(IPermission permission) {
        super("&cNo tienes permisos!", permission);
    }
}
