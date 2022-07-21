package cn.edu.abtu.kanbansystem.service.impl;

import cn.edu.abtu.kanbansystem.bean.system.FileInfo;
import cn.edu.abtu.kanbansystem.mapper.FileInfoMapper;
import cn.edu.abtu.kanbansystem.service.IFileInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author RichardDev
 * @date 2022/5/15 14:23
 */
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements IFileInfoService {
}
