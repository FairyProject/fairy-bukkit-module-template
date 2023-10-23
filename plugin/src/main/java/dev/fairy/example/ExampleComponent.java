package dev.fairy.example;

import io.fairyproject.container.InjectableComponent;
import io.fairyproject.container.PostInitialize;
import lombok.RequiredArgsConstructor;

@InjectableComponent
@RequiredArgsConstructor
public class ExampleComponent {

    private final ExamplePlugin plugin;

    @PostInitialize
    public void onPostInitialize() {
        System.out.println("Loaded ExampleComponent from " + plugin.getName());
    }

}
