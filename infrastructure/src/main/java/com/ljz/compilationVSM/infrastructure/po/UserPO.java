package com.ljz.compilationVSM.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author ljz
 * @since 2024-12-02 15:12:10
 */
@Getter
@Setter
@TableName("t_user")
public class UserPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    @TableField("user_name")
    private String userName;

    @TableField("password")
    private String password;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("is_delete")
    private Boolean isDelete;

    public UserPO(Long id,String userName,String password){
        this.id=id;
        this.userName=userName;
        this.password=password;
    }
}
