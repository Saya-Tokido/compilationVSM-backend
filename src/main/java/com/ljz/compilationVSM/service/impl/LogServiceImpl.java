package com.ljz.compilationVSM.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljz.compilationVSM.dao.LogMapper;
import com.ljz.compilationVSM.entity.Log;
import com.ljz.compilationVSM.service.LogService;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {
    @Override
    public void saveLog(Log log) {

    }
}
