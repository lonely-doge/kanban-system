package cn.edu.abtu.kanbansystem.controller;

import cn.edu.abtu.kanbansystem.bean.constant.HttpCode;
import cn.edu.abtu.kanbansystem.bean.constant.HttpMsg;
import cn.edu.abtu.kanbansystem.bean.entity.User;
import cn.edu.abtu.kanbansystem.bean.model.JsonResult;
import cn.edu.abtu.kanbansystem.bean.system.FileInfo;
import cn.edu.abtu.kanbansystem.enums.FileType;
import cn.edu.abtu.kanbansystem.exception.BaseException;
import cn.edu.abtu.kanbansystem.service.IFileInfoService;
import cn.edu.abtu.kanbansystem.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author Richard
 * @date 2022/3/11 9:35
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private IFileInfoService fileInfoService;

    @Autowired
    private IUserService userService;

    private final String FILE_PATH = "D:/kanban-system/file/";

    @PostConstruct
    public void init() {
        File root = new File("D:/kanban-system/");
        if (!root.exists()) {
            boolean isSuccess = root.mkdir();
            if (!isSuccess) log.error("Project file storage create failure");
            else log.info("Project file storage create success");
        }
        File dir = new File(FILE_PATH);
        if (!dir.exists()) {
            boolean isSuccess = dir.mkdir();
            if (!isSuccess) log.error("Project file storage create failure");
            else log.info("Project file storage create success");
        }
        File images = new File(FILE_PATH + "images/");
        if (!images.exists()) {
            boolean isSuccess = images.mkdir();
            if (!isSuccess) log.error("Project file storage create failure");
            else log.info("Project file storage create success");
        }
        File attachments = new File(FILE_PATH + "attachments/");
        if (!attachments.exists()) {
            boolean isSuccess = attachments.mkdir();
            if (!isSuccess) log.error("Project file storage create failure");
            else log.info("Project file storage create success");
        }
        log.info("Project file storage is exist");
    }


    @GetMapping(value = "/exhibit/{imgId}",
            produces = {
                    MediaType.IMAGE_PNG_VALUE,
                    MediaType.IMAGE_JPEG_VALUE,
            })
    public byte[] exhibit(@PathVariable("imgId") String imgId) {
        try {
            File file = new File(FILE_PATH + "images/" + imgId);
            if (!file.exists()) throw new BaseException(HttpCode.IMAGE_NOT_EXIST, HttpMsg.IMAGE_NOT_EXIST);
            else {
                FileInputStream fis = new FileInputStream(file);
                return fis.readAllBytes();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BaseException(HttpCode.FAILURE, e.getMessage());
        }
    }

    @PostMapping(value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('sys:file:manage')")
    public JsonResult<Object> upload(@RequestPart MultipartFile file,
                                     @RequestParam("type") String type,
                                     @RequestParam("userId") String userId,
                                     @RequestParam("boardId") String boardId,
                                     @RequestParam("teamId") String teamId) {
        InputStream is = null;
        OutputStream os = null;
        Boolean hasType = FileType.hasType(type);
        if (!hasType) throw new BaseException(HttpCode.FILE_UPLOAD_FAILURE, HttpMsg.FILE_UPLOAD_FAILURE);
        try {
            if (type.equals(FileType.AVATAR.getFlag())) {
                File avatar = new File(FILE_PATH + "images/" + userId + ".png");
                is = file.getInputStream();
                BufferedImage bufferedImage = ImageIO.read(is);
                ImageIO.write(bufferedImage, "png", avatar);
            } else if (type.equals(FileType.BACKGROUND.getFlag())) {
                File background = new File(FILE_PATH + "images/" + userId + "-bg.png");
                is = file.getInputStream();
                BufferedImage bufferedImage = ImageIO.read(is);
                ImageIO.write(bufferedImage, "png", background);
            } else if (type.equals(FileType.ATTACHMENTS.getFlag())) {
                File background = new File(FILE_PATH + "attachments/");
                if (!background.exists()) {
                    boolean mkdir = background.mkdir();
                    if (!mkdir) throw new BaseException(HttpCode.FILE_UPLOAD_FAILURE, HttpMsg.FILE_UPLOAD_FAILURE);
                }
                FileInfo fileInfo = new FileInfo(file.getOriginalFilename(), boardId, teamId);
                boolean isSave = fileInfoService.save(fileInfo);
                if (!isSave) throw new BaseException(HttpCode.FILE_UPLOAD_FAILURE, HttpMsg.FILE_UPLOAD_FAILURE);
                String suffix = file.getOriginalFilename().split("\\.")[1];
                is = file.getInputStream();
                os = new FileOutputStream(FILE_PATH + "/attachments/" + fileInfo.getId() + "." + suffix);
                byte[] buffer = new byte[1024];
                while (is.read(buffer) != -1) {
                    os.write(buffer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(HttpCode.FILE_UPLOAD_FAILURE, HttpMsg.FILE_UPLOAD_FAILURE);
        } finally {
            try {
                if (os != null)
                    os.close();
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new JsonResult<>(HttpCode.SUCCESS, HttpMsg.SUCCESS_MSG);
    }

}
