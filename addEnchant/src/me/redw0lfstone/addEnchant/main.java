package me.redw0lfstone.addEnchant;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class main extends JavaPlugin {
	public static Economy economy;
	public FileConfiguration config = this.getConfig();

	public void onEnable() {
		loadConfig();
		if (!setupEconomy()) {
			this.getServer().getPluginManager().disablePlugin(this);
		}
		this.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Add enchant has been enabled!");
		this.getCommand("addenchant").setTabCompleter(new TabComplete());
		this.getCommand("buyenchant").setTabCompleter(new buyCompletions());
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
	}

	public String setPlaceholders(String message, Player player) {
		final String name = player.getName();
		final double bal = economy.getBalance(player);
		final String uuid = player.getUniqueId().toString();
		final Material heldItemType = player.getItemInHand().getType();
		return message.replace("%name%", name)
				.replace("%bal%", String.valueOf(bal))
				.replace("%uuid%", uuid)
				.replace("%item_name%", heldItemType.toString());

	}

	public String setPlaceholderEnchant(String message, int max, double price, String enchant, int lvl, Player player) {
		final String name = player.getName();
		final double bal = economy.getBalance(player);
		final String uuid = player.getUniqueId().toString();
		final Material heldItemType = player.getItemInHand().getType();
		return message.replace("%name%", name).replace("%bal%", String.valueOf(bal)).replace("%uuid%", uuid)
				.replace("%item_name%", heldItemType.toString()).replace("%max%", String.valueOf(max))
				.replace("%price%", String.valueOf(price)).replace("%enchant%", enchant)
				.replace("%lvl%", String.valueOf(lvl));
	}

	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {

		if (cmd.getName().equalsIgnoreCase("addenchant") && args.length == 1) {
			if (sender instanceof Player) {

				Player player = (Player) sender;
				if (player.hasPermission("addenchant.reload")) {
					if (args[0].equalsIgnoreCase("reload")) {
						this.reloadConfig();
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&areloaded addEnchant config"));
					}
				} else {
					String noPerms = this.getConfig().getString("messages.noPermission");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', setPlaceholders(noPerms, player)));
				}
			} else {
				if (args[0].equalsIgnoreCase("reload")) {
					this.reloadConfig();
					System.out.println("Config for addEnchant has been reloaded");
					;
				}
			}

		}

		if (sender instanceof Player) {

			Player player = (Player) sender;

			if (cmd.getName().toLowerCase().startsWith("addenchant") && args.length == 2) {
				if (player.isOp() || player.hasPermission("addenchant.enchant")) {
					String enchant = args[0];
					if (StringUtils.isNumeric(args[1])) {

						int lvl = Integer.valueOf(args[1]);
//						String consoleAddEnchant = this.getConfig().getString("messages.console-msgAddEnchant");

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
							if (enchant.equalsIgnoreCase("respiration")) {
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

				} else if (!player.hasPermission("addEnchant.enchant")) {
					String noPerms = this.getConfig().getString("messages.noPermission");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', setPlaceholders(noPerms, player)));
				}

//					player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
//							.enchantment(Enchantment.getByName(enchant), lvl, true).build());
//					player.sendMessage(ChatColor.GREEN + "Enchanted " + player.getItemInHand().getType() + " With enchantment " + enchant + " " + lvl);
//					this.getServer().getConsoleSender().sendMessage(ChatColor.RED + "User: " + player.getName() + " With UUID: " + player.getUniqueId() + ChatColor.GREEN + " Has Enchanted " + player.getItemInHand().getType() + " With enchantment " + enchant + " " + lvl);
//					player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

			} else if (cmd.getName().startsWith("addEnchant") && !args[0].equalsIgnoreCase("reload")) {
				player.sendMessage(ChatColor.RED
						+ "Invalid usage of the command... Please try doing \"/addEnchant <Enchant name> <level>\"");
			}

			if (cmd.getName().toLowerCase().startsWith("buyenchant") && args.length == 2) {
				if (player.isOp() || player.hasPermission("addenchant.buy")) {
					String enchant = args[0];
					enchant = enchant.toLowerCase();
					if (StringUtils.isNumeric(args[1])) {
						boolean buyable = config.getBoolean("enchants." + enchant + ".buyable");
						int max = config.getInt("enchants." + enchant + ".max");
						int lvl = Integer.valueOf(args[1]);

						String[] versions = this.getServer().getVersion().split(" ");
						String versionNumber = versions[2].substring(0, versions[2].length() - 1);
						versionNumber = versionNumber.substring(2);
						Double version = Double.valueOf(versionNumber);

						int price = config.getInt("enchants." + enchant + ".cost") * lvl;

						String bookGiven = this.getConfig().getString("messages.bookGiven");
						String enchantAdded = this.getConfig().getString("messages.enchantAdded");
						String noFunds = this.getConfig().getString("messages.no-funds");
						String tooBig = this.getConfig().getString("messages.tooHigh");
						String enchantDisabled = this.getConfig().getString("messages.enchantDisabled");
						String consoleItem = this.getConfig().getString("messages.console-msgItem");
						String consoleBook = this.getConfig().getString("messages.console-msgBook");
						String unEnchantable = this.getConfig().getString("messages.unEnchantable");

						if (max > 0) {

							if (lvl <= max) {

								@SuppressWarnings("unchecked")
								List<String> itemList = (List<String>) getConfig()
										.get("enchants." + enchant + ".enchantables");
								if (player.getItemInHand().getType() == Material.AIR || itemList.toString()
										.toUpperCase().contains(player.getItemInHand().getType().toString())) {

									try {

										if (enchant.equalsIgnoreCase("power")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.ARROW_DAMAGE, lvl, true)
																.build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.ARROW_DAMAGE, lvl, true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}
										if (enchant.equalsIgnoreCase("flame")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.ARROW_FIRE, lvl, true)
																.build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.ARROW_FIRE, lvl, true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}
										if (enchant.equalsIgnoreCase("infinity")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.ARROW_INFINITE, lvl, true)
																.build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.ARROW_INFINITE, lvl, true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}
										if (enchant.equalsIgnoreCase("punch")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.ARROW_KNOCKBACK, lvl, true)
																.build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.ARROW_KNOCKBACK, lvl, true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}
										if (enchant.equalsIgnoreCase("sharpness")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.DAMAGE_ALL, lvl, true)
																.build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.DAMAGE_ALL, lvl, true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}
										if (enchant.equalsIgnoreCase("baneofarthropods")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.DAMAGE_ARTHROPODS, lvl, true)
																.build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.DAMAGE_ARTHROPODS, lvl, true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}
										if (enchant.equalsIgnoreCase("smite")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.DAMAGE_UNDEAD, lvl, true)
																.build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.DAMAGE_UNDEAD, lvl, true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}
										if (enchant.equalsIgnoreCase("efficiency")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.DIG_SPEED, lvl, true).build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.DIG_SPEED, lvl, true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}
										if (enchant.equalsIgnoreCase("unbreaking")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.DURABILITY, lvl, true)
																.build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.DURABILITY, lvl, true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}
										if (enchant.equalsIgnoreCase("fortune")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.LOOT_BONUS_BLOCKS, lvl, true)
																.build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.LOOT_BONUS_BLOCKS, lvl, true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}
										if (enchant.equalsIgnoreCase("looting")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.LOOT_BONUS_MOBS, lvl, true)
																.build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.LOOT_BONUS_MOBS, lvl, true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}
										if (enchant.equalsIgnoreCase("luckofthesea")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.LUCK, lvl, true).build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.LUCK, lvl, true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}
										if (enchant.equalsIgnoreCase("respiration")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.OXYGEN, lvl, true).build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.OXYGEN, lvl, true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}
										if (enchant.equalsIgnoreCase("protection")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, lvl,
																		true)
																.build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, lvl,
																true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}
										if (enchant.equalsIgnoreCase("blastProtection")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.PROTECTION_EXPLOSIONS, lvl,
																		true)
																.build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.PROTECTION_EXPLOSIONS, lvl,
																true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}
										if (enchant.equalsIgnoreCase("featherFalling")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.PROTECTION_FALL, lvl, true)
																.build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.PROTECTION_FALL, lvl, true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}
										if (enchant.equalsIgnoreCase("fireprotection")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.PROTECTION_FIRE, lvl, true)
																.build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.PROTECTION_FIRE, lvl, true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}
										if (enchant.equalsIgnoreCase("aquaAffinity")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.WATER_WORKER, lvl, true)
																.build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.WATER_WORKER, lvl, true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}
										if (enchant.equalsIgnoreCase("fireaspect")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.FIRE_ASPECT, lvl, true)
																.build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.FIRE_ASPECT, lvl, true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}
										if (enchant.equalsIgnoreCase("depthstrider")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.DEPTH_STRIDER, lvl, true)
																.build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.DEPTH_STRIDER, lvl, true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}
										if (enchant.equalsIgnoreCase("lure")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.LURE, lvl, true).build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.LURE, lvl, true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}
										if (enchant.equalsIgnoreCase("silktouch") || enchant.equalsIgnoreCase("silk")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.SILK_TOUCH, lvl, true)
																.build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.SILK_TOUCH, lvl, true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}
										if (enchant.equalsIgnoreCase("thorns")) {
											ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);

											if (buyable == true) {

												try {

													if (((int) economy.getBalance(player) < price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else if ((int) economy.getBalance(player) >= price) {

														if (this.getServer().getVersion().contains("1.12")) {
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
														}
														player.setItemInHand(ItemCreator.copyOf(player.getItemInHand())
																.enchantment(Enchantment.THORNS, lvl, true).build());
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(enchantAdded, max, price, enchant,
																		lvl, player)));
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleItem, max, price,
																				enchant, lvl, player)));

													}

												} catch (Exception e2) {
													if (!((int) economy.getBalance(player) >= price)) {
														if (this.getServer().getVersion().contains("1.12")) {
//														p.sendMessage("It is 1.12.2");
														} else if (this.getServer().getVersion().contains("1.8")) {
															player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,
																	1);
														}

														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(noFunds, max, price, enchant, lvl,
																		player)));

													} else {
														EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book
																.getItemMeta();
														meta.addStoredEnchant(Enchantment.THORNS, lvl, true);
														book.setItemMeta(meta);
														player.getInventory().addItem(book);
														economy.withdrawPlayer(player, price);
														player.sendMessage(ChatColor.translateAlternateColorCodes('&',
																setPlaceholderEnchant(bookGiven, max, price, enchant,
																		lvl, player)));
														if (version >= 9) {

														} else if (version > 8 && version < 9) {

															player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

														}
														this.getServer().getConsoleSender()
																.sendMessage(ChatColor.translateAlternateColorCodes('&',
																		setPlaceholderEnchant(consoleBook, max, price,
																				enchant, lvl, player)));

													}
												}

											} else {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&',
														setPlaceholderEnchant(enchantDisabled, max, price, enchant, lvl,
																player)));
											}
										}

									} catch (Exception e) {

										player.sendMessage(ChatColor.RED + "An error occured please contact staff");
										e.printStackTrace();

									}

								} else if (player.getItemInHand() != null) {
									player.sendMessage(ChatColor.translateAlternateColorCodes('&',
											setPlaceholderEnchant(unEnchantable, max, price, enchant, lvl, player)));
								}

							} else {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&',
										setPlaceholderEnchant(tooBig, max, price, enchant, lvl, player)));
							}
						} else {
							player.sendMessage(ChatColor.RED
									+ "This enchant does not exist if you think it does \nplease contact staff and report it");
						}

					} else if (cmd.getName().toLowerCase().startsWith("buyenchant")) {
						player.sendMessage(ChatColor.RED
								+ "Invalid usage of the command... Please try doing \"/buyEnchant <Enchant name> <level>\"");
					}

				} else if (!player.hasPermission("addEnchant.buy")) {
					String noPerms = this.getConfig().getString("messages.noPermission");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', setPlaceholders(noPerms, player)));
				}

			} else if (cmd.getName().toLowerCase().startsWith("buyenchant")) {
				player.sendMessage(ChatColor.RED
						+ "Invalid usage of the command... Please try doing \"/buyEnchant <Enchant name> <level>\"");
			}

		}

		return true;
	}

}
