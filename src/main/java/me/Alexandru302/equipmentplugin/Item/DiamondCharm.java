package me.Alexandru302.equipmentplugin.Item;

import me.Alexandru302.equipmentplugin.EquipmentGUI.EquipmentSlot;
import me.Alexandru302.equipmentplugin.EquipmentPlugin;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiamondCharm extends EquipmentItem {


    public DiamondCharm() {
        super(EquipmentSlot.SLOT1);
    }

    @Override
    public ItemStack setItemStack() {
        // Create the ItemStack for this Charm item
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§bDiamond Charm");
        item.setItemMeta(meta);
        return item;
    }


    @Override
    public void onEquip(Player player) {

    }

    @Override
    public long whileEquippedDelay() {
        return 20L;
    }

    @Override
    public void whileEquipped(Player player) {
    player.sendMessage("im on that good kooosh and alcohooool");
    }

    @Override
    public void onUnequip(Player player) {

    }
}