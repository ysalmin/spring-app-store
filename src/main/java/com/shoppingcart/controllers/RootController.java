package com.shoppingcart.controllers;

import com.shoppingcart.dao.entity.Application;
import com.shoppingcart.dao.entity.Category;
import com.shoppingcart.service.ShopService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by ysalmin on 24.07.2014.
 * Controller for main page and app details page.
 */
@Controller
@RequestMapping("/")
public class RootController {
    private static final int MOST_POPULAR_APPS_NUM = 5;

    @Autowired
    private ShopService shopService;

    @Cacheable("popularApps")
    @RequestMapping(method = RequestMethod.GET)
    public String homePageGet(Model model) throws ObjectNotFoundException {
        List<Application> applications = shopService.getAllApps();
        List<Application> mostPopularApps = shopService.getMostPopularApps(MOST_POPULAR_APPS_NUM);
        List<Category> categories = shopService.getAllCategories();

        model.addAttribute("applications", applications);
        model.addAttribute("mostPopularApps", mostPopularApps);
        model.addAttribute("categories", categories);

        return "index";
    }

    @Cacheable("popularApps")
    @RequestMapping(value="category/{category}", method=RequestMethod.GET)
    public String categoryGet(@PathVariable String category, Model model) throws ObjectNotFoundException {
        List<Application> applications = shopService.getAppsByCategory(new Category(category));
        List<Category> categories = shopService.getAllCategories();
        List<Application> mostPopularApps = shopService.getMostPopularApps(MOST_POPULAR_APPS_NUM);

        model.addAttribute("mostPopularApps", mostPopularApps);
        model.addAttribute("applications", applications);
        model.addAttribute("categories", categories);

        return "index";
    }

    @Cacheable("popularApps")
    @RequestMapping(value="details/{appName}", method=RequestMethod.GET)
    public String detailsGet(@PathVariable String appName, Model model) throws ObjectNotFoundException {
        Application application = shopService.getAppByName(appName);
        List<Application> mostPopularApps = shopService.getMostPopularApps(MOST_POPULAR_APPS_NUM);

        model.addAttribute("application", application);
        model.addAttribute("mostPopularApps", mostPopularApps);

        return "details";
    }

    @RequestMapping(value="download/{appName}", method=RequestMethod.GET)
    public String downloadGet(@PathVariable String appName, Model model) throws ObjectNotFoundException {
        Application application = shopService.getAppByName(appName);
        shopService.incrementDownloadsCountByName(application.getName());
        model.addAttribute("application", application);

        return "redirect:" + application.getFileUrl();
    }
}