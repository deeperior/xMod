package me.xasz.xMod.Skills;

import me.xasz.xMod.xMod;
import me.xasz.xMod.xPlayerProfile;
import me.xasz.xMod.Helper.xBlock;
import me.xasz.xMod.Helper.xFunc;
import me.xasz.xMod.Helper.xPlayer;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * 
 * @author xasz
 *
 */
public class xSkillHoe extends xSkillFarmingSkill {
	public static double getChance(xPlayerProfile player){
		//REPAIRPROCCHANCE
		double chance = (player.getHoeLVL()*xMod.hoeDoubleDropPerLevelFactor);
		if(chance > xMod.hoeMaxDoubleDropChance){
			chance = xMod.hoeMaxDoubleDropChance;
		}
		//rounding 
		chance = chance * 100;
		chance = Math.round(chance);
		chance = chance / 100;
		return chance;
	}	
	public static double getChance(Player player){
		return getChance(xMod.getPlayerProfile(player));
	}	
	/**
	 * calculates the hoe drops and xp
	 * @param player
	 * @param block
	 */
	public static void calc(Player player,Block block){
		xPlayer.sendDebugMessage(player, "hoeskill");
		if(!xMod.hoeIsEnabled || !player.hasPermission(xMod.permSkillHoe)){
			return;
		}
		if(!xFunc.isHoe(player.getItemInHand().getTypeId())){
			xPlayer.sendDebugMessage(player, "No Hoe in Hand - Skill not active");	
			return;
		}
		xPlayerProfile profile = xMod.getPlayerProfile(player);		
		xPlayer.sendDebugMessage(player, "HOE-Calculation started");
		//xp calculation
		int xp = xMod.hoeTickEXP;
		//Double drop chance
		double chance = getChance(player);
		if((Math.random() * 100.0f) < chance ){
			if(block.getType() == Material.MELON_BLOCK){
				xBlock.dropNaturallyItem(Material.MELON, block);
				xBlock.dropNaturallyItem(Material.MELON, block);
				xBlock.dropNaturallyItem(Material.MELON, block);
				xBlock.dropNaturallyItem(Material.MELON, block);
				xBlock.dropNaturallyItem(Material.MELON, block);
				xBlock.dropNaturallyItem(Material.MELON, block);
			} if(block.getType() == Material.CROPS){
				xBlock.dropNaturallyItem(Material.WHEAT, block);	
			} if(block.getType() == Material.LONG_GRASS){
				xBlock.dropNaturallyItem(Material.SEEDS, block);
				xBlock.dropNaturallyItem(Material.SEEDS, block);	
			}
			else{
				xBlock.dropNaturallyItem(Material.WHEAT, block);	
			}
		}		
	
		if(xp < 1){
			xp = 1;
		}	
		profile.gainXP(xSkillType.HOE,xp);
		
		if(profile.getShearLVL() >= 30){
			if((Math.random() * 1000.0f) < 1.0f ){
				xBlock.dropNaturallyItem(Material.GLASS_BOTTLE, block);
			}		
		}		
		
	}
	
	/**
	 * return true if the item is handled by this class, otherwise false
	 * @param m
	 * @return
	 */
	public static boolean isItemBlockHandled(Material m){
		boolean is = false;
		if(  m == Material.LONG_GRASS ||
			m == Material.CROPS ||
			m == Material.PUMPKIN ||
			m == Material.MELON_BLOCK ||
			m == Material.POTATO ||
			m == Material.CARROT ||
			m == Material.NETHER_WARTS)
			{
					is = true;
			}
		return is;
	}
}
