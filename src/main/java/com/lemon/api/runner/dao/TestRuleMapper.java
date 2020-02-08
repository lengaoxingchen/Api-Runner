package com.lemon.api.runner.dao;

import com.lemon.api.runner.pojo.TestRule;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * InnoDB free: 6144 kB Mapper 接口
 * </p>
 *
 * @author nickjiang
 * @since 2019-09-12
 */
public interface TestRuleMapper extends BaseMapper<TestRule> {

	List<TestRule> findTestRulesOfCaseUnderCertainSuite(String suiteId) throws Exception;

}
