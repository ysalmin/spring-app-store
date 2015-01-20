package com.shoppingcart.service;

import com.shoppingcart.dao.ShopDao;
import com.shoppingcart.dao.entity.Application;
import com.shoppingcart.dao.entity.Category;
import com.shoppingcart.helpers.StorageUtils;
import javassist.tools.rmi.ObjectNotFoundException;
import net.lingala.zip4j.exception.ZipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by ysalmin on 20.07.2014.
 */
@Service
@Transactional
public class ShopService {
    private final static Logger logger = LoggerFactory.getLogger(ShopService.class);

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private StorageUtils storageUtils;

    public boolean addApp(String name, MultipartFile file, String path, Date date, String description)
            throws IOException, ZipException, ObjectNotFoundException {
        if(!storageUtils.storeFile(file, path, date)) {
            logger.error("File storage failed.");
            return false;
        }
        Application application = storageUtils.extractFileContent(name, file, path);
        if(application == null) {
            storageUtils.cleanUpFiles(file, path);
            logger.error("File contains wrong content. File removed from server.");
            return false;
        }
        application.setTimeUploaded(date);
        application.setDescription(description);
        shopDao.save(application);
        return true;
    }

    public List<Application> getAppsByCategory(Category category) throws ObjectNotFoundException {
        return shopDao.getAppsByCategory(category);
    }

    public List<Category> getAllCategories() throws ObjectNotFoundException {
        return shopDao.getAllCategories();
    }

    public Application getAppByName(String name) throws ObjectNotFoundException {
        return shopDao.getAppByName(name);
    }

    public Integer incrementDownloadsCountByName(String name) throws ObjectNotFoundException {
        Application application = shopDao.getAppByName(name);
        application.setDownloads(application.getDownloads() + 1);
        shopDao.save(application);
        return application.getDownloads();
    }

    public List<Application> getMostPopularApps(int num) throws ObjectNotFoundException {
        return shopDao.getMostPopularApps(num);
    }

    public List<Application> getAllApps() throws ObjectNotFoundException {
        return shopDao.getAllApps();
    }
}
