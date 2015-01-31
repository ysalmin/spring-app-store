package com.shoppingcart.helpers;

import com.shoppingcart.dao.ShopDao;
import com.shoppingcart.dao.entity.Application;
import com.shoppingcart.dao.entity.Category;
import javassist.tools.rmi.ObjectNotFoundException;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by ysalmin on 21.07.2014.
 * Utils for file processing.
 */
@Component
@ImportResource("classpath:/app-conf.xml")
public class StorageUtils {
    private final Logger logger = LoggerFactory.getLogger(StorageUtils.class);

    @Autowired
    private ShopDao shopDao;

    private @Value("#{props['tmp.path']}") String tmpPath;

    private @Value("#{props['apps.path']}") String appsPath;

    private @Value("#{props['date.format']}") String dateFormat;

    private @Value("#{props['detailed.default.image.path']}") String detailedDefaultImagePath;

    private @Value("#{props['preview.default.image.path']}")  String previewDefaultImagePath;

    private @Value("#{props['preview.image.width']}") int previewImageWidth;

    private @Value("#{props['preview.image.height']}") int previewImageHeight;

    private @Value("#{props['detailed.image.width']}") int detailedImageWidth;

    private @Value("#{props['detailed.image.height']}") int detailedImageHeight;

    private @Value("#{props['txt.files.allowed']}") int txtFilesAllowed;

    private @Value("#{props['total.files.allowed']}") int totalFilesAllowed;

    private String formattedDate;

    /**
     * Initial application archive storing.
     *
     * @param file
     * @param path
     * @param date
     * @return true if file stored successfully
     * @throws IOException
     */
    public boolean storeFile(MultipartFile file, String path, Date date) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        formattedDate = simpleDateFormat.format(date);

        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            File dir = new File(getFullFileName(path, file));
            if (!dir.exists()) {
                if(!dir.mkdirs()) {
                    throw new IOException("Cant create folders");
                }
            }
            File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
            logger.info("Server File Location=" + serverFile.getAbsolutePath());
            return true;
        }
        return false;
    }

    /**
     * Extracts archive content to temp dir and process content.
     *
     * @param name
     * @param file
     * @param path
     * @return Application based on archive content
     * @throws ZipException
     * @throws IOException
     * @throws ObjectNotFoundException
     */
    public Application extractFileContent(String name, MultipartFile file, String path)
            throws ZipException, IOException, ObjectNotFoundException {
        if (!checkFileFormat(file, path)) {
            logger.error("File format is wrong.");
            return null;
        }
        String source = getFullFileName(path, file) + file.getOriginalFilename();
        File destinationFolder = new File(getFullFileName(path, file));

        if (!destinationFolder.exists()) {
            if(!destinationFolder.mkdirs()) {
                throw new IOException("Cant create folders");
            }
        }

        ZipFile zipFile = new ZipFile(source);
        zipFile.extractAll(destinationFolder.getPath());

        if (!checkAppContent(destinationFolder.getPath())) {
            logger.error("App content is wrong.");
            return null;
        }
        return extractAppContent(name, path, destinationFolder.getPath(), file);
    }

    /**
     * Cleaning of files in case of wrong archive content.
     *
     * @param file
     * @param path
     * @throws IOException
     */
    public void cleanUpFiles(MultipartFile file, String path) throws IOException {
        File folderToClean = new File(getFullFileName(path, file));
        FileUtils.cleanDirectory(folderToClean);
        if(!folderToClean.delete()){
            throw new IOException("Cant remove folder " + file.getName());
        }
    }

    private boolean checkFileFormat(MultipartFile file, String path) throws ZipException {
        String source = getFullFileName(path, file) + file.getOriginalFilename();
        ZipFile zipFile = new ZipFile(source);
        if (!zipFile.isValidZipFile()) {
            logger.error("File has wrong format.");
            return false;
        }
        return true;
    }

    private Application extractAppContent(String name, String homePath, String path, MultipartFile multipartFile)
            throws IOException, ObjectNotFoundException {
        String folderName = name.replace(' ', '_');
        String previewImageUrl = previewDefaultImagePath;
        String detailedImageUrl = detailedDefaultImagePath;

        if(!StringUtils.isNotBlank(folderName)
                || !StringUtils.isNotBlank(previewImageUrl)
                || !StringUtils.isNotBlank(detailedImageUrl)) {
            logger.error("Settings for application params are empty.");
            throw new IllegalArgumentException();
        }

        String categoryName = "";

        File appFolder = new File(path);
        File[] files = appFolder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.getName().toLowerCase().endsWith(".txt")) {
                    Properties properties = new Properties();
                    InputStream input = new FileInputStream(file);
                    try {
                        properties.load(input); //load properties from file
                    } finally {
                        input.close();
                    }
                    categoryName = properties.getProperty("package").toLowerCase();
                } else if (file.getName().toLowerCase().endsWith(".jpg")) {
                    BufferedImage bufferedImage = ImageIO.read(file);
                    int width = bufferedImage.getWidth();
                    if (width == previewImageWidth) {
                        previewImageUrl = getImagesFullPath(file, folderName);
                    } else {
                        detailedImageUrl = getImagesFullPath(file, folderName);
                    }
                }
            }
        } else {
            logger.error("No files in archive.");
            throw new IOException();
        }
        moveFilesToPermanentLocation(folderName, homePath, multipartFile);

        Application application = new Application(name, appsPath + folderName
                + "/" + multipartFile.getOriginalFilename(), previewImageUrl, detailedImageUrl);

        Category category = shopDao.getCategoryByName(categoryName);
        application.addCategory(category, categoryName);

        return application;
    }

    private void moveFilesToPermanentLocation(String name, String path, MultipartFile file) throws IOException {
        File destination = new File(path + "apps" + File.separator + name + File.separator);
        File source = new File(getFullFileName(path, file));
        FileUtils.copyDirectory(source, destination);
    }

    private boolean checkAppContent(String path) throws IOException {
        File appFolder = new File(path);
        File[] files = appFolder.listFiles();
        Short numOfTxtFiles = 0;

        if (files != null) {
            if (files.length > totalFilesAllowed) {
                logger.error("Archive contains more files than allowed in settings.");
                return false;
            }
            for (File file : files) {
                if (file.isDirectory()
                        || !file.getName().toLowerCase().endsWith(".jpg")
                        && !file.getName().toLowerCase().endsWith(".txt")
                        && !file.getName().toLowerCase().endsWith(".zip")) {
                    logger.error("Archive contains wrong files.");
                    return false;
                }
                if (file.getName().toLowerCase().endsWith(".txt")) {
                    numOfTxtFiles++;
                    if (numOfTxtFiles > txtFilesAllowed) {
                        logger.error("Num of txt files is bigger than allowed in settings.");
                        return false;
                    }
                    InputStream input = new FileInputStream(file);
                    Properties properties = new Properties();
                    try {
                        properties.load(input);
                    } finally {
                        input.close();
                    }

                    if (properties.size() != 2) {
                        logger.error("App file contains wrong amount of settings.");
                        return false;
                    }
                    if(!properties.containsKey("package") || !properties.containsKey("name")) {
                        logger.error("App file contains wrong settings.");
                        return false;
                    }
                }
                if (file.getName().toLowerCase().endsWith(".jpg")
                        && !file.getName().toLowerCase().endsWith(".txt")) {
                    BufferedImage bufferedImage = ImageIO.read(file);
                    int width = bufferedImage.getWidth();
                    int height = bufferedImage.getHeight();
                    if (!(width == previewImageWidth && height == previewImageHeight ||
                            width == detailedImageWidth && height == detailedImageHeight)) {
                        logger.error("Images in archive have wrong size.");
                        return false;
                    }
                }
            }
        } else {
            logger.error("Archive don't contain files.");
            return false;
        }
        return true;
    }

    private String getImagesFullPath(File file, String folderName) {
        return appsPath + folderName + "/" + file.getName();
    }

    private String getFullFileName(String path, MultipartFile file) {
        return path + tmpPath + File.separator + file.getOriginalFilename()
                + "-" + formattedDate + File.separator;
    }
}