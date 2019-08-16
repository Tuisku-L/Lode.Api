package tech.v2c.minecraft.plugins.jsonApi.tools;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class YamlUtils {
    private final static DumperOptions OPTIONS = new DumperOptions();

    static {
        OPTIONS.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
    }

    public static void SetValue(File dest, String key, Object value) throws IOException {
        Yaml yaml = new Yaml(OPTIONS);
        Map<String, Object> dataMap = (Map<String, Object>) yaml.load(new FileReader(dest));
        if (null == dataMap) {
            dataMap = new LinkedHashMap<>();
        }
        dataMap.put(key, value);
        yaml.dump(dataMap, new FileWriter(dest));
    }

    public static Object GetValue(File source, String key) throws IOException {
        Yaml yaml = new Yaml(OPTIONS);
        Map<String, Object> dataMap = (Map<String, Object>) yaml.load(new FileReader(source));
        return dataMap.get(key);
    }
}
