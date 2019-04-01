package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.AdNode;
import com.taotao.portal.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Value("${SERVICE_BASE_URL}")
	private String SERVICE_BASE_URL ;
	@Value("${INDEX_AD1_URL}")
	private String INDEX_AD1_URL ;
	@Value("${INDEX_AD1_CID}")
	private String INDEX_AD1_CID ;

	@Override
	public List<TbContent> getContentList(long categoryId) {
		//调用服务层的服务
		String resStr = HttpClientUtil.doGet(SERVICE_BASE_URL + INDEX_AD1_URL + categoryId);
		//把字符串转换成java对象
		TaotaoResult result = TaotaoResult.formatToList(resStr, TbContent.class);
		if (result.getStatus() == 200) {
			List<TbContent> listContent = (List<TbContent>) result.getData();
			return listContent;
		}
		return null;
	}

	@Override
	public String getAdList() {
		//调用服务层的服务
		String json = HttpClientUtil.doGet(SERVICE_BASE_URL + INDEX_AD1_URL + INDEX_AD1_CID);
		//把字符串转换成java对象
		TaotaoResult result = TaotaoResult.formatToList(json, TbContent.class);
		if (result.getStatus() == 200) {
			List<TbContent> listContent = (List<TbContent>) result.getData();
			//把内容列表转为AdNode列表
			List<AdNode> resultList = new ArrayList<>();
			for (TbContent tbContent : listContent) {
				AdNode node = new AdNode();
				node.setHeight(240);
				node.setWidth(670);
				node.setSrc(tbContent.getPic());
				node.setHeightB(240);
				node.setWidth(550);
				node.setSrcB(tbContent.getPic2());
				node.setAlt(tbContent.getTitle());
				node.setHref(tbContent.getUrl());
				resultList.add(node);
			}
		   //转为json
			String objectToJson = JsonUtils.objectToJson(resultList);
			return objectToJson;
		}
		return null;
	}

}
