package cn.edu.abtu.kanbansystem.service.impl;

import cn.edu.abtu.kanbansystem.bean.entity.Stage;
import cn.edu.abtu.kanbansystem.mapper.StageMapper;
import cn.edu.abtu.kanbansystem.service.IStageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Richard
 * @date 2022/2/22 17:20
 */
@Service
public class  StageServiceImpl extends ServiceImpl<StageMapper, Stage> implements IStageService {
}
