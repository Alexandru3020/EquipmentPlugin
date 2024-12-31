package me.Alexandru302.equipmentplugin.Item;

import me.Alexandru302.equipmentplugin.EquipmentGUI.EquipmentSlot;
import me.Alexandru302.equipmentplugin.EquipmentPlugin;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class EquipmentItem {
    private final EquipmentSlot slot;
    private final ItemStack item;
    private static final NamespacedKey isEquipmentKey = new NamespacedKey(EquipmentPlugin.getInstance(), "isEquipmentItem");
    private static final NamespacedKey EquipmentSlot = new NamespacedKey(EquipmentPlugin.getInstance(), "EquipmentSlot");
    private static final NamespacedKey EquipmentUUID = new NamespacedKey(EquipmentPlugin.getInstance(), "EquipmentUUID");
    private static final NamespacedKey EquipmentSlotName = new NamespacedKey(EquipmentPlugin.getInstance(), "EquipmentSlotName");
    private static final NamespacedKey isEquippedKey = new NamespacedKey(EquipmentPlugin.getInstance(), "isEquipped");
    private static final NamespacedKey EquipmentClassKey = new NamespacedKey(EquipmentPlugin.getInstance(), "EquipmentClassKey");

    public EquipmentItem(EquipmentSlot slot) {
        this.slot = slot;
        this.item = setItemStack();
        setEquipmentItemMeta(this.item);
    }
    public void setEquipmentItemMeta(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.getPersistentDataContainer().set(isEquipmentKey, PersistentDataType.BOOLEAN, true);
            meta.getPersistentDataContainer().set(EquipmentSlot, PersistentDataType.INTEGER, slot.getSlot());
            meta.getPersistentDataContainer().set(EquipmentSlotName, PersistentDataType.STRING, slot.getSlotName());
            meta.getPersistentDataContainer().set(isEquippedKey, PersistentDataType.BOOLEAN, false);
            meta.getPersistentDataContainer().set(EquipmentUUID, PersistentDataType.STRING, UUID.randomUUID().toString());
            meta.getPersistentDataContainer().set(EquipmentClassKey, PersistentDataType.STRING, this.getClass().getName());

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&eSlot: &6" + slot.getSlotName()));
            meta.setLore(lore);
        }
        item.setItemMeta(meta);
    }
    //This method was a collab with Chat-GPT and StackOverflow.
    public static EquipmentItem ItemStackToEquipmentItem(ItemStack itemStack) {
        if (itemStack == null || !isEquipmentItem(itemStack)) {
            return null;
        }
        try {
            ItemMeta meta = itemStack.getItemMeta();
            String className = meta.getPersistentDataContainer().get(EquipmentClassKey, PersistentDataType.STRING);

            if (className == null) {
                return null;
            }
            Class<?> clazz = Class.forName(className);
            if (!EquipmentItem.class.isAssignableFrom(clazz)) {
                return null;
            }
            return (EquipmentItem) clazz.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static boolean isEquipmentItem(ItemStack itemStack) {
        return Boolean.TRUE.equals(itemStack.getItemMeta().getPersistentDataContainer().get(isEquipmentKey, PersistentDataType.BOOLEAN));
    }
    public static int getEquipmentItemSlot(ItemStack itemStack) {
        return itemStack.getItemMeta().getPersistentDataContainer().get(EquipmentSlot, PersistentDataType.INTEGER);
    }
    public static void setEquipEquipmentItemKey(ItemStack item, boolean value) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(isEquippedKey, PersistentDataType.BOOLEAN, value);
        item.setItemMeta(meta);
    }
    public static boolean isEquipped(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer().get(isEquippedKey, PersistentDataType.BOOLEAN);
    }
    public EquipmentSlot getEquipmentSlot() {
        return slot;
    }
    public ItemStack getItemStack() {
        return item;
    }
    /**
     * Inside of this method you will create the ItemStack.
     *  Example of Usage:
     *  <p>
     *  [email sign thingy]Override
     *  <p>
     *  public ItemStack getItemStack() {
     *  <p>
     *      ItemStack item = new ItemStack(Material.DIAMOND);
     * <p>
     *      ItemMeta meta = item.getItemMeta();
     *  <p>
     *      meta.setDisplayName("§bDiamond Charm");
     *  <p>
     *      item.setItemMeta(meta);
     *  <p>
     *      return item;
     *  <p>
     * }
     * @return the custom Item.
     */
    public abstract ItemStack setItemStack();

    /**
     * This method is executed only when you equip the item.
     */
    public abstract void onEquip(Player player);
    /**
     * This method is executed while the item is equipped <p>
     * Basically the code in here runs every tick.
     */
    public abstract void whileEquipped(Player player);
    /**
     * RETURN THE VALUE OF HOW OFTEN YOU WANT THE CODE TO RUN.
     * DON'T FORGET TO ADD THE 'L' AFTER THE VALUE BECAUSE IT'S A LONG VALUE
     */
    public abstract long whileEquippedDelay();
    /**
     * This method is executed only when you unequip the item.
     */
    public abstract void onUnequip(Player player);

}
