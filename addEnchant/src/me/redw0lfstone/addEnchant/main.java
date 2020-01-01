package me.redw0lfstone.addEnchant;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
	public void onEnable() {
		this.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Add enchant has been enabled!");
		this.getCommand("addenchant").setTabCompleter(new TabComplete());
	}

	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {

		if (sender instanceof Player) {

			Player player = (Player) sender;

			if (cmd.getName().toLowerCase().startsWith("addenchant") && args.length == 2) {
				if (player.isOp() || player.hasPermission("addenchant.enchant")) {
					String enchant = args[0];
					if (StringUtils.isNumeric(args[1])) {

						int lvl = Integer.valueOf(args[1]);

						try {
							player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
									.enchantment(Enchantment.getByName(enchant.toUpperCase()), lvl, true).build());
							player.sendMessage(ChatColor.GREEN + "Enchanted " + player.getItemInHand().getType()
									+ " With enchantment " + enchant + " " + lvl);
							this.getServer().getConsoleSender().sendMessage(ChatColor.RED + "User: " + player.getName()
									+ " With UUID: " + player.getUniqueId() + ChatColor.GREEN + " Has Enchanted "
									+ player.getItemInHand().getType() + " With enchantment " + enchant + " " + lvl);
							player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
						} catch (Exception e) {
							if (enchant.equalsIgnoreCase("power")) {
								player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
										.enchantment(Enchantment.ARROW_DAMAGE, lvl, true).build());
								player.sendMessage(ChatColor.GREEN + "Enchanted " + player.getItemInHand().getType()
										+ " With enchantment " + enchant + " " + lvl);
								this.getServer().getConsoleSender()
										.sendMessage(ChatColor.RED + "User: " + player.getName() + " With UUID: "
												+ player.getUniqueId() + ChatColor.GREEN + " Has Enchanted "
												+ player.getItemInHand().getType() + " With enchantment " + enchant
												+ " " + lvl);
								player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

							}
							if (enchant.equalsIgnoreCase("flame")) {
								player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
										.enchantment(Enchantment.ARROW_FIRE, lvl, true).build());
								player.sendMessage(ChatColor.GREEN + "Enchanted " + player.getItemInHand().getType()
										+ " With enchantment " + enchant + " " + lvl);
								this.getServer().getConsoleSender()
										.sendMessage(ChatColor.RED + "User: " + player.getName() + " With UUID: "
												+ player.getUniqueId() + ChatColor.GREEN + " Has Enchanted "
												+ player.getItemInHand().getType() + " With enchantment " + enchant
												+ " " + lvl);
								player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

							}
							if (enchant.equalsIgnoreCase("infinity")) {
								player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
										.enchantment(Enchantment.ARROW_INFINITE, lvl, true).build());
								player.sendMessage(ChatColor.GREEN + "Enchanted " + player.getItemInHand().getType()
										+ " With enchantment " + enchant + " " + lvl);
								this.getServer().getConsoleSender()
										.sendMessage(ChatColor.RED + "User: " + player.getName() + " With UUID: "
												+ player.getUniqueId() + ChatColor.GREEN + " Has Enchanted "
												+ player.getItemInHand().getType() + " With enchantment " + enchant
												+ " " + lvl);
								player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

							}
							if (enchant.equalsIgnoreCase("punch")) {
								player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
										.enchantment(Enchantment.ARROW_KNOCKBACK, lvl, true).build());
								player.sendMessage(ChatColor.GREEN + "Enchanted " + player.getItemInHand().getType()
										+ " With enchantment " + enchant + " " + lvl);
								this.getServer().getConsoleSender()
										.sendMessage(ChatColor.RED + "User: " + player.getName() + " With UUID: "
												+ player.getUniqueId() + ChatColor.GREEN + " Has Enchanted "
												+ player.getItemInHand().getType() + " With enchantment " + enchant
												+ " " + lvl);
								player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

							}
							if (enchant.equalsIgnoreCase("sharpness")) {
								player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
										.enchantment(Enchantment.DAMAGE_ALL, lvl, true).build());
								player.sendMessage(ChatColor.GREEN + "Enchanted " + player.getItemInHand().getType()
										+ " With enchantment " + enchant + " " + lvl);
								this.getServer().getConsoleSender()
										.sendMessage(ChatColor.RED + "User: " + player.getName() + " With UUID: "
												+ player.getUniqueId() + ChatColor.GREEN + " Has Enchanted "
												+ player.getItemInHand().getType() + " With enchantment " + enchant
												+ " " + lvl);
								player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

							}
							if (enchant.equalsIgnoreCase("baneofarthropods")) {
								player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
										.enchantment(Enchantment.DAMAGE_ARTHROPODS, lvl, true).build());
								player.sendMessage(ChatColor.GREEN + "Enchanted " + player.getItemInHand().getType()
										+ " With enchantment " + enchant + " " + lvl);
								this.getServer().getConsoleSender()
										.sendMessage(ChatColor.RED + "User: " + player.getName() + " With UUID: "
												+ player.getUniqueId() + ChatColor.GREEN + " Has Enchanted "
												+ player.getItemInHand().getType() + " With enchantment " + enchant
												+ " " + lvl);
								player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

							}
							if (enchant.equalsIgnoreCase("smite")) {
								player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
										.enchantment(Enchantment.DAMAGE_UNDEAD, lvl, true).build());
								player.sendMessage(ChatColor.GREEN + "Enchanted " + player.getItemInHand().getType()
										+ " With enchantment " + enchant + " " + lvl);
								this.getServer().getConsoleSender()
										.sendMessage(ChatColor.RED + "User: " + player.getName() + " With UUID: "
												+ player.getUniqueId() + ChatColor.GREEN + " Has Enchanted "
												+ player.getItemInHand().getType() + " With enchantment " + enchant
												+ " " + lvl);
								player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

							}
							if (enchant.equalsIgnoreCase("efficiency")) {
								player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
										.enchantment(Enchantment.DIG_SPEED, lvl, true).build());
								player.sendMessage(ChatColor.GREEN + "Enchanted " + player.getItemInHand().getType()
										+ " With enchantment " + enchant + " " + lvl);
								this.getServer().getConsoleSender()
										.sendMessage(ChatColor.RED + "User: " + player.getName() + " With UUID: "
												+ player.getUniqueId() + ChatColor.GREEN + " Has Enchanted "
												+ player.getItemInHand().getType() + " With enchantment " + enchant
												+ " " + lvl);
								player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

							}
							if (enchant.equalsIgnoreCase("unbreaking")) {
								player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
										.enchantment(Enchantment.DURABILITY, lvl, true).build());
								player.sendMessage(ChatColor.GREEN + "Enchanted " + player.getItemInHand().getType()
										+ " With enchantment " + enchant + " " + lvl);
								this.getServer().getConsoleSender()
										.sendMessage(ChatColor.RED + "User: " + player.getName() + " With UUID: "
												+ player.getUniqueId() + ChatColor.GREEN + " Has Enchanted "
												+ player.getItemInHand().getType() + " With enchantment " + enchant
												+ " " + lvl);
								player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

							}
							if (enchant.equalsIgnoreCase("fortune")) {
								player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
										.enchantment(Enchantment.LOOT_BONUS_BLOCKS, lvl, true).build());
								player.sendMessage(ChatColor.GREEN + "Enchanted " + player.getItemInHand().getType()
										+ " With enchantment " + enchant + " " + lvl);
								this.getServer().getConsoleSender()
										.sendMessage(ChatColor.RED + "User: " + player.getName() + " With UUID: "
												+ player.getUniqueId() + ChatColor.GREEN + " Has Enchanted "
												+ player.getItemInHand().getType() + " With enchantment " + enchant
												+ " " + lvl);
								player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

							}
							if (enchant.equalsIgnoreCase("looting")) {
								player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
										.enchantment(Enchantment.LOOT_BONUS_MOBS, lvl, true).build());
								player.sendMessage(ChatColor.GREEN + "Enchanted " + player.getItemInHand().getType()
										+ " With enchantment " + enchant + " " + lvl);
								this.getServer().getConsoleSender()
										.sendMessage(ChatColor.RED + "User: " + player.getName() + " With UUID: "
												+ player.getUniqueId() + ChatColor.GREEN + " Has Enchanted "
												+ player.getItemInHand().getType() + " With enchantment " + enchant
												+ " " + lvl);
								player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

							}
							if (enchant.equalsIgnoreCase("luckofthesea")) {
								player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
										.enchantment(Enchantment.LUCK, lvl, true).build());
								player.sendMessage(ChatColor.GREEN + "Enchanted " + player.getItemInHand().getType()
										+ " With enchantment " + enchant + " " + lvl);
								this.getServer().getConsoleSender()
										.sendMessage(ChatColor.RED + "User: " + player.getName() + " With UUID: "
												+ player.getUniqueId() + ChatColor.GREEN + " Has Enchanted "
												+ player.getItemInHand().getType() + " With enchantment " + enchant
												+ " " + lvl);
								player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

							}
							if (enchant.equalsIgnoreCase("respirationt")) {
								player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
										.enchantment(Enchantment.OXYGEN, lvl, true).build());
								player.sendMessage(ChatColor.GREEN + "Enchanted " + player.getItemInHand().getType()
										+ " With enchantment " + enchant + " " + lvl);
								this.getServer().getConsoleSender()
										.sendMessage(ChatColor.RED + "User: " + player.getName() + " With UUID: "
												+ player.getUniqueId() + ChatColor.GREEN + " Has Enchanted "
												+ player.getItemInHand().getType() + " With enchantment " + enchant
												+ " " + lvl);
								player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

							}
							if (enchant.equalsIgnoreCase("protection")) {
								player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
										.enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, lvl, true).build());
								player.sendMessage(ChatColor.GREEN + "Enchanted " + player.getItemInHand().getType()
										+ " With enchantment " + enchant + " " + lvl);
								this.getServer().getConsoleSender()
										.sendMessage(ChatColor.RED + "User: " + player.getName() + " With UUID: "
												+ player.getUniqueId() + ChatColor.GREEN + " Has Enchanted "
												+ player.getItemInHand().getType() + " With enchantment " + enchant
												+ " " + lvl);
								player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

							}
							if (enchant.equalsIgnoreCase("blastProtection")) {
								player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
										.enchantment(Enchantment.PROTECTION_EXPLOSIONS, lvl, true).build());
								player.sendMessage(ChatColor.GREEN + "Enchanted " + player.getItemInHand().getType()
										+ " With enchantment " + enchant + " " + lvl);
								this.getServer().getConsoleSender()
										.sendMessage(ChatColor.RED + "User: " + player.getName() + " With UUID: "
												+ player.getUniqueId() + ChatColor.GREEN + " Has Enchanted "
												+ player.getItemInHand().getType() + " With enchantment " + enchant
												+ " " + lvl);
								player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

							}
							if (enchant.equalsIgnoreCase("featherFalling")) {
								player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
										.enchantment(Enchantment.PROTECTION_FALL, lvl, true).build());
								player.sendMessage(ChatColor.GREEN + "Enchanted " + player.getItemInHand().getType()
										+ " With enchantment " + enchant + " " + lvl);
								this.getServer().getConsoleSender()
										.sendMessage(ChatColor.RED + "User: " + player.getName() + " With UUID: "
												+ player.getUniqueId() + ChatColor.GREEN + " Has Enchanted "
												+ player.getItemInHand().getType() + " With enchantment " + enchant
												+ " " + lvl);
								player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

							}
							if (enchant.equalsIgnoreCase("fireprotection")) {
								player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
										.enchantment(Enchantment.PROTECTION_FIRE, lvl, true).build());
								player.sendMessage(ChatColor.GREEN + "Enchanted " + player.getItemInHand().getType()
										+ " With enchantment " + enchant + " " + lvl);
								this.getServer().getConsoleSender()
										.sendMessage(ChatColor.RED + "User: " + player.getName() + " With UUID: "
												+ player.getUniqueId() + ChatColor.GREEN + " Has Enchanted "
												+ player.getItemInHand().getType() + " With enchantment " + enchant
												+ " " + lvl);
								player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

							}
							if (enchant.equalsIgnoreCase("projectileProtection")) {
								player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
										.enchantment(Enchantment.PROTECTION_PROJECTILE, lvl, true).build());
								player.sendMessage(ChatColor.GREEN + "Enchanted " + player.getItemInHand().getType()
										+ " With enchantment " + enchant + " " + lvl);
								this.getServer().getConsoleSender()
										.sendMessage(ChatColor.RED + "User: " + player.getName() + " With UUID: "
												+ player.getUniqueId() + ChatColor.GREEN + " Has Enchanted "
												+ player.getItemInHand().getType() + " With enchantment " + enchant
												+ " " + lvl);
								player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

							}
							if (enchant.equalsIgnoreCase("aquaAffinity")) {
								player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
										.enchantment(Enchantment.WATER_WORKER, lvl, true).build());
								player.sendMessage(ChatColor.GREEN + "Enchanted " + player.getItemInHand().getType()
										+ " With enchantment " + enchant + " " + lvl);
								this.getServer().getConsoleSender()
										.sendMessage(ChatColor.RED + "User: " + player.getName() + " With UUID: "
												+ player.getUniqueId() + ChatColor.GREEN + " Has Enchanted "
												+ player.getItemInHand().getType() + " With enchantment " + enchant
												+ " " + lvl);
								player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

							}

						}

					} else {
						player.sendMessage(ChatColor.RED
						+ "Invalid usage of the command... Please try doing \"/addEnchant <Enchant name> <level>\"");
					}

				}

//					player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
//							.enchantment(Enchantment.getByName(enchant), lvl, true).build());
//					player.sendMessage(ChatColor.GREEN + "Enchanted " + player.getItemInHand().getType() + " With enchantment " + enchant + " " + lvl);
//					this.getServer().getConsoleSender().sendMessage(ChatColor.RED + "User: " + player.getName() + " With UUID: " + player.getUniqueId() + ChatColor.GREEN + " Has Enchanted " + player.getItemInHand().getType() + " With enchantment " + enchant + " " + lvl);
//					player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

			} else {
				player.sendMessage(ChatColor.RED
						+ "Invalid usage of the command... Please try doing \"/addEnchant <Enchant name> <level>\"");
			}
			
			

		}

		return true;
	}

}
