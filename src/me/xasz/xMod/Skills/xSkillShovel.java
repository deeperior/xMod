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
public class xSkillShovel extends xSkillFarmingSkill {
	/**
	 * calculates the shovel drops and xp
	 * @param player
	 * @param block
	 */
	public static void calc(Player player,Block block){
		if(!xMod.shovelIsEnabled || !player.hasPermission(xMod.permSkillShovel)){
			return;
		}
		if(!xFunc.isShovel(player.getItemInHand().getTypeId())){
			xPlayer.sendDebugMessage(player, "No Shovel in Hand - Skill not active");
			return;	
		}
		xPlayerProfile profile = xMod.getPlayerProfile(player);		
		xPlayer.sendDebugMessage(player, "SHOVEL-Calculation started");
		//xp calculation
		int xp = xMod.shovelTickEXP;
		
		//Double drop chance
		double chance = getChance(player);
		if((Math.random() * 100.0f) < chance ){	
			if(block.getType() != Material.CLAY){
				xBlock.dropNaturallyItem(block.getTypeId(), block);	
			}else{
				xBlock.dropNaturallyItem(Material.CLAY_BALL, block);
				xBlock.dropNaturallyItem(Material.CLAY_BALL, block);
				xBlock.dropNaturallyItem(Material.CLAY_BALL, block);
				xBlock.dropNaturallyItem(Material.CLAY_BALL, block);
			}
		}		
		
		if(block.getType() == Material.CLAY){
			xp *= xMod.shovelClayTickMultiplier;
		}
		
		
		if(xp < 1){
			xp = 1;
		}
		profile.gainXP(xSkillType.SHOVEL,xp);
		doBonusDrops(profile,block);
		
	}
	public static double getChance(xPlayerProfile player){
		//REPAIRPROCCHANCE
		double chance = (player.getShovelLVL()*xMod.shovelDoubleDropPerLevelFactor);
		if(chance > xMod.shovelMaxDoubleDropChance){
			chance = xMod.shovelMaxDoubleDropChance;
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
	 * return true if the item is handled by this class, otherwise false
	 * @param m
	 * @return
	 */
	public static boolean isItemBlockHandled(Material m){
		boolean is = false;
		if(  m == Material.DIRT ||
			m == Material.SAND ||
			m == Material.GRAVEL ||
        	m == Material.CLAY ||
        	m == Material.SOUL_SAND ||
        	m == Material.GRASS ||
        	m == Material.MYCEL)
	{
			is = true;
	}
		return is;
	}
	public static void doBonusDrops(xPlayerProfile profile,Block block){
		
		if(profile.getShovelLVL() >= 7){
			if((Math.random() * 250.0f) < 1.f ){
				xBlock.dropNaturallyItem(Material.GLOWSTONE_DUST, block);
			}		
		}	
		if(profile.getShovelLVL() >= 15){
			if((Math.random() * 5000.0f) < 1.f ){
				xBlock.dropNaturallyItem(Material.DIAMOND, block);
			}		
		}			
		if(profile.getShovelLVL() >= 5){
			if((Math.random() * 1000.0f) < 1.f ){
				xBlock.dropNaturallyItem(Material.GHAST_TEAR, block);
			}		
		}				
		if(profile.getShovelLVL() >= 10){
			if((Math.random() * 175.0f) < 1.f ){
				xBlock.dropNaturallyItem(289, block);
			}		
		}				
		if(profile.getShovelLVL() >= 7){
			if((Math.random() * 350.0f) < 1.f ){
				xBlock.dropNaturallyItem(Material.BONE, block);
			}		
		}					
		if(profile.getShovelLVL() >= 13){
			if((Math.random() * 550.0f) < 1.f ){
				xBlock.dropNaturallyItem(Material.SLIME_BALL, block);
			}		
		}						
		if(profile.getShovelLVL() >= 2){
			if((Math.random() * 700.0f) < 1.f ){
				xBlock.dropNaturallyItem(Material.GOLD_NUGGET, block);
			}		
		}	
	}
}
