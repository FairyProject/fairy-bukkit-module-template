package dev.fairy.example;

import io.fairyproject.plugin.Plugin;

public class ExamplePlugin extends Plugin {
    @Override
    public void onPluginEnable() {
        System.out.println("Successfully enabled!");
    }
}