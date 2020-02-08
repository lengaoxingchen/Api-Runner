package com.lemon.api.runner.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.api.runner.pojo.CaseEditVO;
import com.lemon.api.runner.pojo.CaseListVO;
import com.lemon.api.runner.pojo.Suite;

/**
 * <p>
 * InnoDB free: 6144 kB Mapper 接口
 * </p>
 *
 * @author nickjiang
 * @since 2019-08-28
 */
public interface SuiteMapper extends BaseMapper<Suite> {

	List<Suite> findSuiteUnderProjectByApiId(String apiId) throws Exception;

	List<Suite> findSuiteAndReleadtedCasesBy(String projectId) throws Exception;

	List<CaseListVO> findCasesUnderSuite(String suiteId) throws Exception;

	List<CaseEditVO> findCaseEditVosUnderSuite(String suiteId) throws Exception;

}
