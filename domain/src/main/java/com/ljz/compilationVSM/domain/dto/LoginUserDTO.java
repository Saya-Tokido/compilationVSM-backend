package com.ljz.compilationVSM.domain.dto;

import com.ljz.compilationVSM.infrastructure.po.UserPO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class LoginUserDTO implements UserDetails {

    private UserPO userPO;

    private List<String> permissions;

//    @JSONField(serialize = false)
//    private List<SimpleGrantedAuthority> authorities;

    public LoginUserDTO(UserPO userPO, List<String> permissions) {
        this.userPO = userPO;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return userPO.getPassword();
    }

    @Override
    public String getUsername() {
        return userPO.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
