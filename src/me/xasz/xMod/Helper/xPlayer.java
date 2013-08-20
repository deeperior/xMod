package me.xasz.xMod.Helper;

import me.xasz.xMod.xMod;
import me.xasz.xMod.xPlayerProfile;
import me.xasz.xMod.Skills.xSkillAxe;
import me.xasz.xMod.Skills.xSkillHoe;
import me.xasz.xMod.Skills.xSkillPickaxe;
import me.xasz.xMod.Skills.xSkillRepair;
import me.xasz.xMod.Skills.xSkillShear;
import me.xasz.xMod.Skills.xSkillShovel;
import me.xasz.xMod.Skills.xSkillType;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/** used for static player handling
 * prevents some usefull functions
 * @author xasz
 *
 */
public class xPlayer {
	
	
	/** Gives one item to the player.
	 * @param player
	 * @param itemid
	 */
	public static void giveItem(Player player, int itemid){
		player.getInventory().addItem(new ItemStack(itemid));
	}
	
	/** Removes one item from players inventory
	 * @param player
	 * @param itemid
	 */
	public static void removeItem(Player player, int itemid){
		ItemStack[] currentInventory =  player.getInventory().getContents();
		for(ItemStack currentItem : currentInventory){
			if(currentItem != null){
				if(currentItem.getTypeId() == itemid){
					if(currentItem.getAmount() == 1){
						currentItem.setTypeId(0);
						currentItem.setAmount(0);					
					}else{
						currentItem.setAmount(currentItem.getAmount()-1);
					}
					player.getInventory().setContents(currentInventory);
					player.updateInventory();
					return;
				}				
			}
		}		
	}
	
	/** Checks if the player has the right item in his inventory.
	 *  Warning: Does not check the amount, just the type!
	 * @param player
	 * @param item
	 * @return
	 */
	public static boolean hasItem(Player player, ItemStack item){
		ItemStack[] currentInventory =  player.getInventory().getContents();
	
		for(ItemStack currentItem : currentInventory){
			if( currentItem != null ){
				if(currentItem.getTypeId() == item.getTypeId()){
					if(currentItem.getAmount() >= item.getAmount())
					return true;
				}				
			}
		}
		return false;
	}
	
	/** Send Success Message
	 * Always displayed
	 * @param player
	 * @param message
	 */
	public static void sendSuccessMessage(Player player, String message){
		player.sendMessage(ChatColor.WHITE + "[xMod]" + ChatColor.GREEN + message );
	}
	
	/** Send Debug Message
	 * Only displayed if DEBUG_Mode is activated
	 * @param player
	 * @param message
	 */
	public static void sendDebugMessage(Player player, String message){
		if(xMod.DEBUG)
		player.sendMessage(ChatColor.WHITE + "[xMod]" + ChatColor.LIGHT_PURPLE + message );
	}	
	
	/** Send Error Message
	 * Always Displayed
	 * @param player
	 * @param message
	 */
	public static void sendErrorMessage(Player player, String message){
		player.sendMessage(ChatColor.WHITE + "[xMod]" + ChatColor.RED + message );
	}
	/**
	 * sends information to the player
	 * sends all informations about his level and skills
	 */
	public static void getLevelOverview( xPlayerProfile source, xPlayerProfile destination){
		//TODO
		if (destination.getPlayer() != null)
		{
			int xtralevel = source.getXtraLevel();
			
			xPlayer.sendSuccessMessage(destination.getPlayer(),"Overview");
			
			if (xMod.repairIsEnabled)
			{
				destination.getPlayer().sendMessage(ChatColor.RED + "REPAIR-LVL:    "  + ChatColor.YELLOW + source.getRepairLVL() + ChatColor.WHITE +" ("+source.getRepairXP()+"/"+source.getRepairCurrentLevelMaxXP()+") Doublerepair Chance: " + xSkillRepair.getChance(source) + "%");
			}
			if (xMod.pickaxeIsEnabled)
			{
				destination.getPlayer().sendMessage(ChatColor.RED + "PICKAXE-LVL:    " + ChatColor.YELLOW + source.getPickaxeLVL() + ChatColor.WHITE +" ("+source.getPickaxeXP()+"/"+source.getPickaxeCurrentLevelMaxXP()+") Doubledrop Chance: " + xSkillPickaxe.getChance(source) + "%");
			}
			if (xMod.shovelIsEnabled)
			{
				destination.getPlayer().sendMessage(ChatColor.RED + "SHOVEL-LVL:    " + ChatColor.YELLOW + source.getShovelLVL() + ChatColor.WHITE +" ("+source.getShovelXP()+"/"+source.getShovelCurrentLevelMaxXP()+") Doubledrop Chance: " + xSkillShovel.getChance(source) + "%");
			}
			if (xMod.axeIsEnabled)
			{
				destination.getPlayer().sendMessage(ChatColor.RED + "AXE-LVL:    " + ChatColor.YELLOW + source.getAxeLVL() + ChatColor.WHITE +" ("+source.getAxeXP()+"/"+source.getAxeCurrentLevelMaxXP()+") Doubledrop Chance: " + xSkillAxe.getChance(source) + "%");
			}
			if (xMod.shearIsEnabled)
			{
				destination.getPlayer().sendMessage(ChatColor.RED + "SHEAR-LVL:    " + ChatColor.YELLOW + source.getShearLVL() + ChatColor.WHITE +" ("+source.getShearXP()+"/"+source.getShearCurrentLevelMaxXP()+") Doubledrop Chance: " + xSkillShear.getChance(source) + "%");
			}
			if (xMod.hoeIsEnabled)
			{
				destination.getPlayer().sendMessage(ChatColor.RED + "HOE-LVL:    " + ChatColor.YELLOW + source.getHoeLVL() + ChatColor.WHITE +" ("+source.getHoeXP()+"/"+source.getHoeCurrentLevelMaxXP()+") Doubledrop Chance: " + xSkillHoe.getChance(source) + "%");
			}
			
			destination.getPlayer().sendMessage(ChatColor.BLACK +"______________________________");
			destination.getPlayer().sendMessage(ChatColor.WHITE + source.GetPlayerName()+" Xtralevel: \t" + xtralevel);
		}
	}
	/**
	 * show a toplist of one skill to a specific player
	 * @param profiles to show
	 * @param destination - this player geht the outputmessage
	 * @param skill - for which skill
	 */
	public static void showTopList(xPlayerProfile[] profiles, Player destination, xSkillType skill){
		if(skill == xSkillType.NONE){
			return;
		}
		int rang = 1;
		for (int i = profiles.length - 1; i >= 0; i--)
		{
			xPlayerProfile current = profiles[i];
			if(skill == xSkillType.AXE){
				if(rang == 1){
					xPlayer.sendSuccessMessage(destination,"xTop: AXE-Skill"); 
				}
				destination.sendMessage(ChatColor.WHITE + String.valueOf(rang) + ". " + ChatColor.RED + current.GetPlayerName() + "\t" + ChatColor.YELLOW + current.getAxeLVL());
			}else if(skill == xSkillType.PICKAXE){	
				if(rang == 1){
					xPlayer.sendSuccessMessage(destination,"xTop: PICKAXE-Skill"); 
					destination.getPlayer().sendMessage(ChatColor.BLACK +"______________________________");
				}
				destination.sendMessage(ChatColor.WHITE + String.valueOf(rang) + ". " + ChatColor.RED + current.GetPlayerName() + "\t" + ChatColor.YELLOW + current.getPickaxeLVL());			
			}else if(skill == xSkillType.REPAIR){
				if(rang == 1){
					xPlayer.sendSuccessMessage(destination,"xTop: REPAIR-Skill"); 
					destination.getPlayer().sendMessage(ChatColor.BLACK +"______________________________");
				}
				destination.sendMessage(ChatColor.WHITE + String.valueOf(rang) + ". " + ChatColor.RED + current.GetPlayerName() + "\t" + ChatColor.YELLOW + current.getRepairLVL());
			}else if(skill == xSkillType.SHEAR){
				if(rang == 1){
					xPlayer.sendSuccessMessage(destination,"xTop: SHEAR-Skill"); 
					destination.getPlayer().sendMessage(ChatColor.BLACK +"______________________________");
				}
				destination.sendMessage(ChatColor.WHITE + String.valueOf(rang) + ". " + ChatColor.RED + current.GetPlayerName() + "\t" + ChatColor.YELLOW + current.getShearLVL());
			}else if(skill == xSkillType.SHOVEL){
				if(rang == 1){
					xPlayer.sendSuccessMessage(destination,"xTop: SHOVEL-Skill"); 
					destination.getPlayer().sendMessage(ChatColor.BLACK +"______________________________");
				}
				destination.sendMessage(ChatColor.WHITE + String.valueOf(rang) + ". " + ChatColor.RED + current.GetPlayerName() + "\t" + ChatColor.YELLOW + current.getShovelLVL());
			}	
			rang ++;
			if(rang > 10){
				break;
			}
		}
	}
}
