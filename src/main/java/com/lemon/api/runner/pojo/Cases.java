package com.lemon.api.runner.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * InnoDB free: 6144 kB
 * </p>
 *
 * @author nickjiang
 * @since 2019-08-31
 */
public class Cases implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 关联套件编号
     */
    private Integer suiteId;

    /**
     * 用例名称
     */
    private String name;

    /**
     * 创建人
     */
    private Integer createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public Integer getId() {
        return id;
    }

    public Cases setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getSuiteId() {
        return suiteId;
    }

    public Cases setSuiteId(Integer suiteId) {
        this.suiteId = suiteId;
        return this;
    }
    public String getName() {
        return name;
    }

    public Cases setName(String name) {
        this.name = name;
        return this;
    }
    public Integer getCreateUser() {
        return createUser;
    }

    public Cases setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public Cases setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    @Override
    public String toString() {
        return "Case{" +
            "id=" + id +
            ", suiteId=" + suiteId +
            ", name=" + name +
            ", createUser=" + createUser +
            ", createTime=" + createTime +
        "}";
    }
}
