package cn.itcast.elec.service;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import cn.itcast.elec.domain.ElecApplication;
import cn.itcast.elec.domain.ElecApproveInfo;





public interface IElecApplicationService {
	public static final String SERVICE_NAME = "cn.itcast.elec.service.impl.ElecApplicationServiceImpl";

	void saveApplication(ElecApplication elecApplication);

	List<ElecApplication> findApplicationList(ElecApplication elecApplication);

	List<ElecApplication> findApplicationVariable();

	Collection<String> getOutComeTransition(ElecApplication elecApplication);

	InputStream findInputStreamByPath(ElecApplication elecApplication);

	void approveInfo(ElecApplication elecApplication);

	List<ElecApproveInfo> findApproveInfoListByApplicationID(
			ElecApplication elecApplication);

}
