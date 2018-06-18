package com.zack.motherbot.module.command;

import com.zack.motherbot.message.MessageManager;
import com.zack.motherbot.objects.command.CommandInfo;
import com.zack.motherbot.objects.guild.GuildSettings;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.ArrayList;

public class GameCommand implements ICommand {

    @Override
    public String getCommand() {
        return "game";
    }

    @Override
    public ArrayList<String> getAliases() {
        return new ArrayList<>();
    }

    @Override
    public CommandInfo getCommandInfo() {
        CommandInfo ci = new CommandInfo("game");
        ci.setDescription("Recieve roles based on games you play.");
        ci.setExample("!game <function> <value>");
        ci.getSubCommands().put("list", "Shows a list of games currently available");
        ci.getSubCommands().put("add", "Adds a role based on the commands you play");
        ci.getSubCommands().put("remove", "Remove a role.");
        return ci;
    }

    @Override
    public Boolean issueCommand(String[] args, MessageReceivedEvent event, GuildSettings settings) {
        if (event.getAuthor().isBot()) return false;

        if (args.length < 1) {
            MessageManager.sendMessage(MessageManager.getMessage("Notification.Args.Few", settings), event);
        } else {
            switch (args[0].toLowerCase()) {
                case "list":
                    moduleList(event, settings);
                    break;
                case "add":
                    moduleAdd(args, event, settings);
                    break;
                case "remove":
                    moduleRemove(args, event, settings);
                    break;
                default:
                    MessageManager.sendMessage(MessageManager.getMessage("Notification.Args.Invalid", settings), event);
                    break;
            }
        }
        return null;
    }

    public void moduleList(MessageReceivedEvent event, GuildSettings settings) {

    }

    public void moduleAdd(String[] args, MessageReceivedEvent event, GuildSettings settings) {

    }

    public void moduleRemove(String[] args, MessageReceivedEvent event, GuildSettings settings) {

    }
}
