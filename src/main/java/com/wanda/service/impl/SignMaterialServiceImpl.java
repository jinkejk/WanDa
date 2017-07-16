package com.wanda.service.impl;

import java.math.BigInteger;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.wanda.beans.SignMaterial;
import com.wanda.beans.TrainingMaterialsCategory;
import com.wanda.dao.SignMaterialDao;
import com.wanda.service.SignMaterialService;
import com.wanda.util.UtilCommon;

@Service("signMaterialService")
@Transactional
public class SignMaterialServiceImpl implements SignMaterialService {

	@Resource
	private SignMaterialDao signMaterialDao;
	
	@Override
	public void addSignMaterial(SignMaterial SM) {
		signMaterialDao.addSignMaterial(SM);
	}

	//一同删除上传的资料
	public void deleteSignMaterial(SignMaterial SM) {
		String path = "E:\\apache_tomcat_7.0.57\\webapps\\signMaterial\\";
		UtilCommon.deleteFile(path + SM.getSignFile());
		
		signMaterialDao.deleteSignMaterial(SM);
	}

	@Override
	public void updateSignMaterial(SignMaterial SM) {
		signMaterialDao.updateSignMaterial(SM);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<SignMaterial> getSignMaterialByPage(int pageSize, int currentPage) {
		return signMaterialDao.getSignMaterialByPage(pageSize, currentPage);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Long getTotalSignMaterialNum() {
		return signMaterialDao.getTotalSignMaterialNum();
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<SignMaterial> getSignMaterialByContentByPage(String searchContent, Integer pageSize,
			Integer currentPage) {
		return signMaterialDao.getSignMaterialByContentByPage(searchContent, pageSize, currentPage);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public BigInteger getTotalSignMaterialNumByContent(String searchContent) {
		return signMaterialDao.getTotalSignMaterialNumByContent(searchContent);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public SignMaterial getSignMaterialById(Integer SMId) {
		return signMaterialDao.getSignMaterialById(SMId);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<SignMaterial> getLastSignMaterial(int num) {
		return signMaterialDao.getLastSignMaterial(num);
	}

	@Override
	public void addSignMaterialDownloadNum(Integer SMId) {
		SignMaterial sm = signMaterialDao.getSignMaterialById(SMId);
		sm.setClickNum(sm.getClickNum() + 1);
		
		signMaterialDao.updateSignMaterial(sm);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public SignMaterial getSignMaterialByFileName(String signFile) {
		return signMaterialDao.getSignMaterialByFileName(signFile);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<SignMaterial> getSignMaterialsByCategory(TrainingMaterialsCategory category, int pageSize,
			int currentPage) {
		return signMaterialDao.getSignMaterialsByCategory(category, pageSize, currentPage);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public long getSignMaterialNumByCategory(TrainingMaterialsCategory category) {
		return signMaterialDao.getSignMaterialNumByCategory(category);
	}

	@Override
	public void deleteSignMaterials(List<Integer> SMIds) {
		for(int SMId: SMIds){
			signMaterialDao.deleteSignMaterial(signMaterialDao.getSignMaterialById(SMId));
		}		
	}

}
