package com.vtw.camelsample.reload.service;

import com.vtw.camelsample.reload.ReloadRouteFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@RequiredArgsConstructor
@Service
public class ReloadRouteService {

    private final ReloadRouteFile reloadRouteFile;

    public void addRoute(String path) throws Exception {
        String routeId = getRouteId(path);
        reloadRouteFile.addRoute(path, routeId);
    }

    public void reloadRoute(String path) throws Exception {
        String routeId = getRouteId(path);
        reloadRouteFile.reloadRoute(path, routeId);
    }

    public String getRouteId(String path) {
        Path filePath = Path.of(path);
        String fileName = filePath.getFileName().toString();
        String routeId = fileName.split("\\.")[0];
        return routeId;
    }
}
