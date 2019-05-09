package my.test.mynotes;

import android.app.Application;
import android.arch.persistence.room.Room;

import my.test.mynotes.Room.AppDataBase;

public class App extends Application {

    public static App instance;

    private AppDataBase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDataBase.class, "database").allowMainThreadQueries().build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDataBase getDatabase() {
        return database;
    }
}
