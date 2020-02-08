package com.lemon.api.runner.service;

import com.lemon.api.runner.pojo.CaseParamValue;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * InnoDB free: 6144 kB 服务类
 * </p>
 *
 * @author nickjiang
 * @since 2019-08-31
 */
public interface ICaseParamValueService extends IService<CaseParamValue> {

	void updateBatch(List<CaseParamValue> caseParamValues) throws Exception;

	void batchRemoveByApiRequestParamId(List<Integer> removedApiRequestParamIds) throws Exception;

}
