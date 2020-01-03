package me.redw0lfstone.addEnchant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class buyCompletions implements TabCompleter {
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length == 1) {

			List<String> completions = new ArrayList<>();
			completions.add("aquaaffinity");
			completions.add("baneofarthropods");
			completions.add("blastprotection");
			completions.add("depthstrider");
			completions.add("efficiency");
			completions.add("featherfalling");
			completions.add("fireaspect");
			completions.add("fireprotection");
			completions.add("flame");
			completions.add("fortune");
			completions.add("infinity");
			completions.add("knockback");
			completions.add("looting");
			completions.add("luckofthesea");
			completions.add("lure");
			completions.add("power");
			completions.add("projectileprotection");
			completions.add("protection");
			completions.add("punch");
			completions.add("respiration");
			completions.add("sharpness");
			completions.add("silktouch");
			completions.add("smite");
			completions.add("thorns");
			completions.add("unbreaking");

			return completions.stream().filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
					.collect(Collectors.toList());

		}
		return null;
	}
}
