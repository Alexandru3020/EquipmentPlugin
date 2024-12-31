package me.Alexandru302.equipmentplugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class EquipmentCommandTabCompleter implements TabCompleter {
    List<String> arguments = new ArrayList<String>();
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (this.arguments.isEmpty()) {
            this.arguments.add("reload");
            this.arguments.add("reset_config");
        }
        return null;
    }
}
