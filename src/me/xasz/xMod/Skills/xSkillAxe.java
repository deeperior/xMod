package me.xasz.xMod.Skills;

import me.xasz.xMod.xMod;
import me.xasz.xMod.xPlayerProfile;
import me.xasz.xMod.Helper.xBlock;
import me.xasz.xMod.Helper.xFunc;
import me.xasz.xMod.Helper.xPlayer;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
/*
 * 
 */
public class xSkillAxe extends xSkillFarmingSkill {
	public static double getChance(xPlayerProfile player){
		//REPAIRPROCCHANCE
		double chance = (player.getAxeLVL()*xMod.axeCriticalChancePerLevel);
		if(chance > xMod.axeMaxCritChance){
			chance = xMod.axeMaxCritChance;
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
	public static void calc(Player player,Block block){		
		if(!xMod.axeIsEnabled || !player.hasPermission(xMod.permSkillAxe)){
			return;
		}
		if(!xFunc.isAxe(player.getItemInHand().getTypeId())){
			xPlayer.sendDebugMessage(player, "No Axe in Hand - Skill not active");	
			return;
		}
		xPlayerProfile profile = xMod.getPlayerProfile(player);		
		xPlayer.sendDebugMessage(player, "AXE-Calculation started");
		//xp calculation
		int xp = xMod.axeTickEXP;
		
		//Double drop chance
		double chance = (profile.getAxeLVL()*xMod.axeDoubleDropPerLevelFactor);
		if(chance > xMod.axeMaxDoubleDropChance){
			chance = xMod.axeMaxDoubleDropChance;
		}
		if((Math.random() * 100.0f) < chance){
			xBlock.dropNaturallyItem(new ItemStack(block.getType(),1,(short)0,block.getData()), block);
		}		
	
		if(xp < 1){
			xp = 1;
		}
		profile.gainXP(xSkillType.AXE, xp);
		doBonusDrops(profile,block);
		
	}
	/**
	 * return true if the item is handled by this class, otherwise false
	 * @param m
	 * @return
	 */
	public static boolean isItemBlockHandled(Material m){
		boolean is = false;
		if(  m == Material.LOG ||
			m == Material.LEAVES)
		{
				is = true;
		}
		return is;
	}
	public static void doBonusDrops(xPlayerProfile profile,Block block){
		if(profile.getAxeLVL() >= 10){
			if((Math.random() * 1500.0f) < 1.0f ){
				xBlock.dropNaturallyItem(Material.GLASS_BOTTLE, block);
			}		
		}	
		/*if(profile.getAxeLVL() >= 15){
			if((Math.random() * 1500.0f) < 1.0f ){
				xBlock.dropNaturallyItem(Material.DIAMOND, block);
			}		
		}	*/
	}
}
