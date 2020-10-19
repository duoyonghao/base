package com.hudh.zzbl.entity;

import java.io.Serializable;

public class RepairSchemeConfirm implements Serializable {
	
	private String seqId;

    private String createtime;

    private String createuser;

    private String id;

    private String order_number;

    private String tprgleftup;
    private String patientTime;
    private String doctorTime;
    private String status;
    
    public String getPatientTime() {
		return patientTime;
	}

	public void setPatientTime(String patientTime) {
		this.patientTime = patientTime;
	}

	public String getDoctorTime() {
		return doctorTime;
	}

	public void setDoctorTime(String doctorTime) {
		this.doctorTime = doctorTime;
	}

	private String operationDoctorTime;//手术医生签名时间
    private String serviceTime;//客服签名时间
    private String servicesignature;//客服签名
    private String operationDoctorsignature;//手术医生签名
    private String repairDoctorsignature;//修复医生签名
    private String patientsignature;//患者签名
    
    private String username; //患者姓名
    private String sex; //患者性别
    private String age; //患者年龄

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getOperationDoctorTime() {
		return operationDoctorTime;
	}

	public void setOperationDoctorTime(String operationDoctorTime) {
		this.operationDoctorTime = operationDoctorTime;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getServicesignature() {
		return servicesignature;
	}

	public void setServicesignature(String servicesignature) {
		this.servicesignature = servicesignature;
	}

	public String getOperationDoctorsignature() {
		return operationDoctorsignature;
	}

	public void setOperationDoctorsignature(String operationDoctorsignature) {
		this.operationDoctorsignature = operationDoctorsignature;
	}

	public String getRepairDoctorsignature() {
		return repairDoctorsignature;
	}

	public void setRepairDoctorsignature(String repairDoctorsignature) {
		this.repairDoctorsignature = repairDoctorsignature;
	}

	public String getPatientsignature() {
		return patientsignature;
	}

	public void setPatientsignature(String patientsignature) {
		this.patientsignature = patientsignature;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	private String tprgleftdown;

    private String tprgrightup;

    private String tprgrightdown;

    private String plantthistime;

    private String dentiumleftup;

    private String dentiumleftdown;

    private String dentiumrightup;

    private String dentiumrightdown;

    private String icxleftup;

    private String icxleftdown;

    private String icxrightup;

    private String icxrightdown;

    private String templantleftup;

    private String templantleftdown;

    private String templantrightup;

    private String templantrightdown;

    private String nobelactiveleftup;

    private String nobelactiveleftdown;

    private String nobelactiverightup;

    private String nobelactiverightdown;

    private String camlogleftup;

    private String camlogleftdown;

    private String camlogrightup;

    private String camlogrightdown;

    private String hiossenleftup;

    private String hiossenleftdown;

    private String hiossenrightup;

    private String hiossenrightdown;

    private String rbrdleftup;

    private String rbrdleftdown;

    private String rbrdrigthup;

    private String rbrdrigthdown;

    private String invidenleftup;

    private String invidenleftdown;

    private String invidenrightup;

    private String invidenrightdown;

    private String locatorleftup;

    private String locatorleftdown;

    private String locatorrightup;

    private String locatorrightdown;

    private String srrleftup;

    private String srrleftdown;

    private String srrrightup;

    private String srrrightdown;

    private String retainerleftup;

    private String retainerleftdown;

    private String retainerrightup;

    private String retainerrightdown;

    private String singlecompleftup;

    private String singlecompleftdown;

    private String singlecomprightup;

    private String singlecomprightdown;

    private String singlecompselect;

    private String sfsincomleftup;

    private String sfsincomleftdown;

    private String sfsincomrightup;

    private String sfsincomrightdown;

    private String sfsincomselect;

    private String localplantleftup;

    private String localplantleftdown;

    private String localplantrightup;

    private String localplantrightdown;

    private String localplantselect;

    private String requirerestor;

    private String organization;
    
    private String nobelpmcleftup;//新增牙位图
    private String nobelpmcleftdown;
    private String nobelpmcrightup;
    private String nobelpmcrightdown;
    
    private String zimmerleftup;//新增牙位图
    private String zimmerleftdown;
    private String zimmerrightup;
    private String zimmerrightdown;

    private String etleftup;//新增牙位图
    private String etleftdown;
    private String etrightup;
    private String etrightdown;

    private String bbleftup;//新增牙位图
    private String bbleftdown;
    private String bbrightup;
    private String bbrightdown;

    public String getNobelpmcleftup() {
		return nobelpmcleftup;
	}

	public void setNobelpmcleftup(String nobelpmcleftup) {
		this.nobelpmcleftup = nobelpmcleftup;
	}

	public String getNobelpmcleftdown() {
		return nobelpmcleftdown;
	}

	public void setNobelpmcleftdown(String nobelpmcleftdown) {
		this.nobelpmcleftdown = nobelpmcleftdown;
	}

	public String getNobelpmcrightup() {
		return nobelpmcrightup;
	}

	public void setNobelpmcrightup(String nobelpmcrightup) {
		this.nobelpmcrightup = nobelpmcrightup;
	}

	public String getNobelpmcrightdown() {
		return nobelpmcrightdown;
	}

	public void setNobelpmcrightdown(String nobelpmcrightdown) {
		this.nobelpmcrightdown = nobelpmcrightdown;
	}

	public String getZimmerleftup() {
		return zimmerleftup;
	}

	public void setZimmerleftup(String zimmerleftup) {
		this.zimmerleftup = zimmerleftup;
	}

	public String getZimmerleftdown() {
		return zimmerleftdown;
	}

	public void setZimmerleftdown(String zimmerleftdown) {
		this.zimmerleftdown = zimmerleftdown;
	}

	public String getZimmerrightup() {
		return zimmerrightup;
	}

	public void setZimmerrightup(String zimmerrightup) {
		this.zimmerrightup = zimmerrightup;
	}

	public String getZimmerrightdown() {
		return zimmerrightdown;
	}

	public void setZimmerrightdown(String zimmerrightdown) {
		this.zimmerrightdown = zimmerrightdown;
	}

	private static final long serialVersionUID = 1L;

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId == null ? null : seqId.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTprgleftup() {
        return tprgleftup;
    }

    public void setTprgleftup(String tprgleftup) {
        this.tprgleftup = tprgleftup == null ? null : tprgleftup.trim();
    }

    public String getTprgleftdown() {
        return tprgleftdown;
    }

    public void setTprgleftdown(String tprgleftdown) {
        this.tprgleftdown = tprgleftdown == null ? null : tprgleftdown.trim();
    }

    public String getTprgrightup() {
        return tprgrightup;
    }

    public void setTprgrightup(String tprgrightup) {
        this.tprgrightup = tprgrightup == null ? null : tprgrightup.trim();
    }

    public String getTprgrightdown() {
        return tprgrightdown;
    }

    public void setTprgrightdown(String tprgrightdown) {
        this.tprgrightdown = tprgrightdown == null ? null : tprgrightdown.trim();
    }

    public String getPlantthistime() {
        return plantthistime;
    }

    public void setPlantthistime(String plantthistime) {
        this.plantthistime = plantthistime == null ? null : plantthistime.trim();
    }

    public String getDentiumleftup() {
        return dentiumleftup;
    }

    public void setDentiumleftup(String dentiumleftup) {
        this.dentiumleftup = dentiumleftup == null ? null : dentiumleftup.trim();
    }

    public String getDentiumleftdown() {
        return dentiumleftdown;
    }

    public void setDentiumleftdown(String dentiumleftdown) {
        this.dentiumleftdown = dentiumleftdown == null ? null : dentiumleftdown.trim();
    }

    public String getDentiumrightup() {
        return dentiumrightup;
    }

    public void setDentiumrightup(String dentiumrightup) {
        this.dentiumrightup = dentiumrightup == null ? null : dentiumrightup.trim();
    }

    public String getDentiumrightdown() {
        return dentiumrightdown;
    }

    public void setDentiumrightdown(String dentiumrightdown) {
        this.dentiumrightdown = dentiumrightdown == null ? null : dentiumrightdown.trim();
    }

    public String getIcxleftup() {
        return icxleftup;
    }

    public void setIcxleftup(String icxleftup) {
        this.icxleftup = icxleftup == null ? null : icxleftup.trim();
    }

    public String getIcxleftdown() {
        return icxleftdown;
    }

    public void setIcxleftdown(String icxleftdown) {
        this.icxleftdown = icxleftdown == null ? null : icxleftdown.trim();
    }

    public String getIcxrightup() {
        return icxrightup;
    }

    public void setIcxrightup(String icxrightup) {
        this.icxrightup = icxrightup == null ? null : icxrightup.trim();
    }

    public String getIcxrightdown() {
        return icxrightdown;
    }

    public void setIcxrightdown(String icxrightdown) {
        this.icxrightdown = icxrightdown == null ? null : icxrightdown.trim();
    }

    public String getNobelactiveleftup() {
        return nobelactiveleftup;
    }

    public void setNobelactiveleftup(String nobelactiveleftup) {
        this.nobelactiveleftup = nobelactiveleftup == null ? null : nobelactiveleftup.trim();
    }

    public String getNobelactiveleftdown() {
        return nobelactiveleftdown;
    }

    public void setNobelactiveleftdown(String nobelactiveleftdown) {
        this.nobelactiveleftdown = nobelactiveleftdown == null ? null : nobelactiveleftdown.trim();
    }

    public String getNobelactiverightup() {
        return nobelactiverightup;
    }

    public void setNobelactiverightup(String nobelactiverightup) {
        this.nobelactiverightup = nobelactiverightup == null ? null : nobelactiverightup.trim();
    }

    public String getNobelactiverightdown() {
        return nobelactiverightdown;
    }

    public void setNobelactiverightdown(String nobelactiverightdown) {
        this.nobelactiverightdown = nobelactiverightdown == null ? null : nobelactiverightdown.trim();
    }

    public String getCamlogleftup() {
        return camlogleftup;
    }

    public void setCamlogleftup(String camlogleftup) {
        this.camlogleftup = camlogleftup == null ? null : camlogleftup.trim();
    }

    public String getCamlogleftdown() {
        return camlogleftdown;
    }

    public void setCamlogleftdown(String camlogleftdown) {
        this.camlogleftdown = camlogleftdown == null ? null : camlogleftdown.trim();
    }

    public String getCamlogrightup() {
        return camlogrightup;
    }

    public void setCamlogrightup(String camlogrightup) {
        this.camlogrightup = camlogrightup == null ? null : camlogrightup.trim();
    }

    public String getCamlogrightdown() {
        return camlogrightdown;
    }

    public void setCamlogrightdown(String camlogrightdown) {
        this.camlogrightdown = camlogrightdown == null ? null : camlogrightdown.trim();
    }

    public String getHiossenleftup() {
        return hiossenleftup;
    }

    public void setHiossenleftup(String hiossenleftup) {
        this.hiossenleftup = hiossenleftup == null ? null : hiossenleftup.trim();
    }

    public String getHiossenleftdown() {
        return hiossenleftdown;
    }

    public void setHiossenleftdown(String hiossenleftdown) {
        this.hiossenleftdown = hiossenleftdown == null ? null : hiossenleftdown.trim();
    }

    public String getHiossenrightup() {
        return hiossenrightup;
    }

    public void setHiossenrightup(String hiossenrightup) {
        this.hiossenrightup = hiossenrightup == null ? null : hiossenrightup.trim();
    }

    public String getHiossenrightdown() {
        return hiossenrightdown;
    }

    public void setHiossenrightdown(String hiossenrightdown) {
        this.hiossenrightdown = hiossenrightdown == null ? null : hiossenrightdown.trim();
    }

    public String getRbrdleftup() {
        return rbrdleftup;
    }

    public void setRbrdleftup(String rbrdleftup) {
        this.rbrdleftup = rbrdleftup == null ? null : rbrdleftup.trim();
    }

    public String getRbrdleftdown() {
        return rbrdleftdown;
    }

    public void setRbrdleftdown(String rbrdleftdown) {
        this.rbrdleftdown = rbrdleftdown == null ? null : rbrdleftdown.trim();
    }

    public String getRbrdrigthup() {
        return rbrdrigthup;
    }

    public void setRbrdrigthup(String rbrdrigthup) {
        this.rbrdrigthup = rbrdrigthup == null ? null : rbrdrigthup.trim();
    }

    public String getRbrdrigthdown() {
        return rbrdrigthdown;
    }

    public void setRbrdrigthdown(String rbrdrigthdown) {
        this.rbrdrigthdown = rbrdrigthdown == null ? null : rbrdrigthdown.trim();
    }

    public String getInvidenleftup() {
        return invidenleftup;
    }

    public void setInvidenleftup(String invidenleftup) {
        this.invidenleftup = invidenleftup == null ? null : invidenleftup.trim();
    }

    public String getInvidenleftdown() {
        return invidenleftdown;
    }

    public void setInvidenleftdown(String invidenleftdown) {
        this.invidenleftdown = invidenleftdown == null ? null : invidenleftdown.trim();
    }

    public String getInvidenrightup() {
        return invidenrightup;
    }

    public void setInvidenrightup(String invidenrightup) {
        this.invidenrightup = invidenrightup == null ? null : invidenrightup.trim();
    }

    public String getInvidenrightdown() {
        return invidenrightdown;
    }

    public void setInvidenrightdown(String invidenrightdown) {
        this.invidenrightdown = invidenrightdown == null ? null : invidenrightdown.trim();
    }

    public String getLocatorleftup() {
        return locatorleftup;
    }

    public void setLocatorleftup(String locatorleftup) {
        this.locatorleftup = locatorleftup == null ? null : locatorleftup.trim();
    }

    public String getLocatorleftdown() {
        return locatorleftdown;
    }

    public void setLocatorleftdown(String locatorleftdown) {
        this.locatorleftdown = locatorleftdown == null ? null : locatorleftdown.trim();
    }

    public String getLocatorrightup() {
        return locatorrightup;
    }

    public void setLocatorrightup(String locatorrightup) {
        this.locatorrightup = locatorrightup == null ? null : locatorrightup.trim();
    }

    public String getLocatorrightdown() {
        return locatorrightdown;
    }

    public void setLocatorrightdown(String locatorrightdown) {
        this.locatorrightdown = locatorrightdown == null ? null : locatorrightdown.trim();
    }

    public String getSrrleftup() {
        return srrleftup;
    }

    public void setSrrleftup(String srrleftup) {
        this.srrleftup = srrleftup == null ? null : srrleftup.trim();
    }

    public String getSrrleftdown() {
        return srrleftdown;
    }

    public void setSrrleftdown(String srrleftdown) {
        this.srrleftdown = srrleftdown == null ? null : srrleftdown.trim();
    }

    public String getSrrrightup() {
        return srrrightup;
    }

    public void setSrrrightup(String srrrightup) {
        this.srrrightup = srrrightup == null ? null : srrrightup.trim();
    }

    public String getSrrrightdown() {
        return srrrightdown;
    }

    public void setSrrrightdown(String srrrightdown) {
        this.srrrightdown = srrrightdown == null ? null : srrrightdown.trim();
    }

    public String getRetainerleftup() {
        return retainerleftup;
    }

    public void setRetainerleftup(String retainerleftup) {
        this.retainerleftup = retainerleftup == null ? null : retainerleftup.trim();
    }

    public String getRetainerleftdown() {
        return retainerleftdown;
    }

    public void setRetainerleftdown(String retainerleftdown) {
        this.retainerleftdown = retainerleftdown == null ? null : retainerleftdown.trim();
    }

    public String getRetainerrightup() {
        return retainerrightup;
    }

    public void setRetainerrightup(String retainerrightup) {
        this.retainerrightup = retainerrightup == null ? null : retainerrightup.trim();
    }

    public String getRetainerrightdown() {
        return retainerrightdown;
    }

    public void setRetainerrightdown(String retainerrightdown) {
        this.retainerrightdown = retainerrightdown == null ? null : retainerrightdown.trim();
    }

    public String getSinglecompleftup() {
        return singlecompleftup;
    }

    public void setSinglecompleftup(String singlecompleftup) {
        this.singlecompleftup = singlecompleftup == null ? null : singlecompleftup.trim();
    }

    public String getSinglecompleftdown() {
        return singlecompleftdown;
    }

    public void setSinglecompleftdown(String singlecompleftdown) {
        this.singlecompleftdown = singlecompleftdown == null ? null : singlecompleftdown.trim();
    }

    public String getSinglecomprightup() {
        return singlecomprightup;
    }

    public void setSinglecomprightup(String singlecomprightup) {
        this.singlecomprightup = singlecomprightup == null ? null : singlecomprightup.trim();
    }

    public String getSinglecomprightdown() {
        return singlecomprightdown;
    }

    public void setSinglecomprightdown(String singlecomprightdown) {
        this.singlecomprightdown = singlecomprightdown == null ? null : singlecomprightdown.trim();
    }

    public String getSinglecompselect() {
        return singlecompselect;
    }

    public void setSinglecompselect(String singlecompselect) {
        this.singlecompselect = singlecompselect == null ? null : singlecompselect.trim();
    }

    public String getSfsincomleftup() {
        return sfsincomleftup;
    }

    public void setSfsincomleftup(String sfsincomleftup) {
        this.sfsincomleftup = sfsincomleftup == null ? null : sfsincomleftup.trim();
    }

    public String getSfsincomleftdown() {
        return sfsincomleftdown;
    }

    public void setSfsincomleftdown(String sfsincomleftdown) {
        this.sfsincomleftdown = sfsincomleftdown == null ? null : sfsincomleftdown.trim();
    }

    public String getSfsincomrightup() {
        return sfsincomrightup;
    }

    public void setSfsincomrightup(String sfsincomrightup) {
        this.sfsincomrightup = sfsincomrightup == null ? null : sfsincomrightup.trim();
    }

    public String getSfsincomrightdown() {
        return sfsincomrightdown;
    }

    public void setSfsincomrightdown(String sfsincomrightdown) {
        this.sfsincomrightdown = sfsincomrightdown == null ? null : sfsincomrightdown.trim();
    }

    public String getSfsincomselect() {
        return sfsincomselect;
    }

    public void setSfsincomselect(String sfsincomselect) {
        this.sfsincomselect = sfsincomselect == null ? null : sfsincomselect.trim();
    }

    public String getLocalplantleftup() {
        return localplantleftup;
    }

    public void setLocalplantleftup(String localplantleftup) {
        this.localplantleftup = localplantleftup == null ? null : localplantleftup.trim();
    }

    public String getLocalplantleftdown() {
        return localplantleftdown;
    }

    public void setLocalplantleftdown(String localplantleftdown) {
        this.localplantleftdown = localplantleftdown == null ? null : localplantleftdown.trim();
    }

    public String getLocalplantrightup() {
        return localplantrightup;
    }

    public void setLocalplantrightup(String localplantrightup) {
        this.localplantrightup = localplantrightup == null ? null : localplantrightup.trim();
    }

    public String getLocalplantrightdown() {
        return localplantrightdown;
    }

    public void setLocalplantrightdown(String localplantrightdown) {
        this.localplantrightdown = localplantrightdown == null ? null : localplantrightdown.trim();
    }

    public String getLocalplantselect() {
        return localplantselect;
    }

    public void setLocalplantselect(String localplantselect) {
        this.localplantselect = localplantselect == null ? null : localplantselect.trim();
    }

    public String getRequirerestor() {
        return requirerestor;
    }

    public void setRequirerestor(String requirerestor) {
        this.requirerestor = requirerestor == null ? null : requirerestor.trim();
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization == null ? null : organization.trim();
    }

	/**  
	  * @Title:  getStatus <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getStatus() {
		return status;
	}

	/**  
	  * @Title:  setStatus <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */  
	public void setStatus(String status) {
		this.status = status;
	}

    public String getEtleftup() {
        return etleftup;
    }

    public void setEtleftup(String etleftup) {
        this.etleftup = etleftup;
    }

    public String getEtleftdown() {
        return etleftdown;
    }

    public void setEtleftdown(String etleftdown) {
        this.etleftdown = etleftdown;
    }

    public String getEtrightup() {
        return etrightup;
    }

    public void setEtrightup(String etrightup) {
        this.etrightup = etrightup;
    }

    public String getEtrightdown() {
        return etrightdown;
    }

    public void setEtrightdown(String etrightdown) {
        this.etrightdown = etrightdown;
    }

    public String getBbleftup() {
        return bbleftup;
    }

    public void setBbleftup(String bbleftup) {
        this.bbleftup = bbleftup;
    }

    public String getBbleftdown() {
        return bbleftdown;
    }

    public void setBbleftdown(String bbleftdown) {
        this.bbleftdown = bbleftdown;
    }

    public String getBbrightup() {
        return bbrightup;
    }

    public void setBbrightup(String bbrightup) {
        this.bbrightup = bbrightup;
    }

    public String getBbrightdown() {
        return bbrightdown;
    }

    public void setBbrightdown(String bbrightdown) {
        this.bbrightdown = bbrightdown;
    }

    public String getTemplantleftup() {
        return templantleftup;
    }

    public void setTemplantleftup(String templantleftup) {
        this.templantleftup = templantleftup;
    }

    public String getTemplantleftdown() {
        return templantleftdown;
    }

    public void setTemplantleftdown(String templantleftdown) {
        this.templantleftdown = templantleftdown;
    }

    public String getTemplantrightup() {
        return templantrightup;
    }

    public void setTemplantrightup(String templantrightup) {
        this.templantrightup = templantrightup;
    }

    public String getTemplantrightdown() {
        return templantrightdown;
    }

    public void setTemplantrightdown(String templantrightdown) {
        this.templantrightdown = templantrightdown;
    }
}
