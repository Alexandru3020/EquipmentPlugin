package me.Alexandru302.equipmentplugin.EquipmentGUI;

import me.Alexandru302.equipmentplugin.EquipmentPlugin;
import me.Alexandru302.equipmentplugin.Item.DiamondCharm;
import me.Alexandru302.equipmentplugin.Item.EquipmentItem;
import me.Alexandru302.equipmentplugin.Utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static me.Alexandru302.equipmentplugin.Utils.MessageUtil.sendMessage;

public class EquipmentGUI implements Listener {
    public static final Map<UUID, Inventory> playerInventories = new HashMap<>();
    private static final String GUI_TITLE = Config.EQUIPMENT_GUI_NAME;

    BukkitRunnable whileEquippedTask1;
    BukkitRunnable whileEquippedTask2;
    BukkitRunnable whileEquippedTask3;
    BukkitRunnable whileEquippedTask4;



    public static void openEquipmentGUI(Player player) {
        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 1, 0);
        UUID playerId = player.getUniqueId();

        Inventory inventory = playerInventories.computeIfAbsent(playerId, k -> loadEquipment(player));
        player.openInventory(inventory);
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase(GUI_TITLE)) {
            Player player = (Player) event.getPlayer();
            UUID playerId = player.getUniqueId();
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 1, 0);
            Inventory inventory = event.getInventory();
            if (playerInventories.containsValue(inventory)) {
                saveEquipment(playerId, inventory);
            }
        }
    }

    public static Inventory loadEquipment(Player player) {
        UUID playerUUID = player.getUniqueId();
        File file = new File(EquipmentPlugin.getInstance().getDataFolder() + "/equipmentData", playerUUID.toString() + ".yml");
        if (!file.exists()) {
            return Bukkit.createInventory(null, 54, GUI_TITLE);
        }

        YamlConfiguration Data = YamlConfiguration.loadConfiguration(file);
        Inventory inventory = Bukkit.createInventory(null, 54, GUI_TITLE);
        ItemStack slot = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta slotMeta = slot.getItemMeta();
        slotMeta.setCustomModelData(1);
        slotMeta.setDisplayName(ChatColor.RESET + "");
        slot.setItemMeta(slotMeta);

        for (int i = 0; i < 54; i++) {
            //slot.setAmount(i+1);
            inventory.setItem(i, slot);
        }
        ItemStack Slot1 = Data.getItemStack("slot_1");
        ItemStack Slot2 = Data.getItemStack("slot_2");
        ItemStack Slot3 = Data.getItemStack("slot_3");
        ItemStack Slot4 = Data.getItemStack("slot_4");

        inventory.setItem(10, Slot1);
        inventory.setItem(19, Slot2);
        inventory.setItem(28, Slot3);
        inventory.setItem(37, Slot4);

        return inventory;
    }

    public static void saveEquipment(UUID playerId, Inventory inventory) {
        File file = new File(EquipmentPlugin.getInstance().getDataFolder() + "/equipmentData", playerId.toString() + ".yml");
        file.getParentFile().mkdirs();

        YamlConfiguration config = new YamlConfiguration();
        config.set("slot_1", inventory.getItem(10));
        config.set("slot_2", inventory.getItem(19));
        config.set("slot_3", inventory.getItem(28));
        config.set("slot_4", inventory.getItem(37));
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (playerInventories.containsValue(inventory)) {

            ItemStack clickedItem = event.getCurrentItem();
            Inventory GUI = event.getInventory();
            InventoryView view = event.getView();
            Player player = (Player) event.getWhoClicked();

            if (view.getTitle().equals(GUI_TITLE)) {
                if (clickedItem != null && !(clickedItem.getType() == Material.BLACK_STAINED_GLASS_PANE)) {
                    if (EquipmentItem.isEquipmentItem(clickedItem)) {
                        if (GUI.getItem(EquipmentItem.getEquipmentItemSlot(clickedItem)) == null && !(EquipmentItem.isEquipped(clickedItem))) {
                            equipItem(clickedItem, GUI, player);
                            EquipmentItem equipmentItem = EquipmentItem.ItemStackToEquipmentItem(clickedItem);
                            //Man, im sorry but this is the only way I could get it to work.
                            if (EquipmentItem.getEquipmentItemSlot(clickedItem) == EquipmentSlot.SLOT1.getSlot()) {
                                whileEquippedTask1  = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        equipmentItem.whileEquipped(player);
                                    }
                                };
                                whileEquippedTask1.runTaskTimer(EquipmentPlugin.getInstance(), 0L, equipmentItem.whileEquippedDelay());
                            } else if (EquipmentItem.getEquipmentItemSlot(clickedItem) == EquipmentSlot.SLOT2.getSlot()) {
                                whileEquippedTask2 = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        equipmentItem.whileEquipped(player);
                                    }
                                };
                                whileEquippedTask2.runTaskTimer(EquipmentPlugin.getInstance(), 0L, equipmentItem.whileEquippedDelay());
                            } else if (EquipmentItem.getEquipmentItemSlot(clickedItem) == EquipmentSlot.SLOT3.getSlot()) {
                                whileEquippedTask3 = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        equipmentItem.whileEquipped(player);
                                    }
                                };
                                whileEquippedTask3.runTaskTimer(EquipmentPlugin.getInstance(), 0L, equipmentItem.whileEquippedDelay());
                            } else if (EquipmentItem.getEquipmentItemSlot(clickedItem) == EquipmentSlot.SLOT4.getSlot()) {
                                 whileEquippedTask4 = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        equipmentItem.whileEquipped(player);
                                    }
                                };
                                whileEquippedTask4.runTaskTimer(EquipmentPlugin.getInstance(), 0L, equipmentItem.whileEquippedDelay());
                            }


                        } else if (EquipmentItem.isEquipped(clickedItem)) {
                            unequipItem(clickedItem, GUI, player);
                            if (EquipmentItem.getEquipmentItemSlot(clickedItem) == EquipmentSlot.SLOT1.getSlot()) {
                                whileEquippedTask1.cancel();
                            } else if (EquipmentItem.getEquipmentItemSlot(clickedItem) == EquipmentSlot.SLOT2.getSlot()) {
                                whileEquippedTask2.cancel();
                            } else if (EquipmentItem.getEquipmentItemSlot(clickedItem) == EquipmentSlot.SLOT3.getSlot()) {
                                whileEquippedTask3.cancel();
                            } else if (EquipmentItem.getEquipmentItemSlot(clickedItem) == EquipmentSlot.SLOT4.getSlot()) {
                                whileEquippedTask4.cancel();
                            }
                        } else sendMessage(player, "&cThis slot is already occupied!");
                    } else {
                        sendMessage(player, "&cThis is not an Equipment Item!");
                    }
                }
                event.setCancelled(true);
            }
        }
    }
    public static void equipItem(ItemStack item, Inventory GUI, Player player) {
        int itemSlot = EquipmentItem.getEquipmentItemSlot(item);
        EquipmentItem.setEquipEquipmentItemKey(item, true);
        EquipmentItem equipmentItem = EquipmentItem.ItemStackToEquipmentItem(item);
        equipmentItem.onEquip(player);
        GUI.setItem(itemSlot, item);
        item.setAmount(0);
    }
    public static void unequipItem(ItemStack item, Inventory GUI, Player player) {
        int itemSlot = EquipmentItem.getEquipmentItemSlot(item);
        GUI.setItem(itemSlot, new ItemStack(Material.AIR));
        EquipmentItem.setEquipEquipmentItemKey(item, false);
        EquipmentItem equipmentItem = EquipmentItem.ItemStackToEquipmentItem(item);
        player.getInventory().addItem(item);
        equipmentItem.onUnequip(player);
    }
}