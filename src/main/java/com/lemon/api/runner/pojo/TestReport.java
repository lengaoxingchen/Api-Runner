package com.lemon.api.runner.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * InnoDB free: 6144 kB
 * </p>
 *
 * @author nickjiang
 * @since 2019-09-16
 */
@Data
public class TestReport implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用例编号
     */
    private Integer caseId;

    /**
     * 接口地址
     */
    private String requestUrl;

    /**
     * 请求头
     */
    private String requestHeaders;

    /**
     * 请求体数据
     */
    private String requestBody;

    /**
     * 响应头
     */
    private String responseHeaders;

    /**
     * 响应体
     */
    private String responseBody;

    /**
     * 测试结果（通过 or 不通过）
     */
    private String passFlag;

	public TestReport(Integer id, Integer caseId, String requestUrl, String requestHeaders, String requestBody,
			String responseHeaders, String responseBody, String passFlag) {
		super();
		this.id = id;
		this.caseId = caseId;
		this.requestUrl = requestUrl;
		this.requestHeaders = requestHeaders;
		this.requestBody = requestBody;
		this.responseHeaders = responseHeaders;
		this.responseBody = responseBody;
		this.passFlag = passFlag;
	}
	public TestReport() {
		super();
	}

   
}
