package com.training.controller;


import com.training.model.MenuCategory;
import com.training.model.MenuItems;
import com.training.service.ClarivateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clarivate")
public class ClarivateController {

    @Autowired
    private ClarivateService clarivateService;

    @GetMapping("/menu")
    public List<MenuCategory> getMenuItems() {
        return clarivateService.scrapeMenu();
    }
}
