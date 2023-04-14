package com.example.whitecommunity.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;

@RestController
public class ImageController {
    @Autowired
    RestTemplate restTemplate;

    @CrossOrigin
    @PostMapping("/api/image")
    public String uploadImage(@RequestParam("file") MultipartFile image) throws Exception {
        if (image.getSize() > 512 * 1024) {
            return "";
        }

        String contentType = image.getContentType();
        if (contentType == null) {
            return "";
        }

        String imageSuffix = "";
        switch (contentType) {
            case "image/png":
                imageSuffix = "png";
                break;
            case "image/jpeg":
                imageSuffix = "jpg";
                break;
            default:
                return "";
        }

        String hash = DigestUtils.md5Hex(image.getBytes());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String time = dateFormat.format(new Date());

        String fileName = time + hash + "." + imageSuffix;
        File parent = new File("image");
        File file = new File(parent.getAbsolutePath(), fileName);

        if (!parent.exists()) {
            parent.mkdir();
        }
        try {
            image.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        String res = restTemplate.getForObject("http://localhost:8001/image/" + fileName, String.class);

        if ("0".equals(res)) {
            return "http://localhost:8443/api/image/" + fileName;
        }

        return "";
    }


    @CrossOrigin
    @PostMapping("/api/media")
    public String uploadMedia(@RequestParam("file") MultipartFile media) throws Exception {
        if (media.getSize() > 30*1024 * 1024) {
            System.out.println("oversize");
            return "";
        }

        String contentType = media.getContentType();
        if (contentType == null) {
            System.out.println("NullNullNull");
            return "";
        }

        String mediaSuffix = "";
        switch (contentType) {
            case "video/mp4":
                mediaSuffix = "mp4";
                break;
            case "video/AVI":
                mediaSuffix = "AVI";
                break;
            default:
                return "";
        }

        String hash = DigestUtils.md5Hex(media.getBytes());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String time = dateFormat.format(new Date());

        String fileName = time + hash + "." + mediaSuffix;
        File parent = new File("media");
        File file = new File(parent.getAbsolutePath(), fileName);

        if (!parent.exists()) {
            parent.mkdir();
        }
        try {
            media.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error");
            return "";
        }

        return "http://localhost:8443/api/media/" + fileName;
    }


}
