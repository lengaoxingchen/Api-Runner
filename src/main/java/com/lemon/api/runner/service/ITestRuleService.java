package com.lemon.api.runner.service;

import com.lemon.api.runner.pojo.TestRule;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * InnoDB free: 6144 kB 服务类
 * </p>
 *
 * @author nickjiang
 * @since 2019-09-12
 */
public interface ITestRuleService extends IService<TestRule> {

	List<TestRule> findTestRulesOfCaseUnderCertainSuite(String suiteId) throws Exception;

}
