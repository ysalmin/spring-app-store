package com.shoppingcart.controllers;

import com.shoppingcart.dao.entity.Application;
import com.shoppingcart.service.ShopService;
import javassist.tools.rmi.ObjectNotFoundException;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * Created by ysalmin on 22.07.2014.
 * Controller for admin panel.
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private ShopService shopService;

    @RequestMapping(method=RequestMethod.GET)
    public String dashboardGet(Model model) {
        model.addAttribute("application", new Application());
        return "dashboard";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("name") String name, @RequestParam("description") String description,
                         @RequestParam("file") MultipartFile file, HttpServletRequest request)
                         throws ZipException, IOException, ObjectNotFoundException {
        String path = request.getServletContext().getRealPath("/");

        if(shopService.getAppByName(name) != null
                || !shopService.addApp(name, file, path, new Date(), description)) {
            return "redirect:upload/fail";
        }
        return "redirect:upload/success";
    }

    @RequestMapping(value = "/upload/success", method=RequestMethod.GET)
    public String uploadSuccessGet(Model model) {
        return "upload-success";
    }

    @RequestMapping(value = "/upload/fail", method=RequestMethod.GET)
    public String uploadFailGet(Model model) {
        model.addAttribute("title", "Application Upload Failed");
        model.addAttribute("errorMsg", "error.msg.wrong.archive.content");
        return "error";
    }

    @RequestMapping(value = "/login", method=RequestMethod.GET)
    public String loginGet() {
        return "login";
    }

    @RequestMapping(value = "/login/error", method=RequestMethod.GET)
    public String loginErrorGet(Model model) {
        model.addAttribute("title", "Login failed");
        model.addAttribute("errorMsg", "error.msg.wrong.credentials");
        return "error";
    }
}
