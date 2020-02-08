package com.lemon.api.runner.service.impl;

import com.lemon.api.runner.pojo.Api;
import com.lemon.api.runner.pojo.ApiEditVO;
import com.lemon.api.runner.pojo.ApiListVO;
import com.lemon.api.runner.pojo.ApiOnlineRunResult;
import com.lemon.api.runner.pojo.ApiRequestParam;
import com.lemon.api.runner.pojo.ApiRunVO;
import com.lemon.api.runner.pojo.ApiViewVO;
import com.lemon.api.runner.pojo.CaseParamValue;
import com.lemon.api.runner.pojo.FirstSecondMenuVO;
import com.lemon.api.runner.dao.ApiMapper;
import com.lemon.api.runner.service.IApiRequestParamService;
import com.lemon.api.runner.service.IApiService;
import com.lemon.api.runner.service.ICaseParamValueService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
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

/**
 * <p>
 * InnoDB free: 6144 kB 服务实现类
 * </p>
 *
 * @author nickjiang
 * @since 2019-08-12
 */
@Service
public class ApiServiceImpl extends ServiceImpl<ApiMapper, Api> implements IApiService {
	@Autowired
	private ApiMapper apiMapper;
	@Autowired
	private IApiRequestParamService apiRequestParamService;
	@Autowired
	private ICaseParamValueService caseParamValueSerive;
	@Override
	public List<ApiListVO> showApiListByProjectId(String projectId) throws Exception {
		// TODO Auto-generated method stub
		return apiMapper.showApiListByProjectId(projectId);
	}
	@Override
	public List<ApiListVO> showApiListByApiClassification(String apiClassificationId) throws Exception {
		return apiMapper.showApiListByApiClassification(apiClassificationId);
	}
	@Override
	public FirstSecondMenuVO findApiSelectedMenu(String apiId) throws Exception {
		return apiMapper.findApiSelectedMenu(apiId);
	}
	@Override
	public ApiViewVO findApiViewVO(String apiId) throws Exception {
		ApiViewVO apiViewVO = apiMapper.findApiViewVO(apiId);
		if(apiViewVO!=null){
			List<ApiRequestParam> apiRequestParams = apiViewVO.getRequestParams();
			for (ApiRequestParam apiRequestParam : apiRequestParams) {
				if("1".equals(apiRequestParam.getType()+"")){
					apiViewVO.getQueryParams().add(apiRequestParam);
				}else if("2".equals(apiRequestParam.getType()+"")){
					apiViewVO.getBodyParams().add(apiRequestParam);
				}else if("3".equals(apiRequestParam.getType()+"")){
					apiViewVO.getHeaderParams().add(apiRequestParam);
				}else if("4".equals(apiRequestParam.getType()+"")){
					apiViewVO.getBodyRawParams().add(apiRequestParam);
				}
			}
		}
		return apiViewVO;
	}
	@Transactional
	@Override
	public void edit(ApiEditVO apiEditVO) throws Exception {
		//更新接口的基本信息
		apiMapper.updateBasicInfo(apiEditVO);
		//取出页面提交的所有参数信息
		List<ApiRequestParam> apiRequestParams = new ArrayList<ApiRequestParam>();
		apiRequestParams.addAll(apiEditVO.getQueryParams());
		apiRequestParams.addAll(apiEditVO.getBodyParams());
		apiRequestParams.addAll(apiEditVO.getHeaderParams());
		//查出当前接口原本有的参数
		Map<String, Object> columnMap = new HashMap<String,Object>();
		columnMap.put("api_id", apiEditVO.getId());
		List<ApiRequestParam> dbStoredApiRequestParams = (List<ApiRequestParam>) apiRequestParamService.listByMap(columnMap);
		List<Integer> dbStoredApiRequestParamIds = new ArrayList<Integer>();
		if(dbStoredApiRequestParamIds==null) dbStoredApiRequestParamIds = new ArrayList<Integer>();
		for (ApiRequestParam apiRequestParam : dbStoredApiRequestParams) {
			dbStoredApiRequestParamIds.add(apiRequestParam.getId());
		}
		//通过对比，找出哪些是新增的参数字段，哪些是删除的参数字段，以及哪些是修改的参数字段
		List<ApiRequestParam> addApiRequestParams = new ArrayList<ApiRequestParam>();
		List<ApiRequestParam> editApiRequestParams = new ArrayList<ApiRequestParam>();
		for (ApiRequestParam apiRequestParam : apiRequestParams) {
			//如果id为空，则意味着是页面新增的字段
			if(apiRequestParam.getId()==null){
				addApiRequestParams.add(apiRequestParam);
			}else{//id不为空，此参数字段原本就有
				editApiRequestParams.add(apiRequestParam);
				dbStoredApiRequestParamIds.remove(apiRequestParam.getId());
			}
		}
		//声明一个集合来保存页面被删的参数的id值
		List<Integer> removedApiRequestParamIds = dbStoredApiRequestParamIds;
		//执行批量插入
		if(addApiRequestParams.size()>0){
			//往参数字段表，批量插入新增的参数字段
			apiRequestParamService.saveBatch(addApiRequestParams);
			//找到对应用例，case_param_value表中将新增的参数关联上
			List<Integer> caseIds = apiMapper.findRelatedCaseByApiId(apiEditVO.getId());
			List<CaseParamValue> caseParamValues = new ArrayList<CaseParamValue>();
			for (int caseId : caseIds) {//eg:有两条用例要处理2,3
				//eg:新增了两个字段3,4
				for (ApiRequestParam apiRequestParam : addApiRequestParams) {
					CaseParamValue caseParamValue = new CaseParamValue();
					caseParamValue.setCaseId(caseId);
					caseParamValue.setApiRequestParamId(apiRequestParam.getId());
					caseParamValues.add(caseParamValue);
				}
			}
			if(caseParamValues.size()>0){
				caseParamValueSerive.saveBatch(caseParamValues);
			}
		}
		//批量删除
		if(removedApiRequestParamIds.size()>0){
			//执行删除操作
			//1.删除api_request_param相关数据
			apiRequestParamService.removeByIds(removedApiRequestParamIds);
			//2.删除case_param_value相关数据
			caseParamValueSerive.batchRemoveByApiRequestParamId(removedApiRequestParamIds);
		}
		//批量修改
		if(editApiRequestParams.size()>0){
			apiRequestParamService.updateBatchById(editApiRequestParams);
		}
	
		
	}
	@Override
	public ApiEditVO findApiEditVO(String apiId) throws Exception {
		ApiEditVO apiEditVO = apiMapper.findApiEditVO(apiId);
		if(apiEditVO!=null){
			List<ApiRequestParam> apiRequestParams = apiEditVO.getRequestParams();
			for (ApiRequestParam apiRequestParam : apiRequestParams) {
				if("1".equals(apiRequestParam.getType()+"")){
					apiEditVO.getQueryParams().add(apiRequestParam);
				}else if("2".equals(apiRequestParam.getType()+"")){
					apiEditVO.getBodyParams().add(apiRequestParam);
				}else if("3".equals(apiRequestParam.getType()+"")){
					apiEditVO.getHeaderParams().add(apiRequestParam);
				}else if("4".equals(apiRequestParam.getType()+"")){
					apiEditVO.getBodyRawParams().add(apiRequestParam);
				}
			}
		}
		return apiEditVO;
	}
	@Override
	public ApiRunVO findApiRunVO(String apiId) throws Exception {
		ApiRunVO apiRunVO = apiMapper.findApiRunVO(apiId);
		if(apiRunVO!=null){
			List<ApiRequestParam> apiRequestParams = apiRunVO.getRequestParams();
			for (ApiRequestParam apiRequestParam : apiRequestParams) {
				if("1".equals(apiRequestParam.getType()+"")){
					apiRunVO.getQueryParams().add(apiRequestParam);
				}else if("2".equals(apiRequestParam.getType()+"")){
					apiRunVO.getBodyParams().add(apiRequestParam);
				}else if("3".equals(apiRequestParam.getType()+"")){
					apiRunVO.getHeaderParams().add(apiRequestParam);
				}else if("4".equals(apiRequestParam.getType()+"")){
					apiRunVO.getBodyRawParams().add(apiRequestParam);
				}
			}
		}
		return apiRunVO;
	}
	@Override
	public ApiOnlineRunResult run(ApiRunVO apiRunVO) throws Exception {
		//取出接口信息，完成接口調用，返回數據
		String host = apiRunVO.getHost();
		String url = apiRunVO.getUrl();
		url = host+url;
		String method = apiRunVO.getMethod();
		CloseableHttpResponse httpResponse = null;
		ApiOnlineRunResult apiOnlineRunResult = new ApiOnlineRunResult();
		CloseableHttpClient httpClient = null;
		if("get".equalsIgnoreCase(method)){
			//參數拼接在url上
			List<ApiRequestParam> queryParams = apiRunVO.getQueryParams();
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
			List<ApiRequestParam> headerParams = apiRunVO.getHeaderParams();
			for (ApiRequestParam apiRequestParam : headerParams) {
				httpGet.addHeader(new BasicHeader(apiRequestParam.getName(), apiRequestParam.getValue()));
			}
			//發送請求，執行接口請求
			httpResponse = httpClient.execute(httpGet);
			
		}else if("post".equalsIgnoreCase(method)){
			//參數拼接在url上
			List<ApiRequestParam> queryParams = apiRunVO.getQueryParams();
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
			List<ApiRequestParam> bodyParams = apiRunVO.getBodyParams();
			List<ApiRequestParam> bodyRawParams = apiRunVO.getBodyRawParams();
			String bodyParamString = null;
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
			List<ApiRequestParam> headerParams = apiRunVO.getHeaderParams();
			for (ApiRequestParam apiRequestParam : headerParams) {
				httpPost.addHeader(new BasicHeader(apiRequestParam.getName(), apiRequestParam.getValue()));
			}
			//执行接口调用
			httpClient = HttpClients.createDefault();
			httpResponse = httpClient.execute(httpPost);
		}
		//取出響應頭
		Map<String, Object> headerMap = new HashMap<String,Object>();
		if(httpResponse!=null){
			Header [] headers = httpResponse.getAllHeaders();
			for (Header header : headers) {
				String name = header.getName();
				String value = header.getValue();
				headerMap.put(name, value);
			}
		}
		//要写回到页面的响应头数据
		String headerJsonString = JSONObject.toJSONString(headerMap, true);
		//要写回到页面的响应报文
		String result = EntityUtils.toString(httpResponse.getEntity());
		//json字符串--》java对象（反序列化）
		HashMap<String, Object> resultMap = JSONObject.parseObject(result, HashMap.class);
		//java对象---》json字符串（反序列化）
		String resultJsonString = JSONObject.toJSONString(resultMap, true);
		apiOnlineRunResult.setResponseHeaders(headerJsonString);
		apiOnlineRunResult.setResponseBody(resultJsonString);
		if(httpClient!=null){
			httpClient.close();
		}
		return apiOnlineRunResult;
	}
	
}
