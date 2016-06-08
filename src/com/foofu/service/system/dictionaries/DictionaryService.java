package com.foofu.service.system.dictionaries;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.foofu.dao.DaoSupport;
import com.foofu.entity.system.Dictionary;

@Service("dictionaryService")
public class DictionaryService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 根据机构ID查询本机构及下级机构
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Dictionary> listDictionary(Map<String,Object> paramMap) throws Exception  {
		return (List<Dictionary>) dao.findForList("DictionaryMapper.listDictionary", paramMap);
	}
	
	/**
	 * 根据type和value取出name
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Dictionary queryDictionaryNameByTypeAndValue(Map<String,Object> paramMap) throws Exception  {
		return (Dictionary) dao.findForObject("DictionaryMapper.queryDictionaryNameByTypeAndValue", paramMap);
	}
}
