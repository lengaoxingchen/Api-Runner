package com.lemon.api.runner.service;

import com.lemon.api.runner.pojo.ApiRunVO;
import com.lemon.api.runner.pojo.CaseEditVO;
import com.lemon.api.runner.pojo.CaseOnlineRunResult;
import com.lemon.api.runner.pojo.Cases;
import com.lemon.api.runner.pojo.FirstSecondMenuVO;
import com.lemon.api.runner.pojo.TestReport;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * InnoDB free: 6144 kB 服务类
 * </p>
 *
 * @author nickjiang
 * @since 2019-08-31
 */
public interface ICaseService extends IService<Cases> {

	void add(Cases cs, ApiRunVO apiRunVO) throws Exception;

	CaseEditVO findCaseEditVO(String caseId) throws Exception;

	CaseOnlineRunResult run(CaseEditVO caseEditVO) throws Exception;

	void update(CaseEditVO caseEditVO) throws Exception;

	FirstSecondMenuVO findCaseSelectedMenu(String caseId) throws Exception;

	void seperateParams(CaseEditVO caseEditVO)throws Exception;

	TestReport runAndGetReport(CaseEditVO caseEditVO) throws Exception;

}
