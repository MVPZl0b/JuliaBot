package com.zack.motherbot;

import com.zack.motherbot.database.DatabaseManager;
import com.zack.motherbot.listeners.ReadyEventListener;
import com.zack.motherbot.logger.Logger;
import com.zack.motherbot.message.MessageManager;
import com.zack.motherbot.module.command.CommandExecutor;
import com.zack.motherbot.module.command.GameCommand;
import com.zack.motherbot.objects.bot.BotSettings;
import com.zack.motherbot.services.TimeManager;
import lombok.Getter;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class Main {
    @Getter
    private static IDiscordClient client;

    public static void main(String[] args) throws IOException {
        //Get bot settings
        Properties p = new Properties();
        p.load(new FileReader(new File("/home/zl0bby/discordbots/Mother2/Settings/settings.properties")));
        BotSettings.init(p);

        //Initiate the logger
        Logger.getLogger().init();

        client = createClient(BotSettings.TOKEN.get());


        if(getClient() == null) {
            throw new NullPointerException("Failed to build! Client cannot be NULL!");
        }

        //Register events
        EventDispatcher dispatcher = getClient().getDispatcher();
        dispatcher.registerListener(new ReadyEventListener());

        getClient().login();

        //Connect to MySQL server
        DatabaseManager.getManager().connectToMySQL();
        DatabaseManager.getManager().createTables();

        //Register Commands
        CommandExecutor exe = CommandExecutor.getExecutor().enable();
        exe.registerCommand(new GameCommand());

        //Load language files
        MessageManager.loadLangs();
    }

    private static IDiscordClient createClient(String token) {
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(token).withRecommendedShardCount();
        try {
            return clientBuilder.build();
        } catch (DiscordException e) {
            e.printStackTrace();
        }
        return null;
    }

}
