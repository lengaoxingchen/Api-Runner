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
public class CaseParamValue implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 关联的用例编号
     */
    private Integer caseId;

    /**
     * 关联的接口参数字段编号
     */
    private Integer apiRequestParamId;

    /**
     * 给参数字段准备的测试数据
     */
    private String apiRequestParamValue;

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

    public CaseParamValue setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getCaseId() {
        return caseId;
    }

    public CaseParamValue setCaseId(Integer caseId) {
        this.caseId = caseId;
        return this;
    }
    public Integer getApiRequestParamId() {
        return apiRequestParamId;
    }

    public CaseParamValue setApiRequestParamId(Integer apiRequestParamId) {
        this.apiRequestParamId = apiRequestParamId;
        return this;
    }
    public String getApiRequestParamValue() {
        return apiRequestParamValue;
    }

    public CaseParamValue setApiRequestParamValue(String apiRequestParamValue) {
        this.apiRequestParamValue = apiRequestParamValue;
        return this;
    }
    public Integer getCreateUser() {
        return createUser;
    }

    public CaseParamValue setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public CaseParamValue setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    @Override
    public String toString() {
        return "CaseParamValue{" +
            "id=" + id +
            ", caseId=" + caseId +
            ", apiRequestParamId=" + apiRequestParamId +
            ", apiRequestParamValue=" + apiRequestParamValue +
            ", createUser=" + createUser +
            ", createTime=" + createTime +
        "}";
    }
}
