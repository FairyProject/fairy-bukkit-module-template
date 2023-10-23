package dev.fairy.example;

import io.fairyproject.FairyLaunch;
import io.fairyproject.plugin.Plugin;

@FairyLaunch
public class ExampleModulePlugin extends Plugin {

    @Override
    public void onPluginEnable() {
        System.out.println("Loaded ExampleModulePlugin");
    }
}
