package com.lemon.api.runner.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * InnoDB free: 6144 kB
 * </p>
 *
 * @author nickjiang
 * @since 2019-09-12
 */
@Data
public class TestRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 用例编号
     */
    private Integer caseId;

    /**
     * 验证方式
     */
    private String rule;

    /**
     * 验证表达式
     */
    private String expression;

    /**
     * 匹配规则
     */
    private String operator;

    /**
     * 期望值
     */
    private String expected;

    /**
     * 创建人
     */
    private Integer createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
