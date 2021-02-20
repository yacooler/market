package ru.vyazankin.market.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vyazankin.market.aspects.ProfilingControllers;
import ru.vyazankin.market.aspects.ProfilingMethods;

import java.util.Map;

@RestController
@RequestMapping("/adm")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final ProfilingMethods profilingMethods;
    private final ProfilingControllers profilingControllers;

    @GetMapping("/methods")
    public Map<String, Long> methodsProfiling(){
        log.info("Get methods");
        return profilingMethods.getMethodMap();

    }

    @GetMapping("/controllers")
    public Map<String, Long> controllersProfiling(){
        log.info("Get controllers");
        return profilingControllers.getControllersMap();
    }
}
