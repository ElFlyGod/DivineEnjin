package com.enjin.bungee.util;

import com.enjin.bungee.EnjinMinecraftPlugin;
import com.enjin.bungee.util.io.EnjinLogFormatter;
import com.enjin.core.Enjin;
import com.enjin.core.util.EnjinLogger;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Log implements EnjinLogger {
    @Getter
    private final static Logger logger = Logger.getLogger(EnjinMinecraftPlugin.class.getName());

    public Log() {
        Enjin.getLogger().debug("Initializing internal logger");
        logger.setLevel(Level.FINEST);

        File logs = new File(EnjinMinecraftPlugin.getInstance().getDataFolder(), "logs");
        File log = new File(logs, "enjin.log");
        if (log.exists()) {
            if (log.length() > 1024 * 1024 * 5) {
                log.delete();
            }
        } else {
            logs.mkdirs();

            try {
                log.createNewFile();
            } catch (IOException e) {
				Enjin.getLogger().catching(e);
            }
        }

        EnjinLogFormatter formatter = new EnjinLogFormatter();
        FileHandler handler = null;
        try {
            handler = new FileHandler(EnjinMinecraftPlugin.getInstance().getDataFolder().getAbsolutePath() + File.separator + "logs" + File.separator + "enjin.log", true);
        } catch (IOException e) {
			Enjin.getLogger().catching(e);
        }

        handler.setFormatter(formatter);
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
        Enjin.getLogger().debug("Logger initialized.");
    }

    public void info(String msg) {
        logger.info(msg);
    }

    public void fine(String msg) {
        logger.fine(msg);
    }

    public void warning(String msg) {
        logger.warning(msg);
    }

    public void debug(String msg) {
        fine("Enjin Debug: " + msg);
    }

	public void catching(Throwable e) {
		logger.log(Level.SEVERE, "EMP Error: ", e);
	}

    @Override
    public String getLastLine() {
        return "";
    }
}
