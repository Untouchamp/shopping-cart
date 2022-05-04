package config;

import org.ini4j.Ini;

import java.io.FileReader;
import java.io.IOException;

public class SetupConfig {

    private SetupConfig() { throw new IllegalStateException("Config class");}

    static Ini ini;

    static {
        try (FileReader fileReader = new FileReader("config.properties")) {
            ini = new Ini(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final class UISettings {
        private UISettings() { throw new IllegalStateException("Config class");}
        private static final String  sectionName         = "UI_Settings";
        public  static final String  browserType         = ini.get(sectionName, "browserType", String.class);
        public  static final boolean confHoldBrowserOpen = ini.get(sectionName, "confHoldBrowserOpen", boolean.class);
    }

    public static final class UIEndpoints {
        private UIEndpoints() { throw new IllegalStateException("Config class");}
        private static final String sectionName    = "UI_Endpoints";
        public  static final String baseUrl        = ini.get(sectionName, "baseUrl", String.class);
    }

}
