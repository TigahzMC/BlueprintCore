package me.tigahz.bpcore.gui;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.tigahz.bpcore.config.ProjectsConfig;
import me.tigahz.bpcore.serializable.City;
import me.tigahz.bpcore.util.Items;
import me.tigahz.bpcore.util.Ref;

public class CityMenu implements Listener {

	private final String name;
	private final Material material;
	private final String colour;
	
	public CityMenu(String name, Material material, String colour) {
		this.name = name;
		this.material = material;
		this.colour = colour;
	}
	
	Inventory i;
	ItemStack itemStack;
	
	public void createMenu(Player p) {
		
		String menuName = Ref.format("&" + colour + "&l" + ProjectsConfig.getConfig().getString("cities." + name + ".name"));
		i = Bukkit.createInventory(null, 36, menuName);
		
		@SuppressWarnings("unchecked")
		final List<City> city = (List<City>) ProjectsConfig.getConfig().getList("cities." + name + ".warps");
		city.forEach(it -> it.insertInto(i, material, colour));
		
		Items.createItem(i, itemStack, Material.SUGAR_CANE, 27, "&c&lGo Back");
		Items.createItem(i, itemStack, Material.BONE_MEAL, 35, "&c&lExit Menu");
		
		p.openInventory(i);
		
	}
	
	@EventHandler
	public void onCityClick(InventoryClickEvent e) {
		
		String menuName = Ref.format("&" + colour + "&l" + ProjectsConfig.getConfig().getString("cities." + name + ".name"));
		@SuppressWarnings({ "unchecked", "unused" })
		List<City> city = (List<City>) ProjectsConfig.getConfig().getList("cities." + name + ".warps"); 
		Player p = (Player) e.getWhoClicked();
		
		if (e.getClickedInventory().getName().equalsIgnoreCase(menuName)) {
			
			if (e.getClickedInventory() == null) {
				e.setCancelled(true);
			} else if (!e.getCurrentItem().hasItemMeta()) {
				e.setCancelled(true);
			} else if (e.getCurrentItem() == null) {
				e.setCancelled(true);
			} else if (Items.getClickedItem(e, "&c&lGo Back")) {
				MainMenu menu = new MainMenu();
				menu.createMenu(p);
				e.setCancelled(true);
			} else if (Items.getClickedItem(e, "&c&lExit Menu")) {
				e.setCancelled(true);
				p.closeInventory();
			} else if (Items.getClickedItem(e, name)) {
				e.setCancelled(true);
				p.closeInventory();
				p.performCommand("warp " + name);
			} else {
				e.setCancelled(true);
			}
			
		} else {
			return;
		}
		
	}
	
}
