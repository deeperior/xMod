package me.xasz.xMod.Executor;

import me.xasz.xMod.xMod;
import me.xasz.xMod.Helper.xPlayer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class xConfigExecutor implements CommandExecutor {
	private final xMod x;
	public xConfigExecutor (final xMod instance){
		x = instance;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,	String[] args) {
		boolean success = false;
		Player player = null;
		try{
			//Checking if sender is player object
			if(sender instanceof Player ){
				player = (Player)sender;
			} else {
				sender.sendMessage("Command is only supported for Players");
				return false;
			}
				if (!player.hasPermission(xMod.permConfig))
				{
					xPlayer.sendErrorMessage(player, xMod.msgCmdNoPermission);
					return true;
				}
				
				xPlayer.sendDebugMessage(player, "Rcvd command: " + cmd.getName());
				if (!(args.length == 1))
				{
					xPlayer.sendErrorMessage(player, xMod.msgCmdMissingParams);
					return false;
				}
				else
				{
					xPlayer.sendDebugMessage(player, "xconfig parameter:" + args[0]);
					
					if (args[0].equalsIgnoreCase("reload"))
					{
						if (!player.hasPermission(xMod.permConfigReload))
						{
							xPlayer.sendErrorMessage(player, xMod.msgCmdNoPermission);
							return true;
						}
						
						x.reloadConfig();
						xPlayer.sendSuccessMessage(player, xMod.msgCmdConfigReloadSuccess);
						return true;
					}
					else if (args[0].equalsIgnoreCase("save"))
					{
						if (!player.hasPermission(xMod.permConfigSave))
						{
							xPlayer.sendErrorMessage(player, xMod.msgCmdNoPermission);
							return true;
						}
						
						x.saveConfig();
						xPlayer.sendSuccessMessage(player, xMod.msgCmdConfigSaveSuccess);
						return true;
					}
					else
					{
						xPlayer.sendErrorMessage(player, "Wrong parameter: " + args[0]);
						return false;
					}

				}
		}
		catch(Exception ex){
			x.getServer().broadcastMessage("Error occured on commmand:"+cmd.getName());
		}

		
		return success;
	}

}
