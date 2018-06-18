package com.zack.motherbot.objects.bot;

import java.util.Properties;

public enum BotSettings {
    SQL_HOST, SQL_USER, SQL_PASSWORD,
    SQL_DB, SQL_PORT, SQL_PREFIX, TOKEN, LOG_FOLDER, LANG_PATH, PREFIX;

    private String val;

    BotSettings() {
    }

    public static void init(Properties properties) {
        for (BotSettings s : values()) {
            s.set(properties.getProperty(s.name()));
        }
    }

        public String get() {
            return val;
    }

    public void set(String val) {
        this.val = val;
    }
}
