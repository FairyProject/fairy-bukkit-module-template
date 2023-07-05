package dev.fairy.example.module;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.fairy.example.ExamplePlugin;
import io.fairyproject.Fairy;
import io.fairyproject.container.ContainerContext;
import io.fairyproject.container.PostInitialize;
import io.fairyproject.container.Service;
import io.fairyproject.container.node.ContainerNode;
import io.fairyproject.container.node.loader.ContainerNodeLoader;
import io.fairyproject.container.node.scanner.ContainerNodeClassScanner;
import io.fairyproject.util.CC;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

@Service
public class ModuleManager {

    private static final Gson GSON = new Gson();
    private final ContainerContext context;
    @Getter
    private final List<Module> modules;
    private final Path path;

    public ModuleManager(ContainerContext context) {
        this.context = context;
        this.modules = new ArrayList<>();
        this.path = ExamplePlugin.get().getDataFolder().resolve("modules");
    }

    @PostInitialize
    public void onPostInitialize() throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
            return;
        }
        Files.newDirectoryStream(path, "*.jar").forEach(path -> {
            Module module;

            try {
                module = this.loadPath(path);
            } catch (Exception e) {
                e.printStackTrace();
                Bukkit.getConsoleSender().sendMessage(CC.RED + "[Fairy] Failed to load module from jar " + path + ". (is it out-dated?)");
                return;
            }

            if (module == null)
                return;

            this.modules.add(module);
            Bukkit.getConsoleSender().sendMessage(CC.YELLOW + "[Fairy] Loaded module " + CC.WHITE + module.getName() + CC.YELLOW + ".");
        });
    }

    private Module loadPath(Path path) throws IOException {
        Fairy.getPlatform().getClassloader().addPath(path);

        JsonObject jsonObject;
        try (JarFile jarFile = new JarFile(path.toFile())) {
            ZipEntry entry = jarFile.getEntry("module.json");
            if (entry == null) {
                Bukkit.getConsoleSender().sendMessage(CC.RED + "[Fairy] Jar " + path.getFileName() + " does not have a module.json file!");
                return null;
            }

            try (InputStreamReader reader = new InputStreamReader(jarFile.getInputStream(entry))) {
                jsonObject = GSON.fromJson(reader, JsonObject.class);
            }
        }

        Module module = new Module(
                jsonObject.get("name").getAsString(),
                jsonObject.get("description").getAsString()
        );

        String name = getFileName(path.getFileName().toString());
        ContainerNode node = ContainerNode.create(ExamplePlugin.get().getName() + ":" + name);
        ContainerNodeClassScanner classScanner = new ContainerNodeClassScanner(context, ExamplePlugin.get().getName() + ":" + name, node);
        classScanner.getClassLoaders().add(ExamplePlugin.get().getPluginClassLoader());
        classScanner.getClassPaths().add("*");
        classScanner.getUrls().add(path.toUri().toURL());
        classScanner.scan();

        new ContainerNodeLoader(context, node).load();

        return module;
    }

    private String getFileName(String fileName) {
        if (fileName == null) {
            return null;
        }
        int extensionPos = fileName.lastIndexOf('.');
        int lastUnixPos = fileName.lastIndexOf('/');
        int lastWindowsPos = fileName.lastIndexOf('\\');
        int lastSeparator = Math.max(lastUnixPos, lastWindowsPos);
        int index = lastSeparator > extensionPos ? -1 : extensionPos;
        if (index == -1) {
            return fileName;
        } else {
            return fileName.substring(0, index);
        }
    }

}
