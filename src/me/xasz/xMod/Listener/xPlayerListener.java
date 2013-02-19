package me.xasz.xMod.Listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.block.Action;


import me.xasz.xMod.xMod;
import me.xasz.xMod.xPlayerProfile;
import me.xasz.xMod.Helper.xPlayer;
//import me.xasz.xMod.Skills.xSkillBow;
import me.xasz.xMod.Skills.xSkillRepair;
import me.xasz.xMod.Skills.xSkillShear;

/** called from bukkit, if registered playerelement exists
 * manages the playerlist
 * @author xasz
 *
 */
public class xPlayerListener implements Listener {

	private final xMod x;
	private List<xPlayerProfile> playerProfiles = new ArrayList<xPlayerProfile>(); 
	public xPlayerListener (final xMod instance){
		x = instance;
	}
	public void savePlayersAndClear(){
		
		for(xPlayerProfile current : playerProfiles){
			x.Connector.savePlayerProfile(current);
		}
		playerProfiles.clear();
	}
	public void saveOnlinePlayers(){
		
		for(xPlayerProfile current : playerProfiles){
			System.out.println("Saving Playerporfile:"+ current.GetPlayerName());
			x.Connector.savePlayerProfile(current);
		}
	}
	public void loadCurrentPlayers(){
		for(Player current : x.getServer().getOnlinePlayers()){
			playerProfiles.add(x.Connector.loadPlayerProfile(current));	
		}
	}
	
	/* (non-Javadoc)
	 * @see org.bukkit.event.player.PlayerListener#onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent)
	 * adds players to online-playerlist for profilehandling
	 * 
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event){
	 //TO-DO sag dem spieler blalbla level und so weiter	
		xPlayer.sendSuccessMessage(event.getPlayer(), "Willkommen " + event.getPlayer().getDisplayName());
		
		playerProfiles.add(x.Connector.loadPlayerProfile(event.getPlayer()));
		
		if(xMod.DEBUG){
			x.getServer().broadcastMessage(ChatColor.WHITE + "[xMod]" + ChatColor.LIGHT_PURPLE +"Player joined " + event.getPlayer().getDisplayName() + " Playercount: " + playerProfiles.size());
		}
	}
	
	/* (non-Javadoc)
	 * @see org.bukkit.event.player.PlayerListener#onPlayerQuit(org.bukkit.event.player.PlayerQuitEvent)
	 * removes players from online-playerlist
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent event){
		try
		{
			//save to xml
			xPlayerProfile t = getPlayerProfile(event.getPlayer());
			//saveOnlinePlayers();
			//x.Connector.savePlayerProfile(t);
			playerProfiles.remove(t);
			if(xMod.DEBUG){
				x.getServer().broadcastMessage(ChatColor.WHITE + "[xMod]" + ChatColor.LIGHT_PURPLE +"Player quit " + event.getPlayer().getDisplayName() + " Playercount: " + playerProfiles.size());
			}
		}catch(Exception e){
			System.out.println("[xMod] Could not save Players to Database on Playerlogout.");
		}
	}
	
	/* (non-Javadoc)
	 * @see org.bukkit.event.player.PlayerListener#onPlayerQuit(org.bukkit.event.player.PlayerQuitEvent)
	 * removes players from online-playerlist
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerKick (PlayerKickEvent event){
		try
		{
			//save to xml
			xPlayerProfile t = getPlayerProfile(event.getPlayer());
			//saveOnlinePlayers();
			x.Connector.savePlayerProfile(t);
			playerProfiles.remove(t);
			if(xMod.DEBUG){
				x.getServer().broadcastMessage(ChatColor.WHITE + "[xMod]" + ChatColor.LIGHT_PURPLE +"Player kicked " + event.getPlayer().getDisplayName() + "Playercount: " + playerProfiles.size());
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}	
	
	/* (non-Javadoc)
	 * @see org.bukkit.event.player.PlayerListener#onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent)
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerInteract(PlayerInteractEvent event) {
		xPlayer.sendDebugMessage(event.getPlayer(), "xPlayerListener:onPlayerInteract() triggered");	
		
		if((event.getAction() == Action.RIGHT_CLICK_AIR && event.getPlayer().getItemInHand().getType() == Material.ARROW) || (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().getItemInHand().getType() == Material.ARROW)){
			xPlayer.sendDebugMessage(event.getPlayer(), "ArrowShoot triggered");	
			//TODO: Cooldowntimer
			//xSkillBow.throwArrow(event.getPlayer());
		}
		
		// Is Player hitting on Repairblock
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getTypeId() == 42){
			xPlayer.sendDebugMessage(event.getPlayer(), "Repair triggered");
			xSkillRepair.repair(event.getPlayer());
		}
		
		
		if(xMod.DEBUG){		
		// Is Playing Hitting on Goldblock - durability of the item will displayed - in debugmode
			if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getTypeId() == 41){
					xPlayer.sendDebugMessage(event.getPlayer(), "Your Itemdurabilty is: " + event.getPlayer().getItemInHand().getDurability() +" -- only availible in DEBUG-Mode");
			}	
		}
	}
	
	/** player -> xPlayerProfile
	 * @param player
	 * @return
	 */
	public xPlayerProfile getPlayerProfile(Player player){
		for(xPlayerProfile current : playerProfiles){
				if(current != null){
					if(current.getPlayer().getDisplayName().matches(player.getDisplayName())){
					return current;
				}
			}
		}
		return null;
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerInteractEntity (PlayerInteractEntityEvent event){
		if(event.getRightClicked() instanceof Sheep){
			Sheep sheep = (Sheep)event.getRightClicked();
			xSkillShear.shear(event.getPlayer(), sheep);
		}
	}
}
