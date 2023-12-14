package com.vtw.camelsample.reload.service;

import com.vtw.camelsample.reload.ReloadRouteFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReloadRouteService {

    private final ReloadRouteFile reloadRouteFile;

    public void addRoute(String path) throws Exception {
        reloadRouteFile.addRoute(path);
    }

    public void reloadRoute(String path) throws Exception {
        reloadRouteFile.reloadRoute(path);
    }
}
