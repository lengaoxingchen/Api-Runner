<html ang="zh-CN">
<#assign contextPath="${request.contextPath}"/>
<head> 
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>接口预览</title>
    <meta name="description" content=""> 
    <meta name="keywords" content="">
    <meta http-equiv="Cache-Control" content="no-transform">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/index.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/pagination.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/alert.css">
</head>
<body>
        	<div class="right-interlist right-interpre">
        		<div class="nav-interpre">
	        		<ul>
						<li class="active"><a href="${contextPath}/api/toApiView?apiId=${apiId}">预览</a></li>
						<li><a href="${contextPath}/api/toApiEdit?apiId=${apiId}">编辑</a></li>
						<li><a href="${contextPath}/api/toApiRun?apiId=${apiId}">运行</a></li>
					</ul>
				</div>
        		<div class="del-interpre">
 					<div class="basicinfo-interpre">
						<div class="comtit-interpre notop">基本信息</div>
						<div class="basiclist-interpre comlist-interpre">
							<ul>
								<li>
									<label>接口名称：</label>
									<div class="basicintro-interpre">${apiViewVO.name}</div>
								</li>
								<li class="alone">
									<label>接口路径：</label>
									<div class="basicintro-interpre">
										<i class="icon-badge">${apiViewVO.method!''}</i>
										${apiViewVO.url}
									</div>
								</li>
								<li>
									<label>创 建 人：</label>
									<div class="basicintro-interpre">
										<a href="#" class="link-perctr">
											<i class="icon-header"></i>
											${apiViewVO.createUser!''}
										</a>
									</div>
								</li>
								<li>
									<label>更新时间：</label>
									<div class="basicintro-interpre">${apiViewVO.createTime!''}</div>
								</li>
							</ul>
						</div>
 					</div>
 					<div class="remark-interpre">
						<div class="comtit-interpre">备注</div>
						<div class="remarkintro-interpre comlist-interpre">
							系统注册接口
						</div>
 					</div>
 					<div class="reqparam-interpre">
						<div class="comtit-interpre">请求参数</div>
						<div class="reqcom-interpre reqbody-interpre comlist-interpre">
							<div class="reqtit-interpre">Queries：</div>
							<div class="reqlist-interpre">
								<table>
									<thead>
										<tr>
											<th width="15%">参数名称</th>
											<th width="12%">参数类型</th>
											<th width="10%">示例</th>
											<th width="53%">备注</th>
										</tr>
									</thead>
									<tbody>
											<#if (apiViewVO.queryParams??) && ((apiViewVO.queryParams?size>0))>
											<#list apiViewVO.queryParams as queryParam>
											<tr>
												<td>${queryParam.name}</td>
												<td>${queryParam.paramType}</td>
												<td>${queryParam.exampleData}</td>
												<td>${queryParam.description}</td>
											</tr>
											</#list>
											</#if>
									</tbody>
								</table> 
							</div>
						</div>
						<div class="reqcom-interpre reqbody-interpre comlist-interpre">
							<div class="reqtit-interpre">Headers：</div>
							<div class="reqlist-interpre">
								<table>
									<thead>
										<tr>
											<th width="15%">参数名称</th>
											<th width="12%">参数类型</th>
											<th width="10%">示例</th>
											<th width="53%">备注</th>
										</tr>
									</thead>
									<tbody>
											<#if (apiViewVO.headerParams??) && ((apiViewVO.headerParams?size>0))>
											<#list apiViewVO.headerParams as headerParam>
											<tr>
												<td>${headerParam.name}</td>
												<td>${headerParam.paramType}</td>
												<td>${headerParam.exampleData}</td>
												<td>${headerParam.description}</td>
											</tr>
											</#list>
											</#if>
									</tbody>
								</table>
							</div>
						</div>
						<div class="reqcom-interpre reqbody-interpre comlist-interpre">
							<div class="reqtit-interpre">Body：</div>
							<div class="reqlist-interpre">
								<#if (apiViewVO.bodyParams??) && ((apiViewVO.bodyParams?size>0))>
								<table>
									<thead>
										<tr>
											<th width="15%">参数名称</th>
											<th width="12%">参数类型</th>
											<th width="10%">示例</th>
											<th width="53%">备注</th>
										</tr>
									</thead>
									<tbody>
										<#list apiViewVO.bodyParams as bodyParam>
											<tr>
												<td>${bodyParam.name}</td>
												<td>${bodyParam.paramType}</td>
												<td>${bodyParam.exampleData}</td>
												<td>${bodyParam.description}</td>
											</tr>
										</#list>
									</tbody>
								</table>
								</#if>
								<#if (apiViewVO.bodyRawParams??) && (apiViewVO.bodyRawParams?size>0)>
								<#list apiViewVO.bodyRawParams as bodyRawParam>
									<div class="basicinfo-interpre reqparamsset-interedit" >
				 						<div class="reqplist-interedit">
				 							<textarea name="bodyRawParams[0].exampleData" id="" class="remark-interedit" disabled="disabled">${bodyRawParam.exampleData!'{}'}</textarea>
				 							<input name="bodyRawParams[0].type" value="4" type="hidden"/>
				 						</div>
 									</div>
								</#list>
								</#if>
							</div>
						</div>
 					</div>
        		</div>
        	</div>
     <script type="text/javascript" src="${contextPath}/js/jquery-1.11.3.js"></script>  
     <script type="text/javascript" src="${contextPath}/js/jquery.pagination.js"></script>
     <script type="text/javascript" src="${contextPath}/js/alert.js"></script>
     <script type="text/javascript" src="${contextPath}/js/base.js"></script>
     <script type="text/javascript" src="${contextPath}/js/common.js"></script>


</body></html>