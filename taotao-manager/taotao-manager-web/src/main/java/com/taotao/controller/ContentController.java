package com.taotao.controller;

import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

/**
 * 内容管理
 * <p>Title: ContentController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年8月19日上午11:24:41
 * @version 1.0
 */
@Controller
@RequestMapping("/content")
public class ContentController {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/save")
	@ResponseBody
	private TaotaoResult saveContent(TbContent content) {
		TaotaoResult result = contentService.insertContent(content);
		//调用taotao-rest发布的服务，同步缓存
		//REST_BASE_URL+REST_CONTENT_SYNC_URL+content.getCategoryId()
		System.out.println("+++++++++++++++++++++++++++"+REST_BASE_URL+REST_CONTENT_SYNC_URL+content.getCategoryId());
		String doGet = HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+content.getCategoryId());
		System.out.println("------------------------"+doGet);
		return result;
	}
}
