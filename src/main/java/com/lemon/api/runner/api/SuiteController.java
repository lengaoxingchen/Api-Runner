package com.lemon.api.runner.api;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lemon.api.runner.pojo.CaseListVO;
import com.lemon.api.runner.pojo.CaseOnlineRunResult;
import com.lemon.api.runner.pojo.Result;
import com.lemon.api.runner.pojo.ResultMsg;
import com.lemon.api.runner.pojo.Suite;
import com.lemon.api.runner.pojo.TestReport;
import com.lemon.api.runner.service.ISuiteService;
import com.lemon.api.runner.service.ITestReportService;

/**
 * <p>
 * InnoDB free: 6144 kB 前端控制器
 * </p>
 *
 * @author nickjiang
 * @since 2019-08-28
 */
@RestController
@RequestMapping("/suite")
public class SuiteController {
	@Autowired
	private ISuiteService suiteService;
	@Autowired
	private ITestReportService testReportService;
	@RequestMapping("/add")
	public Result add(Suite suite){
		Result result = null;
		try {
			suiteService.save(suite);
			result = new Result("1","success");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = new Result(ResultMsg.SERVER_GO_WRONG.getStatus(), ResultMsg.SERVER_GO_WRONG.getMsg());
		}
		return result;
	}
	
	@RequestMapping("/run")
	public Result run(String suiteId){
		Result result = null;
		try {
			List<TestReport> caseRunResults = suiteService.run(suiteId);
			result = new Result("1",caseRunResults,"success");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = new Result(ResultMsg.SERVER_GO_WRONG.getStatus(), ResultMsg.SERVER_GO_WRONG.getMsg());
		}
		return result;
	}
	
	@RequestMapping("/findCaseRunResult")
	public Result findCaseRunResult(String caseId){
		Result result = null;
		try {
			TestReport testReport = suiteService.findCaseRunResult(caseId);
			result = new Result("1",testReport,"success");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = new Result(ResultMsg.SERVER_GO_WRONG.getStatus(), ResultMsg.SERVER_GO_WRONG.getMsg());
		}
		return result;
	}
	
	
	@RequestMapping("/toCaseList")
	public ModelAndView toCaseList(String suiteId,String projectId){
		ModelAndView modelAndView = new ModelAndView();
		try {
			List<CaseListVO> caseListVOs = suiteService.findCasesUnderSuite(suiteId);
			modelAndView.addObject("caseListVOs",caseListVOs);
			modelAndView.addObject("suiteId", suiteId);
			if(caseListVOs.size()>0){
				modelAndView.addObject("apiId", caseListVOs.get(0).getApiId());
			}
			modelAndView.addObject("projectId", projectId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		modelAndView.setViewName("caseList");
		return modelAndView;
	}
	
	
	
	
}
