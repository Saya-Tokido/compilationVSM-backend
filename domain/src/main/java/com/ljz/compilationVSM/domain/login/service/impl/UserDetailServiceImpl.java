//package com.ljz.compilationVSM.domain.login.service.impl;
//
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.ljz.compilationVSM.common.exception.BizException;
//import com.ljz.compilationVSM.common.dto.LoginUserDTO;
//import com.ljz.compilationVSM.infrastructure.po.UserPO;
//import com.ljz.compilationVSM.infrastructure.repository.UserRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Slf4j
//@Service
//public class UserDetailServiceImpl  implements UserDetailsService{
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException,BizException {
//        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(UserPO::getUserName,userName);
//        UserPO user = userRepository.getOne(queryWrapper);
//        if(Optional.ofNullable(user).isEmpty()) {
//            log.info("login failed");
//            throw new BizException(1001, "userName or password error!");
//        }
////        List<String> perms=userMapper.selectPermsById(user.getId());
//        return new LoginUserDTO(user,null);
//    }
//}
