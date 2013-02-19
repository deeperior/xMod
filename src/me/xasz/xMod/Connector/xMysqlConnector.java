package me.xasz.xMod.Connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import org.bukkit.entity.Player;

import me.xasz.xMod.xMod;
import me.xasz.xMod.xPlayerProfile;
import me.xasz.xMod.Helper.xPlayer;
import me.xasz.xMod.Skills.xSkillType;

public class xMysqlConnector extends xConnector {
	Logger log = Logger.getLogger("Minecraft");
	String mysql = "";
	Connection con = null;
	boolean status = false;
	Statement state = null;
	public xMysqlConnector(xMod instance) {
		super(instance);
		createdb();
	}
	
	public void createdb(){
		connect();
			updateQuery(
					"CREATE TABLE IF NOT EXISTS player"+
					"(" +
					"id INTEGER auto_increment PRIMARY KEY," +
					"name char(50),"+
					"repairxp INTEGER, "+
					"pickaxexp INTEGER, "+
					"axexp INTEGER, "+
					"shovelxp INTEGER, "+
					"shearxp INTEGER,"+
					"hoexp INTEGER,"+
					"enchantmentxp INTEGER"+
					");"
				);
			updateQuery(
					"CREATE TABLE IF NOT EXISTS blockwatcher"+
					"(" +
					"id  BIGINT auto_increment PRIMARY KEY," +
					"world char(100),"+
					"x INTEGER, "+
					"y INTEGER, "+
					"z INTEGER "+
					");"
				);		
			disconnect();
	}
	
	public boolean connect(){
		if(status == true){
			disconnect();
		}
		if(xMod.DEBUG == true)
			System.out.println("Connecting to Mysql");
		try {
			con = DriverManager.getConnection( "jdbc:mysql://"+xMod.mysqlServer+":"+xMod.mysqlPort+"/"+xMod.mysqlDB,xMod.mysqlUsername,xMod.mysqlPassword);
			con.setAutoCommit(false);
			state = con.createStatement();
			status = true;
		} catch (SQLException e) {
			writeError(e.getMessage(), true);
		}
		return status;
	}
	
	@Override
	public void disconnect(){		
		if(xMod.DEBUG == true)
			System.out.println("Disconnecting Mysql");
		try {
			con.commit();
			con.setAutoCommit(true);
			state.close();
			this.con.close();
			status = false;
		} catch (SQLException e) {			
			status = false;
		}
	}
	/** 
	 * error output
	 * @param toWrite
	 * @param severe
	 */
	public void writeError(String toWrite, boolean severe) {
		if (toWrite != null) {
			if (severe) {
				this.log.severe("[xMod]" + "[Mysql] " + toWrite);
			} else {
				this.log.warning("[xMod]" + "[Mysql] " + toWrite);
			}
		}
	}
	/**
	 * 
	 * @return true = connected || false = not connected
	 */
	public boolean checkConnection(){
		return status;
	}
	
	@Override
	public boolean savePlayerProfile(xPlayerProfile profile) {
		connect();
		boolean success = false;
		ResultSet res;
		try {
			if(profile.isChanged()){
				res = selectQuery("SELECT * FROM player WHERE name = '"+profile.GetPlayerName()+"';");
				//getcount
				res.last();
				int count = res.getRow();
				res.beforeFirst();
				
				if(count >= 1){
					res.first();
					//player is in db
					try {
						String query = "UPDATE player "+ 
								"SET "+
								"repairxp="+profile.getRepairXP()+", "+
								"pickaxexp="+profile.getPickaxeXP()+", "+
								"axexp="+profile.getAxeXP()+", "+
								"shovelxp="+profile.getShovelXP()+", "+
								"shearxp="+profile.getShearXP()+", "+
								"hoexp="+profile.getHoeXP()+", "+
								"enchantmentxp="+profile.getEnchantmentXP()+" "+
								"WHERE name='"+profile.GetPlayerName()+"'";
						updateQuery(query);
						con.commit();
						if(xMod.DEBUG)
							System.out.println("[xMod] "+profile.GetPlayerName()+"'s Profile saved");
						success = true;
					} finally{
						try{
							con.commit();				
						}catch(Exception e){}
						try{
							res.close();				
						}catch(Exception e){}
					}
				}else{
					//player is not in db
					try {
						String query = "INSERT INTO player (name, repairxp, pickaxexp, axexp, shovelxp, shearxp, hoexp, enchantmentxp) "+
								"VALUES ('"+
								profile.GetPlayerName()+"', "+
								profile.getRepairXP()+", "+
								profile.getPickaxeXP()+", "+
								profile.getAxeXP()+", "+
								profile.getShovelXP()+", "+
								profile.getShearXP()+", "+
								profile.getHoeXP()+", "+
								profile.getEnchantmentXP()+
								")";
						updateQuery(query);
						if(xMod.DEBUG)
							System.out.println("[xMod] "+profile.GetPlayerName()+"'s Profile saved");
						success = true;
					} finally{
						try{
							con.commit();				
						}catch(Exception e){}
						try{
							res.close();				
						}catch(Exception e){}
					}
				}
			}

		} catch (SQLException e) {
			System.out.println("[xMod] "+ profile.GetPlayerName() + "'s Profile wasn't saved");	
			e.printStackTrace();
		}

		disconnect();
		return success;
	}

	@Override
	public xPlayerProfile loadPlayerProfile(Player player) {
		connect();
		xPlayerProfile preturn = new xPlayerProfile(x, player);
		preturn.refreshAxeCurrentLevelMaxXP();
		preturn.refreshPickaxeCurrentLevelMaxXP();
		preturn.refreshRepairCurrentLevelMaxXP();
		preturn.refreshShearCurrentLevelMaxXP();
		preturn.refreshShovelCurrentLevelMaxXP();
		preturn.refreshHoeCurrentLevelMaxXP();
		preturn.refreshEnchantmentCurrentLevelMaxXP();
		
		ResultSet res = null;
		try {
			res = selectQuery("Select * from player Where name = '"+player.getDisplayName()+"'");
			
			//getcount
			res.last();
			int count = res.getRow();
			res.beforeFirst();
			
			if(count >= 1){
				//player exists	
				res.first();
				preturn.setRepairXP(res.getInt("repairxp"));
				preturn.setPickaxeXP(res.getInt("pickaxexp"));
				preturn.setAxeXP(res.getInt("axexp"));
				preturn.setShovelXP(res.getInt("shovelxp"));
				preturn.setShearXP(res.getInt("shearxp"));
				preturn.setHoeXP(res.getInt("hoexp"));
				preturn.setEnchantmentXP(res.getInt("enchantmentxp"));
				xPlayer.sendSuccessMessage(player, "Player " + res.getString("name") +" Profile loaded");
			}else{
				//player does not exist... probably :D
				throw new Exception();
			}
			
		} catch (Exception e) {
			xPlayer.sendSuccessMessage(player, "New xMod Profile generated. The Profile will saved when you leave the game");

		} finally{
			try{
				con.commit();				
			}catch(Exception e){}
			try{
				res.close();				
			}catch(Exception e){}
		}

		disconnect();
		return preturn;
	}
	@Override
	public xPlayerProfile[] getAllFakeProfiles(){
		connect();
		xPlayerProfile[] fake = null;
		ResultSet profiles = null;
		try {
			profiles = selectQuery("SELECT * FROM player");
			int count = 0;
			try {
				profiles.last();
				count = profiles.getRow();
				profiles.beforeFirst();
			}
			catch(Exception ex) {
				count =  0;
			}
			fake = new xPlayerProfile[count];
			
			int i = 0;
			while(profiles.next())
			{
					fake[i] = new xPlayerProfile(x, profiles.getString("name"));
					fake[i].setRepairXP(profiles.getInt("repairxp"));
					fake[i].setPickaxeXP(profiles.getInt("pickaxexp"));
					fake[i].setAxeXP(profiles.getInt("axexp"));
					fake[i].setShovelXP(profiles.getInt("shovelxp"));
					fake[i].setShearXP(profiles.getInt("shearxp"));
					fake[i].setHoeXP(profiles.getInt("hoexp"));
					fake[i].setEnchantmentXP(profiles.getInt("enchantmentxp"));
				i++;
			}		
		} catch (SQLException ex) {			
			writeError(ex.getMessage(), true);
			try {
				profiles.close();
			} catch (SQLException e) {			}	
		}
		disconnect();
		return fake;
	}
	@Override
	public xPlayerProfile getFakeProfileByName(String name){
		connect();
		ResultSet pro = null;
		Player player = x.getServer().getPlayer(name);
		xPlayerProfile fake = null;
		if(player != null){
			fake= xMod.getPlayerProfile(player);
		}else{
			try{
				pro = selectQuery("SELECT * FROM player WHERE name='"+name+"'");
				if(pro.next()){
					fake = new xPlayerProfile(x, pro.getString("name"));
					fake.setRepairXP(pro.getInt("repairxp"));
					fake.setPickaxeXP(pro.getInt("pickaxexp"));
					fake.setAxeXP(pro.getInt("axexp"));
					fake.setShovelXP(pro.getInt("shovelxp"));
					fake.setShearXP(pro.getInt("shearxp"));
					fake.setHoeXP(pro.getInt("hoexp"));
					fake.setEnchantmentXP(pro.getInt("enchantmentxp"));
				}
				pro.close();
			} catch (SQLException ex) {
				writeError(ex.getMessage(), true);
			} finally{
				try{
					pro.close();			
				}catch(Exception e){}
			}
		}
		disconnect();
		return fake;
	}
	@Override
	public xPlayerProfile[] getAllFakeProfilesSortedBySkill(xSkillType skill){
		connect();
		xPlayerProfile[] fake = null;
		String queryskill = "none";
		if(skill == xSkillType.AXE){
			queryskill = "axexp";
		}else if(skill == xSkillType.PICKAXE){
			queryskill = "pickaxexp";
			
		}else if(skill == xSkillType.REPAIR){
			queryskill = "repairxp";
			
		}else if(skill == xSkillType.SHEAR){
			queryskill = "shearxp";
			
		}else if(skill == xSkillType.SHOVEL){
			queryskill = "shovelxp";
			
		}else if(skill == xSkillType.HOE){
			queryskill = "hoexp";
			
		}
		else if(skill == xSkillType.ENCHANTMENT){
			queryskill = "enchantmentxp";
			
		}
		else if(skill == xSkillType.NONE){
			return null;
		}
			ResultSet profiles = selectQuery("SELECT * FROM player ORDER BY " + queryskill + " ASC");
			int count = 0;
			try {
				profiles.last();
				count = profiles.getRow();
				profiles.beforeFirst();
			}
			catch(Exception ex) {
				count =  0;
			}
			fake = new xPlayerProfile[count];
			int i = 0;
			try {
				while(profiles.next())
				{
					fake[i] = new xPlayerProfile(x, profiles.getString("name"));
					fake[i].setRepairXP(profiles.getInt("repairxp"));
					fake[i].setPickaxeXP(profiles.getInt("pickaxexp"));
					fake[i].setAxeXP(profiles.getInt("axexp"));
					fake[i].setShovelXP(profiles.getInt("shovelxp"));
					fake[i].setShearXP(profiles.getInt("shearxp"));
					fake[i].setHoeXP(profiles.getInt("hoexp"));
					fake[i].setEnchantmentXP(profiles.getInt("enchantmentxp"));
					i++;
				}
			} catch (SQLException e) {
				fake = null;
				try{
					profiles.close();			
				}catch(Exception e1){}
			}
			disconnect();
		return fake;
	}
	@Override
	public boolean watchBlock(String world, int x, int y, int z) {
		connect();
		return updateQuery("INSERT INTO blockwatcher (world,x,y,z) VALUES ('"+world+"',"+x+","+y+","+z+");");
	}
	/*@Override
	public boolean isBlockWatched(String world, int x, int y, int z) {
		connect();
			boolean result = false;
			ResultSet res = selectQuery("Select * from blockwatcher Where world = '"+world+"' and x = "+x+" and y = "+y+" and z = "+z+";");
			if (res != null){
				try {		
					//getcount
					System.out.println("1");
					res.last();
					System.out.println("2");
					int count = res.getRow();
					System.out.println("3");
					res.beforeFirst();
					System.out.println("4");
					if(count >= 1){
					 result = true;
					}	
					res.close();
				}catch (SQLException e) {
					//Im Zweifelsfall wird der Block als überwacht eingestuft um missbrauch zu verhindern. 
					e.printStackTrace();
					result = false;
				}
			}
			disconnect();
		return result;
	}*/
	@Override
	public boolean isBlockWatched(String world, int x, int y, int z) {
		connect();
		if(!this.status){
			return false;
		}
		ResultSet res = null;
		boolean success = false;
		try {
		    state = con.createStatement();
			res = state.executeQuery("Select * from blockwatcher Where world = '"+world+"' and x = "+x+" and y = "+y+" and z = "+z+";");
			//getcount
			res.last();
			int count = res.getRow();
			res.beforeFirst();
			
			if(count >= 1){
			 success = true;
			}
			
		} catch (Exception e) {

		} finally{
			try{
				con.commit();				
			}catch(Exception e){}
			try{
				res.close();				
			}catch(Exception e){}
			try{
				state.close();
			}catch(Exception e){}
		}
		disconnect();
		return success;
	}
	@Override
	public void disBlock(String world, int x, int y, int z) {
		connect();
		updateQuery("DELETE from blockwatcher Where world = '"+world+"' and x = "+x+" and y = "+y+" and z = "+z+";");
		disconnect();
	}
	
	private boolean updateQuery(String query){
		if(xMod.DEBUG == true)
			System.out.println("updateQuery: "+query);
		boolean result = false;
		Statement state = null;
		try {
			state = con.createStatement();
			state.executeUpdate(query);
			result = true;
			con.commit();
		} catch (SQLException e) {
			result = false;
		}		
		return result;
	}
	private ResultSet selectQuery(String query){
		if(xMod.DEBUG == true)
			System.out.println("selectQuery: "+query);
		ResultSet result = null;
		try {
			state = con.createStatement();
			result = state.executeQuery(query);
			con.commit();
		} catch (SQLException e) {
		}		
		return result;
	}
	
	@Override
	public boolean canConnect() {
		boolean result = false;
		result = this.connect();
		this.disconnect();
		return result;
	}

}
