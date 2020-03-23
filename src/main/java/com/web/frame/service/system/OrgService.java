package com.web.frame.service.system;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.frame.dao.system.OrgDao;
import com.web.frame.entity.table.system.Org;
import com.web.frame.util.Uuid;

@Service
@Transactional
public class OrgService {

	@Autowired
	private OrgDao orgDao;

	public List<Org> initOrg() {
		
		return orgDao.initOrg();
	}

	public Org addOrg(Org org) throws Exception {

		if(!StringUtils.isNotEmpty(org.getId())) {
			String id = Uuid.getUuid();
			org.setId(id );
		}
		String pids = org.getPids();
		if(StringUtils.isNotEmpty(pids)) {
			org.setPids(pids+","+org.getId());
		}else {
			org.setPids(org.getId());
		}
		orgDao.addOrg(org);
		return org;
	}

	public Org queryOrgById(String id) {
		
		return orgDao.queryOrgById(id);
	}

	public void updateOrg(Org org) throws Exception {
		
		orgDao.updateOrg(org);
	}

	public void deleteOrg(String id) {
		
		orgDao.deleteOrg(id);
	}

	public List<Org> initOrgUser(String orgId) {
		
		return orgDao.queryOrgUser(orgId);
	}


	
	
	
}

