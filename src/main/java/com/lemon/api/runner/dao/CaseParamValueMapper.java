package com.lemon.api.runner.dao;

import com.lemon.api.runner.pojo.CaseParamValue;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * InnoDB free: 6144 kB Mapper 接口
 * </p>
 *
 * @author nickjiang
 * @since 2019-08-31
 */
public interface CaseParamValueMapper extends BaseMapper<CaseParamValue> {

	void updateBatch(List<CaseParamValue> caseParamValues) throws Exception;

	void removedApiRequestParamIds(List<Integer> removedApiRequestParamIds) throws Exception;

}
