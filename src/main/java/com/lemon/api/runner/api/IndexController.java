package com.lemon.api.runner.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.lemon.api.runner.pojo.ApiClassification;
import com.lemon.api.runner.pojo.CrossTabSelectMenu;
import com.lemon.api.runner.pojo.FirstSecondMenuVO;
import com.lemon.api.runner.pojo.Result;
import com.lemon.api.runner.pojo.ResultMsg;
import com.lemon.api.runner.pojo.Suite;
import com.lemon.api.runner.service.IApiClassificationSerivce;
import com.lemon.api.runner.service.IApiService;
import com.lemon.api.runner.service.ICaseService;
import com.lemon.api.runner.service.ISuiteService;
import com.lemon.api.runner.service.impl.ApiServiceImpl;


@RestController
@RequestMapping("/index")
public class IndexController {
	@Autowired
	private IApiClassificationSerivce apiClassificationService;
	@Autowired
	private IApiService apiService;
	@Autowired
	private ISuiteService suiteSerivce;
	@Autowired
	private ICaseService caseService;
	@RequestMapping("/toIndex")
	public ModelAndView toIndex(String projectId,String tab,CrossTabSelectMenu crossTabSelectMenu){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("projectId", projectId);
		//查出改项目底下的所有分类 
		try {
			if("1".equals(tab)){
				List<ApiClassification> apiClassifications = apiClassificationService.findAll(projectId);
				modelAndView.addObject("apiClassifications", apiClassifications);
				//从测试集合那边过来的请求，也要选中接口列表下的接口菜单的情况
				Map<String, String> selectedMenu = new HashMap<String,String>();
				if(crossTabSelectMenu==null)crossTabSelectMenu = new CrossTabSelectMenu();
				selectedMenu.put("first", crossTabSelectMenu.getFirst());
				selectedMenu.put("second", crossTabSelectMenu.getSecond());
				selectedMenu.put("refer", crossTabSelectMenu.getRefer());
				selectedMenu.put("apiId", crossTabSelectMenu.getApiId());
				modelAndView.addObject("selectedMenu", selectedMenu);
			}else if("2".equals(tab)){
				//加载测试集合列表数据
				Map<String, Object> columnMap = new HashMap<String, Object>();
				columnMap.put("project_id",projectId);
				List<Suite> suites = (List<Suite>) suiteSerivce.findSuiteAndReleadtedCasesBy(projectId);
				modelAndView.addObject("suites",suites);
				//将公共测试集合的编号id带到页面
				for (Suite suite : suites) {
					if(suite.getName().equals("公共测试集")){
						modelAndView.addObject("suiteId",suite.getId());
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		modelAndView.addObject("tab", tab);
		modelAndView.setViewName("index");
		return modelAndView;
	}
	@RequestMapping("findApiSelectedMenu")
	public Result findApiSelectedMenu(String apiId){
		//根据接口编号查到一级、二级菜单
		Result result = null;
		try {
			FirstSecondMenuVO menuVO = apiService.findApiSelectedMenu(apiId);
			result = new Result("1", menuVO, "查询一级二级菜单成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = new Result(ResultMsg.SERVER_GO_WRONG.getStatus(), ResultMsg.SERVER_GO_WRONG.getMsg());
		}
		return result;
	}
	
	@RequestMapping("findCaseSelectedMenu")
	public Result findCaseSelectedMenu(String caseId){
		//根据用例编号查到一级、二级菜单（测试集合名字、用例名字）
		Result result = null;
		try {
			FirstSecondMenuVO menuVO = caseService.findCaseSelectedMenu(caseId);
			result = new Result("1", menuVO, "查询一级二级菜单成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = new Result(ResultMsg.SERVER_GO_WRONG.getStatus(), ResultMsg.SERVER_GO_WRONG.getMsg());
		}
		return result;
	}
}
