package com.lemon.api.runner.service.impl;

import com.lemon.api.runner.pojo.CaseParamValue;
import com.lemon.api.runner.dao.CaseMapper;
import com.lemon.api.runner.dao.CaseParamValueMapper;
import com.lemon.api.runner.service.ICaseParamValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * InnoDB free: 6144 kB 服务实现类
 * </p>
 *
 * @author nickjiang
 * @since 2019-08-31
 */
@Service
public class CaseParamValueServiceImpl extends ServiceImpl<CaseParamValueMapper, CaseParamValue> implements ICaseParamValueService {
	@Autowired
	private CaseParamValueMapper caseParamValueMapper;
	@Override
	public void updateBatch(List<CaseParamValue> caseParamValues) throws Exception {
		caseParamValueMapper.updateBatch(caseParamValues);
	}
	@Override
	public void batchRemoveByApiRequestParamId(List<Integer> removedApiRequestParamIds) throws Exception {
		caseParamValueMapper.removedApiRequestParamIds(removedApiRequestParamIds);
	}

}
