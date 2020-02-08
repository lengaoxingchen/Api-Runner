package com.lemon.api.runner.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.api.runner.dao.SuiteMapper;
import com.lemon.api.runner.pojo.CaseEditVO;
import com.lemon.api.runner.pojo.CaseListVO;
import com.lemon.api.runner.pojo.CaseOnlineRunResult;
import com.lemon.api.runner.pojo.Suite;
import com.lemon.api.runner.pojo.TestReport;
import com.lemon.api.runner.pojo.TestRule;
import com.lemon.api.runner.service.ICaseService;
import com.lemon.api.runner.service.ISuiteService;
import com.lemon.api.runner.service.ITestReportService;
import com.lemon.api.runner.service.ITestRuleService;

/**
 * <p>
 * InnoDB free: 6144 kB 服务实现类
 * </p>
 *
 * @author nickjiang
 * @since 2019-08-28
 */
@Service
public class SuiteServiceImpl extends ServiceImpl<SuiteMapper, Suite> implements ISuiteService {
	@Autowired
	private SuiteMapper suiteMapper;
	
	@Autowired
	private ICaseService caseService;
	@Autowired
	private ITestRuleService testRuleService;
	@Autowired
	private ITestReportService testReportService;
	@Override
	public List<Suite> findSuiteUnderProjectByApiId(String apiId) throws Exception {
		return suiteMapper.findSuiteUnderProjectByApiId(apiId);
	}
	@Override
	public List<Suite> findSuiteAndReleadtedCasesBy(String projectId) throws Exception {
		return suiteMapper.findSuiteAndReleadtedCasesBy(projectId);
	}
	@Override
	public List<CaseListVO> findCasesUnderSuite(String suiteId) throws Exception {
		// TODO Auto-generated method stub
		return suiteMapper.findCasesUnderSuite(suiteId);
	}
	@Override
	public List<TestReport> run(String suiteId) throws Exception {
		List<TestReport> testReports = new ArrayList<TestReport>();
		//找到该套件下的所有case
		List<CaseEditVO> caseEditVOs = suiteMapper.findCaseEditVosUnderSuite(suiteId);
		if(caseEditVOs!=null&&caseEditVOs.size()>0){
			//找到该套件下所有用例对应的校验规则
			List<TestRule> testRules = testRuleService.findTestRulesOfCaseUnderCertainSuite(suiteId);
			//绑定校验规则到对象
			attachTestRules(caseEditVOs,testRules);
			//循环执行case
			for (CaseEditVO caseEditVO : caseEditVOs) {
				caseService.seperateParams(caseEditVO);
				TestReport testReport = caseService.runAndGetReport(caseEditVO);
				testReports.add(testReport);
			}
			
		}
		//批量插入测试过程中的数据
		testReportService.saveBatch(testReports);
		return testReports;
		
	}
	private void attachTestRules(List<CaseEditVO> caseEditVOs, List<TestRule> testRules) {
		for (TestRule testRule : testRules) {
			for (CaseEditVO caseEditVO : caseEditVOs) {
				if(caseEditVO.getId().equals(testRule.getCaseId()+"")){
					caseEditVO.getTestRules().add(testRule);
				}
			}
		}
		
	}
	@Override
	public TestReport findCaseRunResult(String caseId) throws Exception {
		QueryWrapper<TestReport> queryWrapper = new QueryWrapper<TestReport>();
		queryWrapper.eq("case_id", caseId);
		TestReport testReport = testReportService.getOne(queryWrapper);
		return testReport;
	}
	

}
