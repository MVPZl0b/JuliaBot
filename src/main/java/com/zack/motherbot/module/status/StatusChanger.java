package com.zack.motherbot.module.status;

import com.zack.motherbot.Main;
import lombok.Getter;
import lombok.Setter;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.StatusType;

import java.util.ArrayList;
import java.util.TimerTask;

public class StatusChanger extends TimerTask {
    private final ArrayList<String> statuses = new ArrayList<String>();
    private Integer index;

    /**
     * Creates the StatusChanger and its Statuses list.
     */
    public StatusChanger() {
        statuses.add("!game to add a game");
        statuses.add("Made by Zack#2743");
        index = 0;
    }

    @Override
    public void run() {
        String status = statuses.get(index);
        status = status.replace("%guCount%", Main.getClient().getGuilds().size() + "");
        Main.getClient().changePresence(StatusType.ONLINE, ActivityType.PLAYING, status);

        //Set new index.
        if (index + 1 >= statuses.size())
            index = 0;
        else
            index++;
    }
}
