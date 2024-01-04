package com.ljz.compilationVSM.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljz.compilationVSM.entity.Log;

public interface LogService extends IService<Log> {
    public void saveLog(Log log);
}
