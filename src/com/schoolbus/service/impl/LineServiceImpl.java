package com.schoolbus.service.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.schoolbus.dao.LineDao;
import com.schoolbus.entity.Line;
import com.schoolbus.entity.TreeJson;
import com.schoolbus.service.LineService;

@Service("lineService")
public class LineServiceImpl implements LineService{
	private Log logger  = LogFactory.getLog(LineServiceImpl.class);
	@Resource
	private LineDao lineDao;
	private String resultStr;
	private Gson gson = new Gson();
	
	
	@Override
	public String getLines(Line line) {
		ArrayList<Line> list = lineDao.selectLine(line);
		ArrayList<TreeJson> treeList = new ArrayList<TreeJson>();
		if(list != null){
			/*logger.debug(list.get(0).getName());
			String str = gson.toJson(list);
			resultStr = "{\"total\":"+list.size()+",\"rows\":"+str+"}";*/
			for(Line resultLine : list){
				TreeJson tree = new TreeJson();
				tree.setId(resultLine.getId());
				tree.setText(resultLine.getName());
				treeList.add(tree);
			}
			resultStr = gson.toJson(treeList);
		}else{
			resultStr = "{\"total\":"+0+",\"rows\":[]}";
		}
		return resultStr;
	}

	@Override
	public String updateLines(Line line) {
		line.setState(0);
		lineDao.saveLine(line);
		return null;
	}

	@Override
	public String removeLine(Line line) {
		int i = lineDao.deleteLine(line.getId());
		if(i == 1){
			resultStr = "{\"success\":true}";
		}else{
			resultStr = "{\"success\":false}";
		}
		return resultStr;
	}

}
