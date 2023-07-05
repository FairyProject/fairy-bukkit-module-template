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

    private void loadLibraries() {
        Log.info("Loading libraries... (This may take a while on first startup)");

        this.loadDatabaseLibrary();
        this.loadPacketEventsLibrary();
        this.loadConfigurateLibrary();

        new BukkitAdventureLibraryBundle("4.11.0", "4.1.2").load(Fairy.getLibraryHandler());
    }

    private void loadDatabaseLibrary() {
        Fairy.getLibraryHandler().loadLibrary(Library.builder()
                .gradle("org.mongodb:mongo-java-driver:3.12.11")
                .build(), true);
    }

    private void loadPacketEventsLibrary() {
        Fairy.getLibraryHandler().loadLibrary(Library.builder()
                .groupId("com.github.retrooper.packetevents")
                .artifactId("api")
                .version("2.0-ec36a5d1d8-1")
                .repository("https://jitpack.io/")
                .build(), true
        );

        Fairy.getLibraryHandler().loadLibrary(Library.builder()
                .groupId("com.github.retrooper.packetevents")
                .artifactId("spigot")
                .version("2.0-ec36a5d1d8-1")
                .repository("https://jitpack.io/")
                .build(), true
        );
    }

    private void loadConfigurateLibrary() {
        Fairy.getLibraryHandler().loadLibrary(Library.builder()
                .gradle("org{}spongepowered:configurate-core:4.1.2")
                .build(), true, Relocation.of("org{}spongepowered{}configurate", "dev.ghast.kgenerators.libs.configurate"));
        Fairy.getLibraryHandler().loadLibrary(Library.builder()
                .gradle("org{}spongepowered:configurate-yaml:4.1.2")
                .build(), true, Relocation.of("org{}spongepowered{}configurate", "dev.ghast.kgenerators.libs.configurate"));
    }
}