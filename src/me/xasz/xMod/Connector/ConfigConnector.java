package me.xasz.xMod.Connector;


import me.xasz.xMod.xMod;

import org.bukkit.configuration.file.FileConfiguration;
/** saves and loads the config.yml for the plugin
 * @author xasz
 *
 */
public class ConfigConnector {
	private FileConfiguration config = null;
	private xMod x = null;
	
	public ConfigConnector(xMod y){
		x = y;
	    config = x.getConfig();
	}
	/** loads the config
	 */
	public void load(){

    	
        xMod.RecipeIce=config.getBoolean("general.crafting.IceRecipe",xMod.RecipeIce);
        xMod.RecipeMossyCobble=config.getBoolean("general.crafting.MossyCobbleStone",xMod.RecipeMossyCobble);
        xMod.RecipeMossyBrick=config.getBoolean("general.crafting.MossyBrickStoneRecipe",xMod.RecipeMossyBrick);
        xMod.RecipeCrackedBrick=config.getBoolean("general.crafting.CrackeyBrickStoneRecipe",xMod.RecipeCrackedBrick);
        xMod.RecipeLeather=config.getBoolean("general.crafting.RecipeLeather",xMod.RecipeLeather);
        xMod.RecipeChisledStone=config.getBoolean("general.crafting.RecipeChisledStone",xMod.RecipeChisledStone);
        
		
        
        xMod.axeCriticalChancePerLevel=config.getDouble("skills.axe.CriticalChancePerLevel",xMod.axeCriticalChancePerLevel);
		xMod.axeCriticalHitMultiplicator=config.getDouble("skills.axe.CriticalHitMultiplicator",xMod.axeCriticalHitMultiplicator);
		xMod.axeDoubleDropPerLevelFactor=config.getDouble("skills.axe.DoubleDropPerLevelFactor",xMod.axeDoubleDropPerLevelFactor);
		
		xMod.pickaxeDoubleDropFactor=config.getDouble("skills.pickaxe.DoubleDropFactor",xMod.pickaxeDoubleDropFactor);
		xMod.pickaxeDoubleDropPerLevelFactor=config.getDouble("skills.pickaxe.DoubleDropPerLevelFactor",xMod.pickaxeDoubleDropPerLevelFactor);
		
		xMod.shearInstantGrowChancePerLevelFactor=config.getDouble("skills.shear.InstantGrowChancePerLevelFactor",xMod.shearInstantGrowChancePerLevelFactor);
		xMod.shearDoubleDropPerLevelFactor=config.getDouble("skills.shear.DoubleDropPerLevelFactor",xMod.shearDoubleDropPerLevelFactor);
		
		xMod.shovelDoubleDropPerLevelFactor=config.getDouble("skills.shovel.DoubleDropPerLevelFactor",xMod.shovelDoubleDropPerLevelFactor);
		
		xMod.swordTickEXPblock=config.getInt("skills.sword.TickEXPblock",xMod.swordTickEXPblock);
		
		
		xMod.repairTickEXP=config.getInt("skills.repair.tick",xMod.repairTickEXP);
		xMod.repairMaxLevel=config.getInt("skills.repair.maxlevel",xMod.repairMaxLevel);
		
		xMod.pickaxeTickEXP=config.getInt("skills.pickaxe.tick",xMod.pickaxeTickEXP);
		xMod.pickaxeMaxLevel=config.getInt("skills.pickaxe.maxlevel",xMod.pickaxeMaxLevel);

		xMod.axeTickEXP=config.getInt("skills.axe.tick",xMod.axeTickEXP);
		xMod.axeMaxLevel=config.getInt("skills.axe.maxlevel",xMod.axeMaxLevel);
		
		xMod.shovelTickEXP=config.getInt("skills.shovel.tick",xMod.shovelTickEXP);
		xMod.shovelMaxLevel=config.getInt("skills.shovel.maxlevel",xMod.shovelMaxLevel);
		
		xMod.shearTickEXP=config.getInt("skills.shear.tick",xMod.shearTickEXP);
		xMod.shearSheepTickEXP=config.getInt("skills.shear.sheeptick",xMod.shearTickEXP);
		
		xMod.shearMaxLevel=config.getInt("skills.shear.maxlevel",xMod.shearMaxLevel);
		
		xMod.hoeTickEXP=config.getInt("skills.hoe.tick",xMod.hoeTickEXP);
		xMod.hoeMaxLevel=config.getInt("skills.hoe.maxlevel",xMod.hoeMaxLevel);
		
		xMod.DEBUG=config.getBoolean("general.debugmodus",xMod.DEBUG);
		
		xMod.mysqlDB = config.getString("mysql.db",xMod.mysqlDB);		
		xMod.mysqlUsername = config.getString("mysql.username",xMod.mysqlUsername);		
		xMod.mysqlServer = config.getString("mysql.server",xMod.mysqlServer);		
		xMod.mysqlPort = config.getInt("mysql.port",xMod.mysqlPort);		
		xMod.mysqlPassword = config.getString("mysql.password",xMod.mysqlPassword);


		xMod.DEBUG=config.getBoolean("general.debugmodus",xMod.DEBUG);

		xMod.repairIsEnabled = config.getBoolean("skills.repair.enabled",xMod.repairIsEnabled);
		xMod.repairFactorA=config.getInt("skills.repair.factors.A",xMod.repairFactorA);
		xMod.repairFactorB=config.getInt("skills.repair.factors.B",xMod.repairFactorB);
		xMod.repairFactorC=config.getInt("skills.repair.factors.C",xMod.repairFactorC);

		xMod.pickaxeIsEnabled = config.getBoolean("skills.pickaxe.enabled",xMod.pickaxeIsEnabled);
		xMod.pickaxeFactorA=config.getInt("skills.pickaxe.factors.A",xMod.pickaxeFactorA);
		xMod.pickaxeFactorB=config.getInt("skills.pickaxe.factors.B",xMod.pickaxeFactorB);
		xMod.pickaxeFactorC=config.getInt("skills.pickaxe.factors.C",xMod.pickaxeFactorC);
		
		xMod.pickaxeFactorC=config.getInt("skills.pickaxe.factors.C",xMod.pickaxeFactorC);

		xMod.pickaxeCoalTickMultiplier = config.getDouble("skills.pickaxe.tickmultiplier.Coal",xMod.pickaxeCoalTickMultiplier);
		xMod.pickaxeObsidianTickMultiplier = config.getDouble("skills.pickaxe.tickmultiplier.Obsidian",xMod.pickaxeObsidianTickMultiplier);
		xMod.pickaxeIronTickMultiplier = config.getDouble("skills.pickaxe.tickmultiplier.Iron",xMod.pickaxeIronTickMultiplier);
		xMod.pickaxeGoldTickMultiplier = config.getDouble("skills.pickaxe.tickmultiplier.Gold",xMod.pickaxeGoldTickMultiplier);
		xMod.pickaxeLapisTickMultiplier = config.getDouble("skills.pickaxe.tickmultiplier.Lapis",xMod.pickaxeLapisTickMultiplier);
		xMod.pickaxeDiamondTickMultiplier = config.getDouble("skills.pickaxe.tickmultiplier.Diamond",xMod.pickaxeDiamondTickMultiplier);
		xMod.pickaxeGlowstoneTickMultiplier = config.getDouble("skills.pickaxe.tickmultiplier.Glowstone",xMod.pickaxeGlowstoneTickMultiplier);
		xMod.pickaxeNetherbrickTickMultiplier = config.getDouble("skills.pickaxe.tickmultiplier.Netherbrick",xMod.pickaxeNetherbrickTickMultiplier);
		xMod.pickaxeNetherrackTickMultiplier = config.getDouble("skills.pickaxe.tickmultiplier.Netherrack",xMod.pickaxeNetherrackTickMultiplier);
		xMod.pickaxeSandstoneTickMultiplier = config.getDouble("skills.pickaxe.tickmultiplier.Sandstone",xMod.pickaxeSandstoneTickMultiplier);
		xMod.pickaxeRedStoneTickMultiplier = config.getDouble("skills.pickaxe.tickmultiplier.Redstone",xMod.pickaxeRedStoneTickMultiplier);
		
		
		xMod.axeIsEnabled = config.getBoolean("skills.axe.enabled",xMod.axeIsEnabled);
		xMod.axeFactorA=config.getInt("skills.axe.factors.A",xMod.axeFactorA);
		xMod.axeFactorB=config.getInt("skills.axe.factors.B",xMod.axeFactorB);
		xMod.axeFactorC=config.getInt("skills.axe.factors.C",xMod.axeFactorC);

		xMod.shovelIsEnabled = config.getBoolean("skills.shovel.enabled",xMod.shovelIsEnabled);
		xMod.shovelFactorA=config.getInt("skills.shovel.factors.A",xMod.shovelFactorA);
		xMod.shovelFactorB=config.getInt("skills.shovel.factors.B",xMod.shovelFactorB);
		xMod.shovelFactorC=config.getInt("skills.shovel.factors.C",xMod.shovelFactorC);
		xMod.shovelClayTickMultiplier=config.getDouble("skills.pickaxe.tickmultiplier.Clay",xMod.shovelClayTickMultiplier);

		xMod.shearIsEnabled = config.getBoolean("skills.shear.enabled",xMod.shearIsEnabled);
		xMod.shearFactorA=config.getInt("skills.shear.factors.A",xMod.shearFactorA);
		xMod.shearFactorB=config.getInt("skills.shear.factors.B",xMod.shearFactorB);
		xMod.shearFactorC=config.getInt("skills.shear.factors.C",xMod.shearFactorC);
		
		xMod.hoeIsEnabled = config.getBoolean("skills.hoe.enabled",xMod.hoeIsEnabled);
		xMod.hoeFactorA=config.getInt("skills.hoe.factors.A",xMod.hoeFactorA);
		xMod.hoeFactorB=config.getInt("skills.hoe.factors.B",xMod.hoeFactorB);
		xMod.hoeFactorC=config.getInt("skills.hoe.factors.C",xMod.hoeFactorC);		
		xMod.hoeWheatTickMultiplier = config.getDouble("skills.pickaxe.tickmultiplier.Wheat",xMod.hoeWheatTickMultiplier);
		xMod.hoeMelonTickMultiplier = config.getDouble("skills.pickaxe.tickmultiplier.Melon",xMod.hoeMelonTickMultiplier);
		xMod.hoePumpkinTickMultiplier = config.getDouble("skills.pickaxe.tickmultiplier.Pumpkin",xMod.hoePumpkinTickMultiplier);
		
		xMod.repairMaxProcChance=config.getDouble("skills.repair.MaxProcChance",xMod.repairMaxProcChance);
		xMod.pickaxeMaxDoubleDropChance=config.getDouble("skills.pickaxe.MaxDoubleDropChance",xMod.pickaxeMaxDoubleDropChance);
		xMod.hoeMaxDoubleDropChance=config.getDouble("skills.hoe.MaxDoubleDropChance",xMod.hoeMaxDoubleDropChance);
		xMod.axeMaxDoubleDropChance=config.getDouble("skills.axe.MaxDoubleDropChance",xMod.axeMaxDoubleDropChance);
		xMod.axeMaxCritChance=config.getDouble("skills.axe.MaxCritChance",xMod.axeMaxCritChance);
		xMod.shovelMaxDoubleDropChance=config.getDouble("skills.shovel.MaxDoubleDropChance",xMod.shovelMaxDoubleDropChance);
		xMod.shearMaxDoubleDropChance=config.getDouble("skills.shear.MaxDoubleDropChance",xMod.shearMaxDoubleDropChance);;
		xMod.shearMaxInstantSheepGrowChance=config.getDouble("skills.shear.MaxInstantSheepGrowChance",xMod.shearMaxInstantSheepGrowChance);
		xMod.repairChancePerLevelFactor=config.getDouble("skills.repair.ChancePerLevelFactor",xMod.repairChancePerLevelFactor);
		setDefault();
	    	
	}
	/** Set the config to default values.
	 * The Default Values are specified in the xMod.java
	 */
	public void setDefault(){
		if(config == null){
			return;
		}
	    config.options().copyDefaults(true);
	    x.saveConfig();
	}
	
}
