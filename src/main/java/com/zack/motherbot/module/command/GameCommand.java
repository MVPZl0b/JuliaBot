package com.zack.motherbot.module.command;

import com.sun.prism.paint.Color;
import com.zack.motherbot.Main;
import com.zack.motherbot.message.MessageManager;
import com.zack.motherbot.objects.command.CommandInfo;
import com.zack.motherbot.objects.guild.GuildSettings;
import lombok.Getter;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.EmbedBuilder;

import java.util.ArrayList;
import java.util.HashMap;

public class GameCommand implements ICommand {
    @Getter
    public static GameCommand instance;
    @Getter
    public static HashMap<String, Long> games = new HashMap<>();

    public static void addGame(String game, Long id) {
        getGames().put(game.toLowerCase(), id);
    }

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
        EmbedBuilder em = new EmbedBuilder();
        em.withAuthorIcon(Main.getClient().getApplicationIconURL());
        em.withAuthorName("Zack");
        em.withTitle("List of Available Games");
        for(String game : games.keySet()) {
            em.appendField(game, "<@" + games.get(game) + ">", true);
        }
        em.withColor(Color.RED.hashCode());
        MessageManager.sendMessage(em.build(), event);
    }

    public void moduleAdd(String[] args, MessageReceivedEvent event, GuildSettings settings) {
        for (String game : games.keySet()) {
            if (event.getMessage().getContent().toLowerCase().contains(game)) {
                event.getAuthor().addRole(event.getGuild().getRoleByID(games.get(game)));
                MessageManager.sendMessage(MessageManager.getMessage("Notification.Game.Added", settings).replaceAll("%game%", game), event);
            }
        }

    }

    public void moduleRemove(String[] args, MessageReceivedEvent event, GuildSettings settings) {
        for (String game : games.keySet()) {
            if (event.getMessage().getContent().toLowerCase().contains(game)) {
                event.getAuthor().removeRole(event.getGuild().getRoleByID(games.get(game)));
                MessageManager.sendMessage(MessageManager.getMessage("Notification.Game.Remove", settings).replaceAll("%game%", game), event);
            }
        }
    }
}
