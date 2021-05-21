package me.matamor.generalapi.api.modules;

import me.matamor.minesoundapi.modules.exception.ModuleException;
import org.bukkit.plugin.PluginAwareness;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.InputStream;
import java.io.Reader;
import java.util.Map;

public class ModuleDescription {

    private static final ThreadLocal<Yaml> YAML = new ThreadLocal<Yaml>() {
        @Override
        protected Yaml initialValue() {
            return new Yaml(new SafeConstructor() {
                {
                    yamlConstructors.put(null, new AbstractConstruct() {
                        @Override
                        public Object construct(final Node node) {
                            if (!node.getTag().startsWith("!@")) {
                                // Unknown tag - will fail
                                return SafeConstructor.undefinedConstructor.construct(node);
                            }
                            // Unknown awareness - provide a graceful substitution
                            return new PluginAwareness() {
                                @Override
                                public String toString() {
                                    return node.toString();
                                }
                            };
                        }
                    });
                    for (final PluginAwareness.Flags flag : PluginAwareness.Flags.values()) {
                        yamlConstructors.put(new Tag("!@" + flag.name()), new AbstractConstruct() {
                            @Override
                            public PluginAwareness.Flags construct(final Node node) {
                                return flag;
                            }
                        });
                    }
                }
            });
        }
    };

    private String mainClass;
    private String name;
    private String version;

    public ModuleDescription(String name, String version, String mainClass) {
        this.name = name;
        this.version = version;
        this.mainClass = mainClass;
    }

    public ModuleDescription(final InputStream stream) {
        loadMap(asMap(YAML.get().load(stream)));
    }

    public ModuleDescription(final Reader reader) {
        loadMap(asMap(YAML.get().load(reader)));
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getMainClass() {
        return mainClass;
    }

    private void loadMap(Map<?, ?> map) {
        try {
            name = map.get("name").toString();

            if (!name.matches("^[A-Za-z0-9 _.-]+$")) {
                throw new ModuleException("name '" + name + "' contains invalid characters.");
            }

            name = name.replace(' ', '_');
        } catch (NullPointerException e) {
            throw new ModuleException("name is not defined", e);
        } catch (ClassCastException e) {
            throw new ModuleException("name is of wrong type", e);
        }

        try {
            version = map.get("version").toString();
        } catch (NullPointerException e) {
            throw new ModuleException("version is not defined", e);
        } catch (ClassCastException e) {
            throw new ModuleException("version is of wrong type", e);
        }

        try {
            mainClass = map.get("main").toString();
            if (mainClass.startsWith("org.bukkit.")) {
                throw new ModuleException("main may not be within the org.bukkit namespace");
            }
        } catch (NullPointerException e) {
            throw new ModuleException("main is not defined", e);
        } catch (ClassCastException e) {
            throw new ModuleException("main is of wrong type", e);
        }
    }

    private Map<?,?> asMap(Object object) {
        if (object instanceof Map) {
            return (Map<?,?>) object;
        }

        throw new RuntimeException(object + " is not properly structured.");
    }
}
