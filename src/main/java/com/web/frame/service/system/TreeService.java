package com.web.frame.service.system;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.frame.dao.system.TreeDao;
import com.web.frame.entity.table.system.Tree;
import com.web.frame.util.Uuid;

@Service
@Transactional
public class TreeService {

	@Autowired
	private TreeDao treeDao;

	public List<Tree> initTree() {
		
		return treeDao.initTree();
	}

	public Tree addTree(Tree tree) throws Exception {
		
		String id = Uuid.getUuid();
		tree.setId(id );
		String pids = tree.getPids();
		if(StringUtils.isNotEmpty(pids)) {
			tree.setPids(pids+","+id);
		}else {
			tree.setPids(id);
		}
		treeDao.addTree(tree);
		return tree;
	}

	public Tree queryTreeById(String id) {
		
		return treeDao.queryTreeById(id);
	}

	public void updateTree(Tree tree) throws Exception {
		
		treeDao.updateTree(tree);
	}

	public void deleteTree(String id) {
		
		treeDao.deleteTree(id);
	}

	public List<Tree> initTreeUser(String roleId) {
		
		return treeDao.queryTreeUser(roleId);
	}


	
	
	
}

