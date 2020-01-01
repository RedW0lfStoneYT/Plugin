package me.addsilk;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

public class main extends JavaPlugin {
	public static Economy economy;

	public void onEnable() {
		loadConfig();

		if (!setupEconomy()) {
			this.getServer().getPluginManager().disablePlugin(this);
		}
		System.out.println("AddSilk reloaded");

	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}

	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		if (!(new File(getDataFolder() + ".config.yml").exists())) {
			saveDefaultConfig();
		}
//		saveConfig();
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		FileConfiguration config = this.getConfig();
		if (sender instanceof Player) {
			Player p = (Player) sender;
			int price = this.getConfig().getInt("price");
			String bal = String.valueOf(economy.getBalance(p));
			String success = this.getConfig().getString("messages.success");
			success = success.replace("%name%", p.getName());
			success = success.replace("%bal%", bal);
			success = success.replace("%price%", String.valueOf(price));
			success = success.replace("%item_name%", p.getItemInHand().getType().toString());
			String noFunds = this.getConfig().getString("messages.no-funds");
			noFunds = noFunds.replace("%name%", p.getName());
			noFunds = noFunds.replace("%bal%", bal);
			noFunds = noFunds.replace("%price%", String.valueOf(price));
			noFunds = noFunds.replace("%item_name%", p.getItemInHand().getType().toString());
			String nullItem = this.getConfig().getString("messages.wrong-item");
			nullItem = nullItem.replace("%name%", p.getName());
			nullItem = nullItem.replace("%bal%", bal);
			nullItem = nullItem.replace("%price%", String.valueOf(price));
			nullItem = nullItem.replace("%item_name%", p.getItemInHand().getType().toString());
			String exists = this.getConfig().getString("messages.enchanted");
			exists = exists.replace("%name%", p.getName());
			exists = exists.replace("%bal%", bal);
			exists = exists.replace("%price%", String.valueOf(price));
			exists = exists.replace("%item_name%", p.getItemInHand().getType().toString());
			
			if (cmd.getName().equalsIgnoreCase("addSilk") && args.length == 0) {
				if (p.hasPermission(config.getString("permissions.addsilk")) || p.isOp()) {
//				ConfigurationSection msg = this.getConfig().getConfigurationSection("dungeon-GUI.items");
//				for (String messages : msg.getKeys(false)) {
//					
//				}
				
//				p.sendMessage(string);
					if (p.getItemInHand() != null) {
						if (p.getItemInHand().getType() == Material.DIAMOND_PICKAXE) {

							if (!((int) economy.getBalance(p) >= price)) {
								if (this.getServer().getVersion().contains("1.12")) {
//								p.sendMessage("It is 1.12.2");
								} else if (this.getServer().getVersion().contains("1.8")) {
									p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
								}

								p.sendMessage(ChatColor.translateAlternateColorCodes('&', noFunds));
							} else if (p.getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
								if (this.getServer().getVersion().contains("1.12")) {
//								p.sendMessage("It is 1.12.2");
								} else if (this.getServer().getVersion().contains("1.8")) {
									p.playSound(p.getLocation(),
											Sound.valueOf(this.getConfig().getString("errorSound").toUpperCase()), 1,
											1);
								}
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', exists));

							} else {

//							String newVersion = this.getServer().getVersion().length() -

								String[] versions = this.getServer().getVersion().split(" ");
								String versionNumber = versions[2].substring(0, versions[2].length() - 1);
								versionNumber = versionNumber.substring(2);
								Double version = Double.valueOf(versionNumber);

								economy.withdrawPlayer(p.getName(), price);
								if (version >= 9) {
//								p.sendMessage("It is " + versionNumber + " >= 9");

								} else if (version > 8 && version < 9) {
//								if (this.getServer().getVersion().contains("1.8")) {
									p.playSound(p.getLocation(),
											Sound.valueOf(this.getConfig().getString("sound").toUpperCase()), 1, 1);
//								p.sendMessage("It is " + versionNumber + " < 8");
								}
//							p.sendMessage("It is " + versionNumber + " unknown");

								p.getItemInHand().addEnchantment(Enchantment.SILK_TOUCH, 1);
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', success));
							}

						} else {
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', nullItem));
						}
					} else {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', nullItem));
					}
				}
			}
			
			

		}
		if (cmd.getName().equalsIgnoreCase("addSilk") && args.length > 0) {
			if (args[0].equalsIgnoreCase("Reload")) {
				String noPerms = this.getConfig().getString("messages.no-perms");

				if (sender instanceof Player
						&& !sender.hasPermission(this.getConfig().getString("permissions.reload"))) {
					if (sender.isOp()) {
						return true;
					} else {
						int price = this.getConfig().getInt("price");
						String bal = String.valueOf(economy.getBalance((Player) sender));
						noPerms = noPerms.replace("%name%", sender.getName());
						noPerms = noPerms.replace("%bal%", bal);
						noPerms = noPerms.replace("%price%", String.valueOf(price));
						noPerms = noPerms.replace("%item_name%",
								((Player) sender).getItemInHand().getType().toString());
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noPerms));
					}
				} else if (sender.hasPermission(this.getConfig().getString("permissions.reload")) || sender.isOp()
						|| !(sender instanceof Player)) {
					try {
						this.reloadConfig();

//	                   

						String reload = this.getConfig().getString("messages.reload");
						if (sender instanceof Player) {

							reload = reload.replace("%name%", sender.getName());

							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', reload));
						}
						this.getServer().getConsoleSender()
								.sendMessage(ChatColor.translateAlternateColorCodes('&', reload));
					} catch (Exception e) {
						String nullReload = this.getConfig().getString("messages.noReload");
						if (sender instanceof Player) {

							nullReload = nullReload.replace("%name%", sender.getName());
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', nullReload));
						}
						this.getServer().getConsoleSender()
								.sendMessage(ChatColor.translateAlternateColorCodes('&', nullReload));
						e.printStackTrace();

					}
				}

			}
		} else {
			return true;
		}
		return true;
	}

}
