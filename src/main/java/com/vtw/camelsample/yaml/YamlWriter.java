package com.vtw.camelsample.yaml;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.StringWriter;

@Component
public class YamlWriter {

    public String write(Object data) {
        StringWriter writer = new StringWriter();
        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Yaml yaml = new Yaml(options);
        yaml.dump(data, writer);

        String str = writer.toString();
        return str;
    }
}
