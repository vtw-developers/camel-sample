package com.vtw.camelsample.shell;

import com.vtw.camelsample.reload.service.ReloadRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@Slf4j
@RequiredArgsConstructor
@ShellComponent
public class ReloadRouteCommand {

    private final ReloadRouteService reloadRouteService;

    @ShellMethod(key = "add route")
    public String addRoute(@ShellOption String path) {
        try {
            reloadRouteService.addRoute(path);
            return "Route [" + path + "] is added successfully.";
        } catch (Exception e) {
            log.error("[Error] Route cannot be added.", e);
            return "[Error] Route cannot be added. message: " + e.getMessage();
        }
    }

    @ShellMethod(key = "reload route")
    public String reloadRoute(@ShellOption String path) {
        try {
            reloadRouteService.reloadRoute(path);
            return "Route [" + path + "] is reloaded successfully.";
        } catch (Exception e) {
            log.error("[Error] Route cannot be reloaded.", e);
            return "[Error] Route cannot be reloaded. message: " + e.getMessage();
        }
    }
}
