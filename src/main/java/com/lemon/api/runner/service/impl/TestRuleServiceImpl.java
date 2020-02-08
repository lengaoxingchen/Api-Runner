package com.lemon.api.runner.service.impl;

import com.lemon.api.runner.pojo.TestRule;
import com.lemon.api.runner.dao.TestRuleMapper;
import com.lemon.api.runner.service.ITestRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * InnoDB free: 6144 kB 服务实现类
 * </p>
 *
 * @author nickjiang
 * @since 2019-09-12
 */
@Service
public class TestRuleServiceImpl extends ServiceImpl<TestRuleMapper, TestRule> implements ITestRuleService {

	@Autowired
	private TestRuleMapper testRuleMapper;
	@Override
	public List<TestRule> findTestRulesOfCaseUnderCertainSuite(String suiteId) throws Exception {
		return testRuleMapper.findTestRulesOfCaseUnderCertainSuite(suiteId);
	}

}
