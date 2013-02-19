package me.xasz.xMod.Skills;

import me.xasz.xMod.xMod;
import me.xasz.xMod.xPlayerProfile;
import me.xasz.xMod.Helper.xBlock;
import me.xasz.xMod.Helper.xPlayer;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;

public class xSkillShear {
	public static void shear(Player player, Sheep sheep){
		if(!xMod.shearIsEnabled || !player.hasPermission(xMod.permSkillShear)){
			return;
		}
		if(player.getItemInHand().getTypeId() != 359){
			xPlayer.sendDebugMessage(player, "No Shear in Hand - Skill not active");	
			return;
		}
		if(!sheep.isSheared()){
			xPlayerProfile profile = xMod.getPlayerProfile(player);		
			xPlayer.sendDebugMessage(player, "SHEAR-Calculation started");
			//xp calculation
			int xp = xMod.shearSheepTickEXP;
			
			//Double drop chance - 
			double chance = getChance(player);
			if((Math.random() * 100.0f) < chance ){	
				sheep.setSheared(false);
			}		
			
			if(xp < 1){
				xp = 1;
			}
			profile.gainXP(xSkillType.SHEAR, xp);
		}
	}	
	public static double getChance(xPlayerProfile player){
		//REPAIRPROCCHANCE
		double chance = (player.getShearLVL()*xMod.shearInstantGrowChancePerLevelFactor);
		if(chance > xMod.shearMaxInstantSheepGrowChance){
			chance = xMod.shearMaxInstantSheepGrowChance;
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
	 * calculates the shear drops and xp
	 * @param player
	 * @param block
	 */
	public static void calc(Player player,Block block){
		if(!xMod.shearIsEnabled || !player.hasPermission(xMod.permSkillShear)){
			return;
		}
		if(player.getItemInHand().getTypeId() != 359){
			xPlayer.sendDebugMessage(player, "No Shear in Hand - Skill not active");	
			return;
		}
		xPlayerProfile profile = xMod.getPlayerProfile(player);		
		xPlayer.sendDebugMessage(player, "SHEAR-Calculation started");
		//xp calculation
		int xp = xMod.shearTickEXP;
		//Double drop chance
		double chance = getChance(player);
		if((Math.random() * 100.0f) < chance ){	
			xBlock.dropNaturallyItem(block.getTypeId(), block);	
		}		

		profile.gainXP(xSkillType.SHEAR,xp);
		
		if(profile.getHoeLVL() >= 30){
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
		if(  m == Material.VINE ||
			m == Material.LONG_GRASS ||
			m == Material.LEAVES)
	{
			is = true;
	}
		return is;
	}
}
