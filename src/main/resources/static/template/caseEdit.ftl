<html ang="zh-CN">
<head>
	<#assign contextPath="${request.contextPath}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>接口运行</title>
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta http-equiv="Cache-Control" content="no-transform">
    <link rel="stylesheet" href="${contextPath}/css/index.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/pagination.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/alert.css">
    <style>
   	pre{
    	white-space: pre-wrap;
		word-wrap: break-word;
		color:#2e952e;
    }
   	</style>
</head>
<body> 
       	 <input type="hidden" name="apiId" value="${apiId!''}"/>
       	 <input type="hidden" name="projectId" value="${projectId!''}"/>
        <form name="caseForm">
        	<div class="right-interlist right-interpre">
        		<div class="paramlist-interrun">
					<div class="paramcom-interrun radius4">
							<div class="paramline-interrun active">
								<div class="line-com line-interrun">
									<div class="ipt-interrun f-l" style="width:100%">
										<input placeholder="参数值" style="width:90%" name="caseName" value="${caseEditVO.caseName!''}" type="text">
										<span class="inter-link" style="margin: 0px 8px 0px 6px; font-size: 12px;"><a class="text" href="#" name="relatedApi">对应接口</a></span>
									</div>
								</div>
							</div>
					</div>
        		<input value="${caseEditVO.id}" name="id" type="hidden">
        		<input value="${caseEditVO.method}" name="method" type="hidden">
        		<input value="${caseEditVO.host}" name="host" type="hidden">
        		<input value="${caseEditVO.url}" name="url" type="hidden">
        		<div class="del-interrun">
        			<div class="envirset-interrun">
						<div class="line-interedit line-interrun line-com clearfix">
							<select style="width:10%;" disabled="disabled">
								<option value="get" <#if caseEditVO.method=='get'>selected=""</#if> >GET</option>
								<option value="post" <#if caseEditVO.method=='post'>selected=""</#if>>POST</option>
								<option value="put" <#if caseEditVO.method=='put'>selected=""</#if>>PUT</option>
								<option value="delete" <#if caseEditVO.method=='delete'>selected=""</#if>>DELETE</option>
								<option value="head" <#if caseEditVO.method=='head'>selected=""</#if>>HEAD</option>
								<option value="option" <#if caseEditVO.method=='option'>selected=""</#if>>OPTION</option>
								<option value="patch" <#if caseEditVO.method=='patch'>selected=""</#if>>PATCH</option>
							</select>
							<div class="ipt-interedit f-l" style="width:45%;">
								<input class="iptenvir-interrun pointer arrow" value="${caseEditVO.host}" disabled="disabled" type="text" name="host">
							</div>
							<div class="ipt-interedit" style="width:45%;">
								<input disabled="disabled" name="url" value="${caseEditVO.url}" placeholder="接口地址" type="text">
							</div>
						</div>
						<div class="btnbox-interrun f-r">
							<a href="javascript:void(0);" class="btn-send btn-com">发送</a>
							<a href="javascript:void(0);" id="btnUApi" class="btn-update btn-com">更新</a>
						</div>
        			</div>
        			<div class="paramlist-interrun">
						<div class="paramcom-interrun radius4">
							<div class="paramtit-interrun"><i class="icon-arrow active"></i>QUERIES</div>
							<div class="paramline-interrun active">
								<#if (caseEditVO.queryParams??)&&(caseEditVO.queryParams?size>0)>
								<#list caseEditVO.queryParams as queryParam>
								<div class="line-com line-interrun">
									<input type="hidden" name="queryParams[${queryParam_index}].id" value="${queryParam.id!''}">
									<div class="ipt-interrun f-l" style="width:15%">
										<div class="ltipt-interrun">
											<input class="disabled" readonly="readonly" value="${queryParam.name}" name="queryParams[${queryParam_index}].name" type="text">
										</div>
										<div class="equal-interrun f-r">=</div>
										<input class="disabled f-r" disabled="" checked="" type="checkbox">
									</div>
									<div class="ipt-interrun f-l" style="width:85%">
										<input placeholder="参数值" style="width:100%" name="queryParams[${queryParam_index}].value" value="${queryParam.value!''}" type="text">
										<div class="edit-interrun f-l"></div>
									</div>
								</div>
								</#list>
								</#if>
							</div>
						</div>
        			<div class="paramlist-interrun">
						<div class="paramcom-interrun radius4">
							<div class="paramtit-interrun"><i class="icon-arrow active"></i>BODY(F9)</div>
							<div class="paramline-interrun active">
								<#if caseEditVO.bodyParams?? && (caseEditVO.bodyParams?size>0)>
									<#list caseEditVO.bodyParams as bodyParam>
									<div class="line-com line-interrun">
										<input name="bodyParams[${bodyParam_index}].id" value="${bodyParam.id}" type="hidden">
										<div class="ipt-interrun f-l" style="width:15%">
											<div class="ltipt-interrun">
												<input class=" disabled" readonly="readonly" value="${bodyParam.name}" name="bodyParams[${bodyParam_index}].name" type="text">
											</div>
											<div class="equal-interrun f-r">=</div>
											<input class="disabled f-r" disabled="" checked="" type="checkbox">
										</div>
										<div class="ipt-interrun f-l" style="width:85%">
											<input placeholder="参数值" style="width:100%" name="bodyParams[${bodyParam_index}].value" value="${bodyParam.value!''}" type="text">
											<div class="edit-interrun f-l"></div>
										</div>
									</div>
									</#list>
								</#if>
							</div>
						</div>
						<div class="paramcom-interrun radius4">
							<div class="paramtit-interrun"><i class="icon-arrow active"></i>HEADERS</div>
							<div class="paramline-interrun active">
								<#if caseEditVO.headerParams?? && (caseEditVO.headerParams?size>0)>
								<#list caseEditVO.headerParams as headerParam>
								<div class="line-com line-interrun">
									<input name="headerParams[${headerParam_index}].id" value="${headerParam.id}" type="hidden">
									<div class="ipt-interrun f-l" style="width:15%">
										<div class="ltipt-interrun">
											<input class=" disabled" readonly="readonly" value="${headerParam.name}" name="headerParams[${headerParam_index}].name" type="text">
										</div>
										<div class="equal-interrun f-r">=</div>
										<input class="disabled f-r" disabled="" checked="" type="checkbox">
									</div>
									<div class="ipt-interrun f-l" style="width:85%">
										<input placeholder="参数值" style="width:100%" name="headerParams[${headerParam_index}].value" value="${headerParam.value!''}" type="text">
										<div class="edit-interrun f-l"></div>
									</div>
								</div>
								</#list>
								</#if>
							</div>
						</div>
        			</div>
        			<div class="response-interrun">
        				<div class="restit-interrun">
							<div class="titnav-interrun-non-underline" style="border-bottom:2px solid #2395f1;color:#2395f1">Response</div>
							<div class="titnav-interrun-non-underline">Test</div>
        				</div>
						<div class="reslist-interrun">
							<div class="comlist-interrun headlist-interrun f-l">
								<div class="titlist-interrun">Headers</div>
								<div class="txtlist-interrun radius4" name="responseHeaders">
								
								</div>
							</div>
							<div class="comlist-interrun bodylist-interrun f-l">
								<div class="titlist-interrun">Body</div>
								<div class="txtlist-interrun radius4" name="responseBody">
								
								</div>
							</div>
						</div>
						<div class="testlist-interrun" style="display:none">
							<div class="reqpline-interedit linebox-interedit">
								<a href="javascript:void(0)" class="btn-com" id="addRule" style="margin-top:10px;margin-left:10px">添加数据校验规则</a>
								<#if caseEditVO.testRules?? && (caseEditVO.testRules?size>0)>
								<#list caseEditVO.testRules as testRule>
								<div class="line-interedit line-com">
									<input type="hidden" name="testRules[${testRule_index}].id" value="${testRule.id}">
									<input placeholder="jsonpath表达式" name="testRules[${testRule_index}].expression" value="${testRule.expression}" style="width:40%;margin-left:5px" type="text">
									<select name="testRules[${testRule_index}].operator" id="" style="width:10%;margin-left:10px">
										<option value="=" <#if testRule.operator=="=">selected=""</#if>>=</option>
										<option value="contains" <#if testRule.operator=="contains">selected=""</#if>>contains</option>
									</select>
									<input placeholder="期望值" name="testRules[${testRule_index}].expected" value="${testRule.expected}" style="width:30%;margin-left:5px" type="text">
									<i class="icon icon-delete f-l"></i>
								</div>
								</#list>
								</#if>
							</div>
						</div>
        			</div>
        			
        		</div>
        	</div>
        </div>
        </form>
     <script type="text/javascript" src="${contextPath}/js/jquery-1.11.3.js"></script>  
     <script type="text/javascript" src="${contextPath}/js/jquery.pagination.js"></script>
     <script type="text/javascript" src="${contextPath}/js/alert.js"></script>
     <script type="text/javascript" src="${contextPath}/js/base.js"></script>
     <script type="text/javascript" src="${contextPath}/js/common.js"></script>
     <script type="text/javascript" src="${contextPath}/js/jquery-validate.js"></script>
     <script type="text/javascript" src="${contextPath}/js/caseEdit.js"></script>
</body>
</html>