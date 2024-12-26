package com.ljz.compilationVSM.infrastructure.repository.impl;

import com.ljz.compilationVSM.infrastructure.po.LexerPDPO;
import com.ljz.compilationVSM.infrastructure.mapper.LexerPDMapper;
import com.ljz.compilationVSM.infrastructure.repository.LexerPDRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 词法分析器代码查重表,Plagiarism detection 服务实现类
 * </p>
 *
 * @author ljz
 * @since 2024-12-25 10:09:05
 */
@Service
public class LexerPDRepositoryImpl extends ServiceImpl<LexerPDMapper, LexerPDPO> implements LexerPDRepository {

}
