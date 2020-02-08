package com.lemon.api.runner.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.lemon.api.runner.pojo.ApiRunVO;
import com.lemon.api.runner.pojo.CaseEditVO;
import com.lemon.api.runner.pojo.CaseOnlineRunResult;
import com.lemon.api.runner.pojo.Cases;
import com.lemon.api.runner.pojo.Result;
import com.lemon.api.runner.pojo.ResultMsg;
import com.lemon.api.runner.service.ICaseService;

/**
 * <p>
 * InnoDB free: 6144 kB 前端控制器
 * </p>
 *
 * @author nickjiang
 * @since 2019-08-31
 */
@RestController
@RequestMapping("/case")
public class CaseController {
	@Autowired
	private ICaseService caseService;
	@RequestMapping("/add")
	public Result add(Cases cs,ApiRunVO apiRunVO){
		Result result = null;
		try {
			caseService.add(cs,apiRunVO);
			result = new Result("1", "创建用例成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = new Result(ResultMsg.SERVER_GO_WRONG.getStatus(),
					ResultMsg.SERVER_GO_WRONG.getMsg());
		}
		return result;
	}
	
	@RequestMapping("/toCaseEdit")
	public ModelAndView toCaseEdit(String caseId,String projectId,String apiId){
		ModelAndView modelAndView = new ModelAndView();
		try {
			//找到CaseEditVO数据，带到页面
			CaseEditVO caseEditVO = caseService.findCaseEditVO(caseId);
			modelAndView.addObject("caseEditVO", caseEditVO);
			modelAndView.addObject("apiId", apiId);
			modelAndView.addObject("projectId", projectId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		modelAndView.setViewName("caseEdit");
		return modelAndView;
	}
	
	
	@RequestMapping("/run")
	public Result run(CaseEditVO caseEditVO){
		Result result = null;
		try {
			//找到CaseEditVO数据，带到页面
			CaseOnlineRunResult caseOnlineRunResult = caseService.run(caseEditVO);
			result = new Result("1",caseOnlineRunResult,"ok");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = new Result(ResultMsg.SERVER_GO_WRONG.getStatus(), ResultMsg.SERVER_GO_WRONG.getMsg());
		}
		return result;
	}
	
	@RequestMapping("/update")
	public Result update(CaseEditVO caseEditVO){
		Result result = null;
		try {
			//找到CaseEditVO数据，带到页面
			caseService.update(caseEditVO);
			result = new Result("1","用例更新成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = new Result(ResultMsg.SERVER_GO_WRONG.getStatus(), ResultMsg.SERVER_GO_WRONG.getMsg());
		}
		return result;
	}
}
