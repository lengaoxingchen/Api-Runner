package com.lemon.api.runner.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.api.runner.dao.CaseMapper;
import com.lemon.api.runner.pojo.*;
import com.lemon.api.runner.service.IApiService;
import com.lemon.api.runner.service.ICaseParamValueService;
import com.lemon.api.runner.service.ICaseService;
import com.lemon.api.runner.service.ITestRuleService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * InnoDB free: 6144 kB 服务实现类
 * </p>
 *
 * @author nickjiang
 * @since 2019-08-31
 */
@Service
public class CaseServiceImpl extends ServiceImpl<CaseMapper, Cases> implements ICaseService {
	@Autowired
	private CaseMapper caseMapper;
	@Autowired
	private ICaseParamValueService caseParamValueService;
	@Autowired
	private IApiService apiService;
	@Autowired
	private ITestRuleService testRuleService;
	@Override
	public void add(Cases cs, ApiRunVO apiRunVO) throws Exception {
		// TODO Auto-generated method stub
		//保存用例case到case表
		caseMapper.insert(cs);
		//保存用例的测试数据到case_param_value
		List<ApiRequestParam> params = new ArrayList<ApiRequestParam>();
		params.addAll(apiRunVO.getQueryParams());
		params.addAll(apiRunVO.getBodyParams());
		params.addAll(apiRunVO.getHeaderParams());
		List<CaseParamValue> caseParamValues = new ArrayList<CaseParamValue>();
		for (ApiRequestParam apiRequestParam : params) {
			CaseParamValue caseParamValue = new CaseParamValue();
			caseParamValue.setCaseId(cs.getId());
			caseParamValue.setApiRequestParamId(apiRequestParam.getId());
			caseParamValue.setApiRequestParamValue(apiRequestParam.getValue());
			caseParamValues.add(caseParamValue);
		}
		//批量插入
		caseParamValueService.saveBatch(caseParamValues);
	}
	@Override
	public CaseEditVO findCaseEditVO(String caseId) throws Exception {
		CaseEditVO caseEditVO = caseMapper.findCaseEditVO(caseId);
		seperateParams(caseEditVO);
		//查询用例关联的校验规则
		Map<String, Object> columnMap = new HashMap<String, Object>();
		columnMap.put("case_id", caseEditVO.getId());
		List<TestRule> testRules = (List<TestRule>) testRuleService.listByMap(columnMap);
		caseEditVO.setTestRules(testRules);
		return caseEditVO;
	}
	
	public void seperateParams(CaseEditVO caseEditVO) {
		if(caseEditVO!=null){
			List<ApiRequestParam> apiRequestParams = caseEditVO.getRequestParams();
			for (ApiRequestParam apiRequestParam : apiRequestParams) {
				if("1".equals(apiRequestParam.getType()+"")){
					caseEditVO.getQueryParams().add(apiRequestParam);
				}else if("2".equals(apiRequestParam.getType()+"")){
					caseEditVO.getBodyParams().add(apiRequestParam);
				}else if("3".equals(apiRequestParam.getType()+"")){
					caseEditVO.getHeaderParams().add(apiRequestParam);
				}else if("4".equals(apiRequestParam.getType()+"")){
					caseEditVO.getBodyRawParams().add(apiRequestParam);
				}
			}
			
		}
	}
	@Override
	public CaseOnlineRunResult run(CaseEditVO caseEditVO) throws Exception {
		//准备ApiRunVO对象
		ApiRunVO apiRunVO = new ApiRunVO();
		BeanUtils.copyProperties(apiRunVO, caseEditVO);
		ApiOnlineRunResult apiOnlineRunResult = apiService.run(apiRunVO);
		CaseOnlineRunResult caseOnlineRunResult = new CaseOnlineRunResult();
		BeanUtils.copyProperties(caseOnlineRunResult, apiOnlineRunResult);
		return caseOnlineRunResult;
	}
	
	@Transactional
	@Override
	public void update(CaseEditVO caseEditVO) throws Exception {
		//1.更新case的名称
		Cases cs = new Cases();
		cs.setId(Integer.valueOf(caseEditVO.getId()));
		cs.setName(caseEditVO.getCaseName());
		caseMapper.updateById(cs);
		//2.修改参数字段的测试数据
		List<CaseParamValue> caseParamValues = new ArrayList<CaseParamValue>();
		List<ApiRequestParam> apiRequestParams = new ArrayList<ApiRequestParam>();
		apiRequestParams.addAll(caseEditVO.getQueryParams());
		apiRequestParams.addAll(caseEditVO.getBodyParams());
		apiRequestParams.addAll(caseEditVO.getHeaderParams());
		for (ApiRequestParam apiRequestParam : apiRequestParams) {
			CaseParamValue caseParamValue = new CaseParamValue();
			caseParamValue.setCaseId(Integer.valueOf(caseEditVO.getId()));
			caseParamValue.setApiRequestParamId(apiRequestParam.getId());
			caseParamValue.setApiRequestParamValue(apiRequestParam.getValue());
			caseParamValues.add(caseParamValue);
		}
		caseParamValueService.updateBatch(caseParamValues);
		//删除原关联的所有校验规则，然后重新生成并关联
		Map<String, Object> columnMap = new HashMap<String, Object>();
		columnMap.put("case_id",caseEditVO.getId());
		List<TestRule> testRules  = (List<TestRule>) testRuleService.listByMap(columnMap);
		List<Integer> dbExsitedIds = new ArrayList<Integer>();
		for (TestRule testRule : testRules) {
			dbExsitedIds.add(testRule.getId());
		}
		//取出页面的所有rule
		List<TestRule> pageTestRules = caseEditVO.getTestRules();
		for (TestRule testRule : pageTestRules) {
			//找到删除的
			if(testRule.getId()!=null&&dbExsitedIds.contains(testRule.getId())){
				dbExsitedIds.remove(testRule.getId());
			}
		}
		List<Integer> deleteIds = dbExsitedIds;
		//批量删除
		if(deleteIds.size()>0){
			testRuleService.removeByIds(deleteIds);
		}
		//批量更新或者插入
		if(pageTestRules.size()>0){
			for (TestRule testRule : pageTestRules) {
				testRule.setCaseId(Integer.valueOf(caseEditVO.getId()));
			}
			testRuleService.saveOrUpdateBatch(pageTestRules);
		}
	} 
	@Override
	public FirstSecondMenuVO findCaseSelectedMenu(String caseId) throws Exception {
		return caseMapper.findCaseSelectedMenu(caseId);
	}
	
	@Override
	public TestReport runAndGetReport(CaseEditVO caseEditVO) throws Exception {
		//取出接口信息，完成接口調用，返回數據
		String host = caseEditVO.getHost();
		String url = caseEditVO.getUrl();
		url = host+url;
		String method = caseEditVO.getMethod();
		CloseableHttpResponse httpResponse = null;
		Map<String, String> requestHeaderMap = new HashMap<String,String>();
		String bodyParamString = null;
		CloseableHttpClient httpClient = null;
		if("get".equalsIgnoreCase(method)){
			//參數拼接在url上
			List<ApiRequestParam> queryParams = caseEditVO.getQueryParams();
			List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
			for (ApiRequestParam apiRequestParam : queryParams) {
				nameValuePairs.add(new BasicNameValuePair(apiRequestParam.getName(), apiRequestParam.getValue()));
			}
			String queryParamString = URLEncodedUtils.format(nameValuePairs, "UTF-8");
			url = url+"?"+queryParamString;
			//創建HttpGet對象模擬get請求
			HttpGet httpGet = new HttpGet(url);
			//準備客戶端
			httpClient = HttpClients.createDefault();
			//设置请求头
			List<ApiRequestParam> headerParams = caseEditVO.getHeaderParams();
			for (ApiRequestParam apiRequestParam : headerParams) {
				httpGet.addHeader(new BasicHeader(apiRequestParam.getName(), apiRequestParam.getValue()));
				requestHeaderMap.put(apiRequestParam.getName(), apiRequestParam.getValue());
			}
			//發送請求，執行接口請求
			httpResponse = httpClient.execute(httpGet);
			
		}else if("post".equalsIgnoreCase(method)){
			//參數拼接在url上
			List<ApiRequestParam> queryParams = caseEditVO.getQueryParams();
			List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
			for (ApiRequestParam apiRequestParam : queryParams) {
				nameValuePairs.add(new BasicNameValuePair(apiRequestParam.getName(), apiRequestParam.getValue()));
			}
			String queryParamString = URLEncodedUtils.format(nameValuePairs, "UTF-8");
			if(StringUtils.isNotEmpty(queryParamString)){
				url = url+"?"+queryParamString;
			}
			//創建HttpPost对象来模拟post请求
			HttpPost httpPost = new HttpPost(url);
			//准备参数
			List<ApiRequestParam> bodyParams = caseEditVO.getBodyParams();
			List<ApiRequestParam> bodyRawParams = caseEditVO.getBodyRawParams();
			if(bodyParams.size()>0){
				List<BasicNameValuePair> nameValuePairs2 = new ArrayList<BasicNameValuePair>();
				for (ApiRequestParam apiRequestParam : bodyParams) {
					nameValuePairs2.add(new BasicNameValuePair(apiRequestParam.getName(), apiRequestParam.getValue()));
				}
				bodyParamString = URLEncodedUtils.format(nameValuePairs2, "UTF-8");
			}else if(bodyRawParams.size()>0){
				ApiRequestParam apiRequestParam = bodyParams.get(0);
				String value = apiRequestParam.getValue();
				bodyParamString = value;
			}
			//参数设置到请求体
			httpPost.setEntity(new StringEntity(bodyParamString, "UTF-8"));
			//设置请求头
			List<ApiRequestParam> headerParams = caseEditVO.getHeaderParams();
			for (ApiRequestParam apiRequestParam : headerParams) {
				httpPost.addHeader(new BasicHeader(apiRequestParam.getName(), apiRequestParam.getValue()));
				requestHeaderMap.put(apiRequestParam.getName(), apiRequestParam.getValue());
			}
			//执行接口调用
			httpClient = HttpClients.createDefault();
			httpResponse = httpClient.execute(httpPost);
		}
		//取出響應頭
		Header [] headers = httpResponse.getAllHeaders();
		Map<String, Object> responseHeaderMap = new HashMap<String,Object>();
		for (Header header : headers) {
			String name = header.getName();
			String value = header.getValue();
			responseHeaderMap.put(name, value);
		}
		//要写回到页面的响应头数据
		String responseHeadersString = JSONObject.toJSONString(responseHeaderMap, true);
		//要写回到页面的响应报文
		String result = EntityUtils.toString(httpResponse.getEntity());
		//json字符串--》java对象（反序列化）
		HashMap<String, Object> resultMap = JSONObject.parseObject(result, HashMap.class);
		//java对象---》json字符串（反序列化）
		String resultJsonString = JSONObject.toJSONString(resultMap, true);
		TestReport testReport = new TestReport();
		testReport.setRequestUrl(url);
		testReport.setRequestHeaders(JSONObject.toJSONString(requestHeaderMap, true));
		testReport.setRequestBody(bodyParamString);
		testReport.setResponseHeaders(responseHeadersString);
		testReport.setResponseBody(resultJsonString);
		testReport.setCaseId(Integer.valueOf(caseEditVO.getId()));
		testReport.setPassFlag(assertByTestRule(resultJsonString,caseEditVO.getTestRules()));
		if(httpClient!=null){
			httpClient.close();
		}
		return testReport;
	}
	
	private String assertByTestRule(String responseBody, List<TestRule> testRules) {
		boolean isPassed = true;
		for (TestRule testRule : testRules) {
			String rule = testRule.getRule();
			if("jsonpath".equalsIgnoreCase(rule)){
				String expression = testRule.getExpression();
				String operator = testRule.getOperator();
				String expected = testRule.getExpected();
				Object value = JSONPath.read(responseBody, expression);
				String actual = null;
				if(value instanceof Integer){
					actual = String.valueOf(value);
				}else if(value instanceof String){
					actual = value.toString();
				}
				if("=".equals(operator)){
					isPassed = actual.equals(expected);
				}else if("contains".equals(operator)){
					isPassed = actual.contains(expected);
				}
				if(!isPassed){
					return "不通过";
				}
			}
		}
		return "通过";
		
	} 

}
