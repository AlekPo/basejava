package ru.javaops.basejava;

import ru.javaops.basejava.storage.SqlStorage;
import ru.javaops.basejava.storage.Storage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    //    private static final File PROPS = new File(getHomeDir(), "config\\resumes.properties");
    private static final String PROPS = "/resumes.properties";
    private static final Config INSTANCE = new Config();

    private final String storageDir;
    //    private final String dbUrl;
//    private final String dbUser;
//    private final String dbPassword;
    private final Storage storage;

    public static Config get() {
        return INSTANCE;
    }

/*
    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = props.getProperty("storage.dir");
            dbUrl = props.getProperty("db.url");
            dbUser = props.getProperty("db.user");
            dbPassword = props.getProperty("db.password");
            storage = new SqlStorage(dbUrl, dbUser, dbPassword);
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }
*/

    private Config() {
        try (InputStream is = Config.class.getResourceAsStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
//            storageDir = new File(props.getProperty("storage.dir"));
            storageDir = props.getProperty("storage.dir");
            storage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS);
        }
    }

    public String getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }

//    public String getDbUrl() {
//        return dbUrl;
//    }
//
//    public String getDbUser() {
//        return dbUser;
//    }
//
//    public String getDbPassword() {
//        return dbPassword;
//    }
//

//    private static File getHomeDir() {
//        String strHomeDir = System.getProperty("homeDir");
//        File homeDir = new File(strHomeDir == null ? "." : strHomeDir);
//        if (!homeDir.isDirectory()) {
//            throw new IllegalStateException(homeDir.getName() + "  is not directory");
//        }
//        return homeDir;
//    }
}
