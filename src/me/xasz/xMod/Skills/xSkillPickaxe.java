package me.xasz.xMod.Skills;



import me.xasz.xMod.xMod;
import me.xasz.xMod.xPlayerProfile;
import me.xasz.xMod.Helper.xBlock;
import me.xasz.xMod.Helper.xFunc;
import me.xasz.xMod.Helper.xPlayer;

//import org.bukkit.Material;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
/**
 * pickaxe skill
 * @author xasz
 *
 */

public class xSkillPickaxe extends xSkillFarmingSkill{
	
	public static double getChance(xPlayerProfile player){
		//REPAIRPROCCHANCE
		double chance = (player.getPickaxeLVL()*xMod.pickaxeDoubleDropPerLevelFactor);
		
		if(chance > xMod.pickaxeMaxDoubleDropChance){
			chance = xMod.pickaxeMaxDoubleDropChance;
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
	 * calculates the pickaxe drops and xp
	 * @param player
	 * @param block 
	 */
	public static void calc(Player player,Block block){
		if(!xMod.pickaxeIsEnabled || !player.hasPermission(xMod.permSkillPickaxe)){
			return;
		}
		if(!xFunc.isPickaxe(player.getItemInHand().getTypeId())){
			xPlayer.sendDebugMessage(player, "No Pickaxe in Hand - Skill not active");	
			return;
		}
		xPlayerProfile profile = xMod.getPlayerProfile(player);		
		xPlayer.sendDebugMessage(player, "PICKAXE-Calculation started");
		//xp calculation
		int xp = xMod.pickaxeTickEXP;
		
		//Double drop chance
		double chance = getChance(player);
		double temp = Math.random() * 100.0f;
		if((temp) < chance ){	
			
			switch(block.getType()){
				case STONE:
					if(profile.getPickaxeLVL() >= 10){
						xBlock.dropNaturallyItem(Material.STONE, block);
					}else{
						xBlock.dropNaturallyItem(Material.COBBLESTONE, block);						
					}
				break;
				case QUARTZ_ORE:
					xBlock.dropNaturallyItem(Material.QUARTZ, block);
				break;	
				case COAL_ORE:
					xBlock.dropNaturallyItem(Material.COAL, block);
				break;			
				case LAPIS_ORE:
					ItemStack item = new ItemStack(Material.getMaterial(351), 1, (byte)0,(byte)0x4);
					xBlock.dropNaturallyItem(item, block);
					xBlock.dropNaturallyItem(item, block);
					xBlock.dropNaturallyItem(item, block);					
				break;				
				case GLOWSTONE:
					xBlock.dropNaturallyItem(Material.GLOWSTONE_DUST, block);
					xBlock.dropNaturallyItem(Material.GLOWSTONE_DUST, block);
					xBlock.dropNaturallyItem(Material.GLOWSTONE_DUST, block);
					xBlock.dropNaturallyItem(Material.GLOWSTONE_DUST, block);
				break;				
				case DIAMOND_ORE:
					xBlock.dropNaturallyItem(Material.DIAMOND, block);
				break;				
				case REDSTONE_ORE:
					xBlock.dropNaturallyItem(Material.REDSTONE, block);
					xBlock.dropNaturallyItem(Material.REDSTONE, block);
					xBlock.dropNaturallyItem(Material.REDSTONE, block);
					xBlock.dropNaturallyItem(Material.REDSTONE, block);
				break;
				default:
					ItemStack bla = new ItemStack(block.getType(),1,(byte)0,block.getData());
					xBlock.dropNaturallyItem(bla, block);
					break;
			}
		}		
		
		switch (block.getType())
		{
		  case COAL_ORE:      xp *= xMod.pickaxeCoalTickMultiplier;    break;
		  case OBSIDIAN:      xp *= xMod.pickaxeObsidianTickMultiplier;    break;
		  case IRON_ORE:      xp *= xMod.pickaxeIronTickMultiplier;    break;
		  case QUARTZ_ORE:     xp *= xMod.pickaxeQuartzTickMultiplier;    break;
		  case GOLD_ORE:      xp *= xMod.pickaxeGoldTickMultiplier;    break;
		  case LAPIS_ORE:     xp *= xMod.pickaxeLapisTickMultiplier;    break;
		  case DIAMOND_ORE:   xp *= xMod.pickaxeDiamondTickMultiplier;    break;
		  case GLOWSTONE:     xp *= xMod.pickaxeGlowstoneTickMultiplier;    break;
		  case NETHER_BRICK:  xp *= xMod.pickaxeNetherbrickTickMultiplier;  break;
		  case NETHERRACK:    xp *= xMod.pickaxeNetherrackTickMultiplier;  break;
		  case SANDSTONE:     xp *= xMod.pickaxeSandstoneTickMultiplier;    break;
		  case REDSTONE_ORE:     xp *= xMod.pickaxeRedStoneTickMultiplier;    break;
		  default:
			  break;
		}

		
		if(xp < 1){
			xp = 1;
		}
		profile.gainXP(xSkillType.PICKAXE, xp);
		
		doBonusDrops(profile,block);
		
	}
	/**
	 * return true if the item is handled by this class, otherwise false
	 * @param m
	 * @return
	 */
	public static boolean isItemBlockHandled(Material m){
		boolean is = false;
		if(  m == Material.COBBLESTONE ||
			m == Material.STONE ||
			m == Material.COAL_ORE ||
        	m == Material.OBSIDIAN ||
        	m == Material.IRON_ORE ||
        	m == Material.GOLD_ORE ||
        	m == Material.LAPIS_ORE ||
        	m == Material.DIAMOND_ORE ||
        	m == Material.GLOWSTONE ||
        	m == Material.NETHER_BRICK ||
            m == Material.NETHERRACK ||
        	m == Material.SANDSTONE ||
        	m == Material.REDSTONE_ORE || 
            m == Material.QUARTZ_ORE ||
        	m == Material.ENDER_STONE
			)
	{
			is = true;
	}
		return is;
	}
	public static void doBonusDrops(xPlayerProfile profile,Block block){
		
		//special coarl drop chance for dia
		if(block.getType() == Material.COAL_ORE){
			if((Math.random() * 1000.0f) < 1f ){
				xBlock.dropNaturallyItem(Material.DIAMOND, block);
			}		
		}
		
		if(profile.getPickaxeLVL() >= 20){
			if((Math.random() * 3000.0f) < 1f ){
				xBlock.dropNaturallyItem(Material.DIAMOND, block);
			}		
		}	
		if(profile.getPickaxeLVL() >= 10){
			if((Math.random() * 700.0f) < 1.f ){
				xBlock.dropNaturallyItem(Material.IRON_ORE, block);
			}		
		}			
		if(profile.getPickaxeLVL() >= 5){
			if((Math.random() * 1500.0f) < 1.f ){
				xBlock.dropNaturallyItem(Material.SPONGE, block);
			}		
		}				
		if(profile.getPickaxeLVL() >= 2){
			if((Math.random() * 500.0f) < 1.f ){
				xBlock.dropNaturallyItem(Material.NETHER_BRICK, block);
			}		
		}				
		if(profile.getPickaxeLVL() >= 7){
			if((Math.random() * 1000.0f) < 1.f ){
				xBlock.dropNaturallyItem(Material.OBSIDIAN, block);
			}		
		}					
		if(profile.getPickaxeLVL() >= 13){
			if((Math.random() * 1000.0f) < 1.f ){
				xBlock.dropNaturallyItem(Material.ENDER_STONE, block);
			}		
		}		
		if(profile.getPickaxeLVL() >= 8){
			if((Math.random() * 500.0f) < 1.f ){
				xBlock.dropNaturallyItem(Material.GLOWSTONE, block);
			}		
		}
		
		//debug
		if(xMod.DEBUG)
		{
			//proc calculation
			//10% dirt proc chance ... YEAH
			if((int)(Math.random() * 100.0f) < 10.0f ){
				xPlayer.sendDebugMessage(profile.getPlayer(), "You got the 10 % dirt drop");
				xBlock.dropNaturallyItem(3, block);
			}	
		}
	}
}
