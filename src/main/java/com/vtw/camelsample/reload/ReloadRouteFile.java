package com.vtw.camelsample.reload;

import com.vtw.camelsample.yaml.YamlWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.spi.Resource;
import org.apache.camel.spi.RoutesLoader;
import org.apache.camel.support.PluginHelper;
import org.apache.camel.support.ResourceHelper;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@Component
public class ReloadRouteFile implements CamelContextAware {

    private CamelContext camelContext;

    @Override
    public CamelContext getCamelContext() {
        return camelContext;
    }

    @Override
    public void setCamelContext(CamelContext camelContext) {
        this.camelContext = camelContext;
    }


    public void addRoute(String path) throws Exception {
        Resource resource = getResource(path);
        RoutesLoader loader = PluginHelper.getRoutesLoader(camelContext);
        loader.loadRoutes(resource);
    }

    public void reloadRoute(String path) throws Exception {
        Path filePath = Path.of(path);
        String fileName = filePath.getFileName().toString();
        String routeId = fileName.split("\\.")[0];

        camelContext.getRouteController().stopRoute(routeId);
        camelContext.removeRoute(routeId);
        addRoute(path);
    }

    private Resource getResource(String path) throws Exception {
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(path);
        List data = yaml.load(inputStream);

        YamlWriter writer = new YamlWriter();
        String yamlCode = writer.write(data);

        Resource resource = ResourceHelper.fromString(path, yamlCode);
        return resource;
    }
}
