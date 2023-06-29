package dev.fairy.example.module;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Module {

    private final String name;
    private final String description;

}
