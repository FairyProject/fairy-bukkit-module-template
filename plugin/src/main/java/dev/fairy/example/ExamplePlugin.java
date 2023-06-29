package dev.fairy.example;

import io.fairyproject.Fairy;
import io.fairyproject.bukkit.library.BukkitAdventureLibraryBundle;
import io.fairyproject.library.Library;
import io.fairyproject.library.relocate.Relocation;
import io.fairyproject.log.Log;
import io.fairyproject.plugin.Plugin;

public class ExamplePlugin extends Plugin {
    private static ExamplePlugin INSTANCE;

    public static ExamplePlugin get() {
        return INSTANCE;
    }

    @Override
    public void onInitial() {
        INSTANCE = this;

        this.loadLibraries();
    }

    @Override
    public void onPluginEnable() {
        System.out.println("Successfully enabled!");
    }

    private void loadLibraries() {
        Log.info("Loading libraries... (This may take a while on first startup)");

        Fairy.getLibraryHandler().loadLibrary(Library.builder()
                .gradle("io{}fairyproject{}packetevents:api:2.0.17-SNAPSHOT")
                .version("2.0.17-20230204.083929-1", "2.0.17-SNAPSHOT")
                .repository("https://repo.imanity.dev/imanity-libraries/")
                .build(), true, Relocation.of("net{}kyori", "io.fairyproject.libs.kyori"));
        Fairy.getLibraryHandler().loadLibrary(Library.builder()
                .gradle("io{}fairyproject{}packetevents:spigot:2.0.17-SNAPSHOT")
                .version("2.0.17-20230204.083929-1", "2.0.17-SNAPSHOT")
                .repository("https://repo.imanity.dev/imanity-libraries/")
                .build(), true, Relocation.of("net{}kyori", "io.fairyproject.libs.kyori"));

        new BukkitAdventureLibraryBundle("4.11.0", "4.1.2").load(Fairy.getLibraryHandler());
    }
}