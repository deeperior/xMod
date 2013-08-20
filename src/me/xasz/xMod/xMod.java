package me.xasz.xMod;

import java.io.File;


import org.bukkit.craftbukkit.Main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.xasz.xMod.Connector.ConfigConnector;
import me.xasz.xMod.Connector.xConnector;
import me.xasz.xMod.Connector.xMysqlConnector;
import me.xasz.xMod.Executor.xConfigExecutor;
import me.xasz.xMod.Executor.xStatsExecutor;
import me.xasz.xMod.Executor.xTopExecutor;
import me.xasz.xMod.Helper.xFunc;
import me.xasz.xMod.Listener.xBlockListener;
import me.xasz.xMod.Listener.xPlayerListener;




/** xMod bukkit plugin
 *  xMod contains different types of skills to extend the game minecraft with bukkit-server
 * 
 * @author xasz
 *
 */
public class xMod extends JavaPlugin{

	/*
	 * Config vars
	 */
	
	//recipes
	public static boolean RecipeIce = true;
	public static boolean RecipeMossyCobble = true;
	public static boolean RecipeMossyBrick = true;
	public static boolean RecipeCrackedBrick = true;
	public static boolean RecipeLeather = true;
	public static boolean RecipeChisledStone = true;
	
	public static int BlocksToCountUntilPlayerProfileSave = 20;
	
	//static
	public static final String PluginDir = "plugins"+File.separator+"xMod";
	
	//repair vars
	public static  int repairTickEXP = 1;
	public static  int repairMaxLevel = 100;
	public static boolean repairIsEnabled = false;
	public static double repairMaxProcChance = 30.0f;
	public static double repairChancePerLevelFactor = repairMaxProcChance/repairMaxLevel;
	public  static int repairFactorA = 140;
	public  static int repairFactorB = 2500;
	public  static int repairFactorC = 0;
	
	//enchantment vars
	public static  int enchantmentTickEXP = 1;
	public static  int enchantmentMaxLevel = 100;
	public static boolean enchantmentIsEnabled = false;
	public static double enchantmentMaxProcChance = 30.0f;
	public static double enchantmentChancePerLevelFactor = enchantmentMaxProcChance/enchantmentMaxLevel;
	public  static int enchantmentFactorA = 140;
	public  static int enchantmentFactorB = 2500;
	public  static int enchantmentFactorC = 0;
	
	//pickaxe vars
	public static  int pickaxeTickEXP = 1;
	public static  int pickaxeMaxLevel = 100;
	public static double pickaxeMaxDoubleDropChance = 30.0f;
	public static double pickaxeDoubleDropFactor = pickaxeMaxDoubleDropChance/pickaxeMaxLevel;
	public static boolean pickaxeIsEnabled = false;
	public static double pickaxeDoubleDropPerLevelFactor = 1;
	public  static int pickaxeFactorA = 140;
	public  static int pickaxeFactorB = 2500;
	public  static int pickaxeFactorC = 0;
	
	//pickaxe vars for xp drops
	public static double pickaxeCoalTickMultiplier = 1;
	public static double pickaxeObsidianTickMultiplier = 1;
	public static double pickaxeIronTickMultiplier = 1;
	public static double pickaxeQuartzTickMultiplier = 1;
	public static double pickaxeGoldTickMultiplier = 1;
	public static double pickaxeLapisTickMultiplier = 1;
	public static double pickaxeDiamondTickMultiplier = 1;
	public static double pickaxeGlowstoneTickMultiplier = 1;
	public static double pickaxeNetherbrickTickMultiplier = 1;
	public static double pickaxeNetherrackTickMultiplier = 1;
	public static double pickaxeSandstoneTickMultiplier = 1;
	public static double pickaxeRedStoneTickMultiplier = 1;
	

	
	//hoe vars
	public static  int hoeTickEXP = 1;
	public static  int hoeMaxLevel = 100;
	public static double hoeMaxDoubleDropChance = 30.0f;
	public static double hoeDoubleDropFactor = hoeMaxDoubleDropChance/hoeMaxLevel;
	public static boolean hoeIsEnabled = false;
	public static double hoeDoubleDropPerLevelFactor = 1;
	public  static int hoeFactorA = 140;
	public  static int hoeFactorB = 2500;
	public  static int hoeFactorC = 0;	
	public static double hoeWheatTickMultiplier = 1.f;
	public static double hoeMelonTickMultiplier = 1.f;
	public static double hoePumpkinTickMultiplier = 1.f;

	//axe vars
	public static  int axeTickEXP = 1;
	public static  int axeMaxLevel = 100;
	public static double axeCriticalChancePerLevel = 1;
	public static double axeCriticalHitMultiplicator = 1;
	public static double axeMaxDoubleDropChance = 30.0f;
	public static double axeMaxCritChance = 30.0f;
	public static double axeDoubleDropPerLevelFactor = axeMaxDoubleDropChance/axeMaxLevel;
	public static boolean axeIsEnabled = false;
	public  static int axeFactorA = 140;
	public  static int axeFactorB = 2500;
	public  static int axeFactorC = 0;
	
	//shovel vars
	public static  int shovelTickEXP = 1;
	public static  int shovelMaxLevel = 100;
	public static boolean shovelIsEnabled = false;
	public static double shovelMaxDoubleDropChance = 30.0f;
	public static double shovelDoubleDropPerLevelFactor = shovelMaxDoubleDropChance/shovelMaxLevel;
	public static int swordTickEXPblock = 1;
	public  static int shovelFactorA = 140;
	public  static int shovelFactorB = 2500;
	public  static int shovelFactorC = 0;
	public static double shovelClayTickMultiplier = 1;
	

	//shear vars
	public static  int shearTickEXP = 1;
	public static int shearSheepTickEXP = 1;
	public static  int shearMaxLevel = 100;
	public static boolean shearIsEnabled = false;
	public static double shearInstantGrowChancePerLevelFactor = 1;
	public static double shearMaxDoubleDropChance = 30.0f;
	public static double shearMaxInstantSheepGrowChance = 30.0f;
	public static double shearDoubleDropPerLevelFactor = shearMaxDoubleDropChance/shearMaxLevel;
	public  static int shearFactorA = 140;
	public  static int shearFactorB = 2500;
	public  static int shearFactorC = 0;

	//stuff
	public  static boolean DEBUG = false;
	
	/*
	 * Permission vars
	 */

	// Permissions - Config
	public static final String permConfig = "xMod.config";
	public static final String permConfigReload = "xMod.config.reload";
	public static final String permConfigSave = "xMod.config.save";
	
	// Permissions - Skills
	public static final String permSkillAxe = "xMod.skill.axe";
	public static final String permSkillPickaxe = "xMod.skill.pickaxe";
	public static final String permSkillRepair = "xMod.skill.repair";
	public static final String permSkillShear = "xMod.skill.shear";
	public static final String permSkillShovel = "xMod.skill.shovel";
	public static final String permSkillHoe = "xMod.skill.hoe";
	public static final String permSkillEnchantment = "xMod.skill.repair";
	
	// Msgs
	public static final String msgNoPermission = "You do not have the permission to do that!";
	public static final String msgCmdNoPermission = "You do not have the permission to use that command!";
	
	public static final String msgCmdMissingParams = "You have not provided enough parameters!";
	
	public static final String msgCmdConfigReloadSuccess ="Configuration sucessfull reloaded from disk.";
	public static final String msgCmdConfigSaveSuccess ="Configuration successfully saved to disk.";


	public static String  mysqlUsername = "user";
	public static String  mysqlPassword = "password";
	public static String  mysqlDB = "database";
	public static String  mysqlServer = "server";
	public static int mysqlPort = 11;
	
	/*
	 * local class vars
	 */
	public xBlockWatcher blockWatcher = null;
	public xConnector Connector;
	private static xPlayerListener playerListener = null;
	private static xBlockListener blockListener = null;
	public PluginLogger logger = null;
	
	private PluginManager pm = null;
	ConfigConnector config = null;
	
	public static Main instance = null;

	
	@Override
	public void onEnable() {
		
	    // Get the instance of PluginLogger
	    logger = (PluginLogger) this.getLogger();
	    
		config = new ConfigConnector(this);
		config.load();
		
		PluginDescriptionFile pdfFile = getDescription();
		System.out.println("["+pdfFile.getName()+"] Version: " + pdfFile.getVersion() + " aktiviert");
		
		Connector = new xMysqlConnector(this);
		if(!Connector.canConnect()){
			System.out.println("["+pdfFile.getName()+"] Could not connect to database. Plugin will do suicide");
			onDisable();
			return;
		}
		
		playerListener = new xPlayerListener(this);
		blockListener = new xBlockListener(this);
		
		playerListener.loadCurrentPlayers();
		try{
			blockWatcher = new xBlockWatcher(this);
		}catch(Exception e){
			System.out.println("[xMod] Logblock couldn't loaded. Plugin suicide");
			onDisable();
			return;
		}
		
		
		
		pm = getServer().getPluginManager();
		
		//register events for playerlistener
		pm.registerEvents(playerListener, this);
		//register events for blocklistener
		pm.registerEvents(blockListener, this);
		//register events for entitiyListener

		//register events for commandExecutor
		this.getCommand("xtop").setExecutor(new xTopExecutor(this));
		this.getCommand("xstats").setExecutor(new xStatsExecutor(this));
		this.getCommand("xconfig").setExecutor(new xConfigExecutor(this));
		
		//adding custom crafting recipes
		if(RecipeMossyCobble)xFunc.addRecipeeMossyCobblestone(this);
		if(RecipeMossyBrick)xFunc.addRecipeMossyBrickStone(this);
		if(RecipeCrackedBrick)xFunc.addRecipeCrackedBrickStone(this);
		if(RecipeIce)xFunc.addRecipeIceSnowBlock(this);
		if(RecipeLeather)xFunc.addLeatherRecipe(this);
		if(RecipeChisledStone)xFunc.addChisledStone(this);
	}	
	
	/* (non-Javadoc)
	 * @see org.bukkit.plugin.Plugin#onDisable()
	 * Should be called from bukkit, if the plugin is disabled.
	 * Will call all Connectors, to save there data and close their connections
	 */
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		try{
			System.out.println("["+pdfFile.getName()+"] Try to save Playerprofile");
			playerListener.savePlayersAndClear();		
			System.out.println("["+pdfFile.getName()+"] Profiles saved.");
		}catch(Exception ex)
		{
			System.out.println("["+pdfFile.getName()+"] Profiles could not saved.");
		}
		//Connector
		try{
			Connector.disconnect();
			System.out.println("["+pdfFile.getName()+"] Database disconnected");	
		}
		catch(Exception ex){
			System.out.println("["+pdfFile.getName()+"] Database could not be disconnected.");	
		}
		
		System.out.println("["+pdfFile.getName()+"] Version: " + pdfFile.getVersion() + " deactivated");
	}
	
	/* (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 * 
	
	public void reloadConfig (){
		//Load Config
		if(config != null)config.load();	
	} */
	
	/** should be self-explaining
	 * @param player
	 * @return
	 */
	public static xPlayerProfile getPlayerProfile(Player player){
		return playerListener.getPlayerProfile(player);
	}
	public xPlayerListener getPlayerListener(){
		return playerListener;
	}
	public xConnector getConnector(){
		return Connector;
	}

}
