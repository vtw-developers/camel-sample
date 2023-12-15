package com.vtw.camelsample.reload;

import com.vtw.camelsample.yaml.YamlWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.spi.Resource;
import org.apache.camel.spi.RoutesLoader;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.support.PluginHelper;
import org.apache.camel.support.ResourceHelper;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
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


    public void addRoute(String path, String routeId) throws Exception {
        Resource resource = getResource(path);
        RoutesLoader loader = PluginHelper.getRoutesLoader(camelContext);

        SpringCamelContext springCamelContext = (SpringCamelContext) camelContext;
        if (springCamelContext.getRouteTemplateDefinition(routeId) != null) {
            springCamelContext.removeRouteTemplateDefinition(springCamelContext.getRouteTemplateDefinition(routeId));
            loader.updateRoutes(resource);
            reloadTemplatedRoute(routeId);
        } else {
            loader.loadRoutes(resource);
        }
    }

    public void reloadRoute(String path, String routeId) throws Exception {
        camelContext.getRouteController().stopRoute(routeId);
        camelContext.removeRoute(routeId);
        addRoute(path, routeId);
    }

    public void reloadTemplatedRoute(String templateId) throws Exception {
        SpringCamelContext springCamelContext = (SpringCamelContext) camelContext;
        List<RouteDefinition> routes = springCamelContext.getRouteDefinitions();

        for (RouteDefinition route : routes) {
            if (route.getRouteTemplateContext() != null) {
                String routeId = route.getRouteId();
                String templateIdProperty = route.getRouteTemplateContext().getProperty("routeTemplateId").toString();

                if (templateId.equals(templateIdProperty)) {
                    String path = "C:/apps/projects/camel-sample/src/main/resources/camel/routes/" + routeId + ".yaml";
                    reloadRoute(path, routeId);
                }
            }
        }
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
