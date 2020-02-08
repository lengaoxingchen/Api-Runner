package com.lemon.api.runner.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.api.runner.pojo.CaseListVO;
import com.lemon.api.runner.pojo.CaseOnlineRunResult;
import com.lemon.api.runner.pojo.Suite;
import com.lemon.api.runner.pojo.TestReport;

/**
 * <p>
 * InnoDB free: 6144 kB 服务类
 * </p>
 *
 * @author nickjiang
 * @since 2019-08-28
 */
public interface ISuiteService extends IService<Suite> {

	List<Suite> findSuiteUnderProjectByApiId(String apiId) throws Exception;

	List<Suite> findSuiteAndReleadtedCasesBy(String projectId) throws Exception;

	List<CaseListVO> findCasesUnderSuite(String suiteId) throws Exception;

	List<TestReport> run(String suiteId) throws Exception;

	TestReport findCaseRunResult(String caseId) throws Exception;

}
