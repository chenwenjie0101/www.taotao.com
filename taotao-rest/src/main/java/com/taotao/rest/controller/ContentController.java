package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.rest.service.ContentService;

/**
 * 内容管理服务 
 * <p>Title: ContentController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年8月19日上午11:40:55
 * @version 1.0
 */
@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/category/{categoryId}")
	@ResponseBody
	public TaotaoResult categoryList(@PathVariable Long categoryId) {
		//根据cid查询内容列表
		TaotaoResult result = contentService.getContentList(categoryId);
		return result;
	}
	@RequestMapping("/sync/{categoryId}")
	@ResponseBody
	public TaotaoResult syncContent(@PathVariable Long categoryId) {
		//根据cid查询内容列表
		try {
			TaotaoResult result = contentService.syncContent(categoryId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
	}
	
	
}
