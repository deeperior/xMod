package me.xasz.xMod.Executor;

import me.xasz.xMod.xMod;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class xModDbExecutor  implements CommandExecutor  {
	
	private final xMod x;
	public xModDbExecutor (final xMod instance){
		x = instance;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,	String[] args) {
		boolean success = false;
		try{
			//Checking if sender is player object
			if(sender instanceof Player ){
				sender.sendMessage("Command is only supported for Players");
				return false;
			}
			x.getPlayerListener().saveOnlinePlayers();
			success = true;
			
		}
		catch(Exception ex){
			x.getServer().broadcastMessage("Error occured on commmand: "+cmd.getName());
		}

		
		return success;
	}
}
