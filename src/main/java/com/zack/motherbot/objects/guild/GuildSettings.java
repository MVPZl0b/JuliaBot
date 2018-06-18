package com.zack.motherbot.objects.guild;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GuildSettings {
    public static GuildSettings instance;
    private final long guildID;

    private String lang;
    private String prefix;

    private long staticMessage;


    public GuildSettings(long _guildId) {
        guildID = _guildId;

        lang = "ENGLISH";
        prefix = "!";

    }

    public GuildSettings getGuildSettings(long guildId) {
        GuildSettings settings = new GuildSettings(guildId);
        settings.setLang("ENGLISH");
        settings.setPrefix("!");
        return settings;
    }
}
