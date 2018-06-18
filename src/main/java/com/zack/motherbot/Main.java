package com.zack.motherbot;

import com.zack.motherbot.logger.Logger;
import com.zack.motherbot.objects.bot.BotSettings;
import com.zack.motherbot.services.TimeManager;
import lombok.Getter;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
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
        p.load(new FileReader(new File("settings.properties")));
        BotSettings.init(p);

        Logger.getLogger().init();

        //Status Manager
        TimeManager.getManager().init();

        client = createClient(BotSettings.TOKEN.get());

        getClient().login();
        if(getClient() == null) {
            throw new NullPointerException("Failed to build! Client cannot be NULL!");
        }


    }

    private static IDiscordClient createClient(String token) {
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(token);
        try {
            return clientBuilder.build();
        } catch (DiscordException e) {
            e.printStackTrace();
        }
        return null;
    }

}
