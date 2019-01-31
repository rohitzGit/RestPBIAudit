package com.cerner.rest.scheduler.jobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.ws.rs.client.Invocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.cerner.rest.domain.TBL_REM_D_WRK_LOG;
import com.cerner.rest.domain.TBL_REM_SUPPORT_GROUP_WRKLOG;
import com.cerner.rest.models.SupportGroupWrklog;
import com.cerner.rest.models.SupportGroupWrklogResponse;
import com.cerner.rest.persistence.IPersistenceManager;
import com.cerner.rest.persistence.PersistenceObject;
import com.cerner.rest.services.RestServices;

@Transactional
@Controller
public class SupportGroupPeopleJob {

	private RestServices restServices;
	private IPersistenceManager persistenceManager;
	private String problemId;
	ArrayList<String> al = new ArrayList<String>();
	Logger logger = LoggerFactory.getLogger(SupportGroupPeopleJob.class);

	public void setRestServices(RestServices restServices) {
		this.restServices = restServices;
	}
	public void setPersistenceManager(IPersistenceManager persistenceManager) {
		this.persistenceManager = persistenceManager;
	}

	public void execute() {

	logger.info("~*~*~*~*~*~*~*~ SupportGroupPeopleJob started at: " + new Date() + "~*~*~*~*~*~*~*~");
	Invocation invocation = null;	
	System.out.println("~*~*~*~*~*~*~*~Executing~*~*~*~*~*~*~*~");

	List<PersistenceObject> checkWrkLogEmpty = null;
	checkWrkLogEmpty = persistenceManager.findAll(TBL_REM_D_WRK_LOG.class);

	if(checkWrkLogEmpty.size() > 0) {
		persistenceManager.deleteAll(checkWrkLogEmpty);
	}

	List<PersistenceObject> persistenceObjects1 = persistenceManager.findAll(TBL_REM_SUPPORT_GROUP_WRKLOG.class);

	for(PersistenceObject persistenceObject : persistenceObjects1)
	{
		TBL_REM_SUPPORT_GROUP_WRKLOG supportGroupWrklog = (TBL_REM_SUPPORT_GROUP_WRKLOG)persistenceObject;

		invocation = restServices.getWorkLogInvocation(supportGroupWrklog.getProblemId());
		SupportGroupWrklogResponse supportGroupWrklogResponse = invocation.invoke().readEntity(SupportGroupWrklogResponse.class);
		problemId= supportGroupWrklog.getProblemId();


		if(! persistSupportGroupWorklog(supportGroupWrklogResponse)) 
		{
			System.out.println("Failed to insert rows");
		}

	}
	System.out.println("Failed PBI's : " + al);
}

private boolean persistSupportGroupWorklog(SupportGroupWrklogResponse supportGroupWrklogResponse) {

	String audit = null;
	String rcOrClnup=null;
	String noOfEncntrs=null;
	String balance=null;
	int IntergerNoOfEncntrs=0;
	float floatBalance=0;
	String crPbi=null;

	try {
		for(SupportGroupWrklog sprtGrpWrklogObj : supportGroupWrklogResponse.getData()) {

			TBL_REM_D_WRK_LOG wrklog = new TBL_REM_D_WRK_LOG();
			if(sprtGrpWrklogObj.getNotes().contains("RCMAUDIT"))
			{

				audit = sprtGrpWrklogObj.getNotes();
				System.out.println("PROBLEM_ID : " + problemId);
				System.out.println(sprtGrpWrklogObj.getWorkLogId());
				System.out.println(sprtGrpWrklogObj.getNotes());

				List<String> notes = new ArrayList<>(Arrays.asList(audit.split("#")));

				rcOrClnup =notes.get(1);
				noOfEncntrs = notes.get(2);
				IntergerNoOfEncntrs=Integer.parseInt(noOfEncntrs);
				balance =notes.get(3);
				floatBalance=Float.parseFloat(balance);

				String matchAudit = audit.replace("#", "");
				int result = audit.length() - matchAudit.length();
				System.out.println("Result : " + result);
				if(result!=4)
				{
					crPbi="No CR/parent PBI or incomplete details";
				}
				else
				{
					crPbi = notes.get(4);
				}

				System.out.println("rcOrClnup : "+ rcOrClnup);
				System.out.println("noOfEncntrs : "+ IntergerNoOfEncntrs);
				System.out.println("balance : "+ floatBalance);
				System.out.println("crOrPbi : "+ crPbi);
				System.out.println();
				wrklog.setProblemId(problemId);
				wrklog.setWorkLogId(sprtGrpWrklogObj.getWorkLogId());
				wrklog.setNotes(sprtGrpWrklogObj.getNotes());
				wrklog.setW_type(rcOrClnup);
				wrklog.setW_encounters(IntergerNoOfEncntrs);
				wrklog.setW_balance(floatBalance);
				wrklog.setW_cr_pbi(crPbi);
				persistenceManager.save(wrklog);
				}
			}
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println(ex);
			al.add(problemId);
			return false;
		}

	}
}




