package me.xasz.xMod.Connector;

import me.xasz.xMod.xMod;
import me.xasz.xMod.xPlayerProfile;
import me.xasz.xMod.Skills.xSkillType;

import org.bukkit.entity.Player;


/** 
 * Abstract class for connectors
 * Connecters where made for saving information..
 * @author xasz & BlackJoker89
 *
 */
public abstract class xConnector {

	 protected final xMod x;
	 public xConnector (final xMod instance){
			x = instance;
		}
	
	 
	 /**
	  * saves the player profile to database
	  * @param profile
	  * @return true = successful save || false = not successful save
	  */
	 abstract public boolean savePlayerProfile(xPlayerProfile profile);
	
	 
	 /**
	  * loads player profile from database
	  * @param player
	  * @return loaded profile
	  */
	 abstract public xPlayerProfile loadPlayerProfile(Player player);
	
	 
	 /** 
	 * connect to database
	 * create table if not exists
	 * @return true = connected || false = not connected
	 */
	 abstract public boolean connect ();
	
	 
	 /** 
	 * close connection to database
	 * 
	 */
	 abstract public void disconnect ();
	
	 
	 /**
	  * function to get a specific player profile to request a skill level of a player
	  * @param name
	  * @return requested profile
	  */
	 abstract public xPlayerProfile getFakeProfileByName(String name);
	
	 
	 /**
	  * function to get all player profiles
	  * @return
	  */
	 abstract public xPlayerProfile[] getAllFakeProfiles();
	
	 
	 /**
	  * function to get all player profiles sorted by skill
	  * @param skill
	  * @return
	  */
	 abstract public xPlayerProfile[] getAllFakeProfilesSortedBySkill(xSkillType skill);

	 
	 abstract public boolean watchBlock(String world, int x, int y, int z);
	 abstract public boolean isBlockWatched(String world, int x, int y, int z);
	 abstract public void disBlock(String world, int x, int y, int z);


	public abstract boolean canConnect();
}
