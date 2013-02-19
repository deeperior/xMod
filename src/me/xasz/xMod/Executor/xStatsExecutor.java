
package me.xasz.xMod.Executor;

import me.xasz.xMod.xMod;
import me.xasz.xMod.xPlayerProfile;
import me.xasz.xMod.Helper.xPlayer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class xStatsExecutor implements CommandExecutor  {
	
	private final xMod x;
	public xStatsExecutor (final xMod instance){
		x = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,	String[] args) {
		boolean success = false;
		Player player = null;
		xPlayerProfile pprofile = null;
		try{
			//Checking if sender is player object
			if(sender instanceof Player ){
				player = (Player)sender;
				pprofile =  x.getPlayerListener().getPlayerProfile(player);
			} else {
				sender.sendMessage("Command is only supported for Players");
				return false;
			}
			if(args.length == 0){
				//showing own profile
				pprofile.getLevelOverview();			
			}else if (args.length == 1){
				//try to show the profile of another player
				xPlayerProfile sourceProfile = x.getConnector().getFakeProfileByName(args[0]);		
				if(sourceProfile != null){
					xPlayer.getLevelOverview(sourceProfile,pprofile);					
				}else{
					xPlayer.sendErrorMessage(player, "Player " + args[0] + " doesn't exist");
				}
			}
			success = true;
			
		}
		catch(Exception ex){
			x.getServer().broadcastMessage("Error occured on commmand: "+cmd.getName());
		}

		
		return success;
	}
}
