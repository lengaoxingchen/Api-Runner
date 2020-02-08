package com.lemon.api.runner.dao;

import com.lemon.api.runner.pojo.CaseEditVO;
import com.lemon.api.runner.pojo.CaseParamValue;
import com.lemon.api.runner.pojo.Cases;
import com.lemon.api.runner.pojo.FirstSecondMenuVO;

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
public interface CaseMapper extends BaseMapper<Cases> {

	CaseEditVO findCaseEditVO(String caseId) throws Exception;

	FirstSecondMenuVO findCaseSelectedMenu(String caseId) throws Exception;

}
