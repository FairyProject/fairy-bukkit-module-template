package dev.fairy.example;

import io.fairyproject.FairyLaunch;
import io.fairyproject.plugin.Plugin;

@FairyLaunch
public class ExamplePlugin extends Plugin {

    @Override
    public void onInitial() {
    }

    @Override
    public void onPluginEnable() {
        System.out.println("Loaded Example Plugin");
    }

}