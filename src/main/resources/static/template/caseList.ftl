<html ang="zh-CN">
<head>
<#assign contextPath="${request.contextPath}">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试集合</title>
<meta name="description" content="">
<meta name="keywords" content="">
<meta http-equiv="Cache-Control" content="no-transform">
<link rel="stylesheet" href="${contextPath}/css/index.css" type="text/css">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/pagination.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/alert.css">
</head>
<body>
	<input name="suiteId" value="${suiteId}" type="hidden">
	<input name="apiId" value="${apiId!''}" type="hidden">
	<input name="projectId" value="${projectId!''}" type="hidden">
	<div class="right-interlist right-testass">
		<div class="desc-interlist">
			<div class="desctop-interlist">
				<div class="desctit-interlist f-l">测试集合</div>
				<a href="javascript:;" id="starttest" class="btn-addfl btn-com f-r">开始测试</a>
			</div>
			<div class="remark-interlist">
				<input class="input-com" value="此处是套件的描述信息description" type="text">
				<div class="btnbox-interlist">
					<a href="javascript:;" class="btn-edit"></a> <a href="javascript:;"
						class="btn-sure"></a> <a href="javascript:;" class="btn-cancel"></a>
				</div>
			</div>
		</div>
		<div class="data-intrelist data-testass">
			<table>
				<thead>
					<tr>
						<th width="20%">用例名称</th>
						<th width="8%">状态</th>
						<th width="47%">接口路径</th>
						<th width="15%">测试报告</th>
					</tr>
				</thead>
				<tbody>
					<#if caseListVOs?? && (caseListVOs?size>0)>
						<#list caseListVOs as caseListVO>
							<tr>
								<input name="caseId" value="${caseListVO.id}" type="hidden">
								<td><a href="#" name="caseEditHref">${caseListVO.name}</a></td>
								<td><i class="icon-fail"></i></td>
								<td><a href="#" class="link-path" name="apiUrlHref">${caseListVO.apiUrl}</a></td>
								<td><a href="#" class="btn-testrep btn-com btn-default">测试报告</a></td>
							</tr>
						</#list>
					</#if>
				</tbody>
			</table>

		</div>
		<div class="box-pagination">
			<div class="inline-block">
				<div class="pagination m-style" id="pagination"></div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${contextPath}/js/jquery-1.11.3.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery.pagination.js"></script>
	<script type="text/javascript" src="${contextPath}/js/alert.js"></script>
	<script type="text/javascript" src="${contextPath}/js/base.js"></script>
	<script type="text/javascript" src="${contextPath}/js/common.js"></script>
	<script type="text/javascript" src="${contextPath}/js/caseList.js"></script>

</body>
</html>