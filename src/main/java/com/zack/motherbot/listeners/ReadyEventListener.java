package com.zack.motherbot.listeners;

import com.zack.motherbot.logger.Logger;
import com.zack.motherbot.message.MessageManager;
import com.zack.motherbot.module.command.GameCommand;
import com.zack.motherbot.services.TimeManager;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;

public class ReadyEventListener {

    @EventSubscriber
    public void onReady(ReadyEvent event) {
        System.out.println("debug");
        try {
            TimeManager.getManager().init();

            //Add all the games.
            GameCommand.getInstance().addGame("CSGO", 457675287371579392L);
            GameCommand.getInstance().addGame("PUBG", 457675461686722583L);
            GameCommand.getInstance().addGame("OVERWATCH", 457687051530272769L);
            GameCommand.getInstance().addGame("FORTNITE", 457675702519595018L);

            MessageManager.reloadLangs();
            Logger.getLogger().debug("[ReadyEvent] Connection success!");
        } catch (Exception e) {
            Logger.getLogger().exception(null, "BAD!!!", e, ReadyEventListener.class);
        }


    }
}
