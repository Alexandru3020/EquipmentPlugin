package me.Alexandru302.equipmentplugin.EquipmentGUI;

import me.Alexandru302.equipmentplugin.EquipmentPlugin;

public enum EquipmentSlot {

    SLOT1(10),
    SLOT2(19),
    SLOT3(28),
    SLOT4(37);

    private final int slot;

    EquipmentSlot(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }
    public String getSlotName() {
        switch (slot) {
            case 10 -> {
                return "Charm";
            }
            case 19 -> {
                return "Artifact";
            }
            case 28 -> {
                return "Ring";
            }
            case 37 -> {
                return "Necklace";
            }
        }
        return null;
    }
}