package me.redw0lfstone.addEnchant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabComplete implements TabCompleter {
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			List<String> completions = new ArrayList<>();
			completions.add("aquaaffinity");
			completions.add("arrow_damage");
			completions.add("arrow_fire");
			completions.add("arrow_infinite");
			completions.add("arrow_knockback");
			completions.add("baneofarthropods");
			completions.add("blastprotection");
			completions.add("damage_all");
			completions.add("damage_arthropods");
			completions.add("damage_undead");
			completions.add("depth_strider");
			completions.add("dig_speed");
			completions.add("durability");
			completions.add("efficiency");
			completions.add("featherfalling");
			completions.add("fire_aspect");
			completions.add("fireprotection");
			completions.add("flame");
			completions.add("fortune");
			completions.add("infinity");
			completions.add("knockback");
			completions.add("loot_bonus_blocks");
			completions.add("loot_bonus_mobs");
			completions.add("looting");
			completions.add("luck");
			completions.add("luckofthesea");
			completions.add("lure");
			completions.add("oxygen");
			completions.add("power");
			completions.add("projectileprotection");
			completions.add("protection");
			completions.add("protection_environmental");
			completions.add("protection_explosions");
			completions.add("protection_fall");
			completions.add("protection_fire");
			completions.add("protection_projectile");
			completions.add("punch");
			completions.add("respirationt");
			completions.add("sharpness");
			completions.add("silk_touch");
			completions.add("smite");
			completions.add("thorns");
			completions.add("unbreaking");
			completions.add("water_worker");

			
			return completions.stream().filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase())).collect(Collectors.toList());
		}

		return null;
	}
}
