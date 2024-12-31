package me.Alexandru302.equipmentplugin.Utils;

import me.Alexandru302.equipmentplugin.EquipmentPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageUtil {


    /**
     * Used to send a message with the Plugin's Prefix.
     * @param player player to send the message to
     */
    public static void sendMessage(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                Config.prefix + " " +message));
    }
    /**
     * Used to send a message with the Plugin's Prefix.
     * @param sender CommandSender to send the message to
     */
    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                Config.prefix + " " +message));
    }
    public static void noPermissionMessage(Player player) {
        //TODO add this message to config.
        sendMessage(player, "&cYou don't have permission to use this command!");
    }
    public static void noPermissionMessage(CommandSender sender) {
        //TODO add this message to config.
        sendMessage(sender, "&cYou don't have permission to use this command!");
    }
}
