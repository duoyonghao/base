package com.kqds.entity.base;

import java.io.Serializable;
import java.math.BigDecimal;

public class KqdsUserdocument implements Serializable {

    /**
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */
    //private static final long serialVersionUID = 1L;

    private String seqId;

    private String usercode;

    private String username;

    private String sex;

    private String birthday;

    private Integer age;

    private String profession;

    private String idcardno;

    private String phonenumber1;

    private String phonenumber2;

    private String guider;

    private String userimpress;

    private String introducer;

    private String platenumber;

    private String costlevel;

    private String dress;

    private String firstword;

    private String province;

    private String city;

    private String address;

    private String devchannel;

    private String createtime;

    private String createuser;

    private String askperson;

    private Integer isdelete;

    private String organization;

    private String medicalhistory;

    private String drugllergy;

    private String remark;

    private String experience;

    private String habit;

    private String email;

    private Integer type;

    private String xueli;

    private String qq;

    private String doctor;

    private String important;

    private String nexttype;

    private BigDecimal totalpay;

    private String developer;

    private Integer doorstatus;

    private String glr;

    private String glrremark;

    private String xgr;

    private String xgtime;

    private String town;

    private String area;//添加字段，记录地址到街道

    private Integer invoice;//开票标识

    private String kefu;

    private String usersort;

    private String openid;

    private String bindstatus;

    private String pym;

    private BigDecimal integral;

    private String blcode;

    private String barcode;

    //标识
    private String iscreateLclj;

    private String accepttool;

    //用于患者建档存手机号码分家人本人判断标识
    private String familyship;

    private String hobby;//添加字段、存储患者爱好

    private String activity;//添加字段、存储患者参加医院活动

    private String introduce;//添加字段、存储患者是否介绍他人来院

    private String clipAddress;//身份证住址

    private String nation;//民族

    private String certOrg;//发证机关

    private String effDate;//发证日期

    private String expDate;//换证失效日期

    private String headPic;//照片

    private String photoDisplay;//打印照片

    private String emergencyContact;//紧急联系人

    private String emergencyPhone;//紧急联系人电话

    private String contagion;//传染病

    private BigDecimal arrearage;//欠款

    public String getContagion() {
        return contagion;
    }

    public void setContagion(String contagion) {
        this.contagion = contagion;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getPhotoDisplay() {
        return photoDisplay;
    }

    public void setPhotoDisplay(String photoDisplay) {
        this.photoDisplay = photoDisplay;
    }

    /**
     * @Title: getNation <BR>
     * @Description: please write your description <BR>
     * @return: String <BR>
     */
    public String getNation() {
        return nation;
    }

    /**
     * @Title: setNation <BR>
     * @Description: please write your description <BR>
     * @return: String <BR>
     */
    public void setNation(String nation) {
        this.nation = nation;
    }

    /**
     * @Title: getCertOrg <BR>
     * @Description: please write your description <BR>
     * @return: String <BR>
     */
    public String getCertOrg() {
        return certOrg;
    }

    /**
     * @Title: setCertOrg <BR>
     * @Description: please write your description <BR>
     * @return: String <BR>
     */
    public void setCertOrg(String certOrg) {
        this.certOrg = certOrg;
    }

    /**
     * @Title: getEffDate <BR>
     * @Description: please write your description <BR>
     * @return: String <BR>
     */
    public String getEffDate() {
        return effDate;
    }

    /**
     * @Title: getClipAddress <BR>
     * @Description: please write your description <BR>
     * @return: String <BR>
     */
    public String getClipAddress() {
        return clipAddress;
    }

    /**
     * @Title: setClipAddress <BR>
     * @Description: please write your description <BR>
     * @return: String <BR>
     */
    public void setClipAddress(String clipAddress) {
        this.clipAddress = clipAddress;
    }

    /**
     * @Title: setEffDate <BR>
     * @Description: please write your description <BR>
     * @return: String <BR>
     */
    public void setEffDate(String effDate) {
        this.effDate = effDate;
    }

    /**
     * @Title: getExpDate <BR>
     * @Description: please write your description <BR>
     * @return: String <BR>
     */
    public String getExpDate() {
        return expDate;
    }

    /**
     * @Title: setExpDate <BR>
     * @Description: please write your description <BR>
     * @return: String <BR>
     */
    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    /**
     * @Title: getIscreateLclj <BR>
     * @Description: please write your description <BR>
     * @return: String <BR>
     */
    public String getIscreateLclj() {
        return iscreateLclj;
    }

    public String getFamilyship() {
        return familyship;
    }

    public void setFamilyship(String familyship) {
        this.familyship = familyship;
    }

    /**
     * @Title: setIscreateLclj <BR>
     * @Description: please write your description <BR>
     * @return: String <BR>
     */
    public void setIscreateLclj(String iscreateLclj) {
        this.iscreateLclj = iscreateLclj;
    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId == null ? null : seqId.trim();
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode == null ? null : usercode.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession == null ? null : profession.trim();
    }

    public String getIdcardno() {
        return idcardno;
    }

    public void setIdcardno(String idcardno) {
        this.idcardno = idcardno == null ? null : idcardno.trim();
    }

    public String getPhonenumber1() {
        return phonenumber1;
    }

    public void setPhonenumber1(String phonenumber1) {
        this.phonenumber1 = phonenumber1 == null ? null : phonenumber1.trim();
    }

    public String getPhonenumber2() {
        return phonenumber2;
    }

    public void setPhonenumber2(String phonenumber2) {
        this.phonenumber2 = phonenumber2 == null ? null : phonenumber2.trim();
    }

    public String getGuider() {
        return guider;
    }

    public void setGuider(String guider) {
        this.guider = guider == null ? null : guider.trim();
    }

    public String getUserimpress() {
        return userimpress;
    }

    public void setUserimpress(String userimpress) {
        this.userimpress = userimpress == null ? null : userimpress.trim();
    }

    public String getIntroducer() {
        return introducer;
    }

    public void setIntroducer(String introducer) {
        this.introducer = introducer == null ? null : introducer.trim();
    }

    public String getPlatenumber() {
        return platenumber;
    }

    public void setPlatenumber(String platenumber) {
        this.platenumber = platenumber == null ? null : platenumber.trim();
    }

    public String getCostlevel() {
        return costlevel;
    }

    public void setCostlevel(String costlevel) {
        this.costlevel = costlevel == null ? null : costlevel.trim();
    }

    public String getDress() {
        return dress;
    }

    public void setDress(String dress) {
        this.dress = dress == null ? null : dress.trim();
    }

    public String getFirstword() {
        return firstword;
    }

    public void setFirstword(String firstword) {
        this.firstword = firstword == null ? null : firstword.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getDevchannel() {
        return devchannel;
    }

    public void setDevchannel(String devchannel) {
        this.devchannel = devchannel == null ? null : devchannel.trim();
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

    public String getAskperson() {
        return askperson;
    }

    public void setAskperson(String askperson) {
        this.askperson = askperson == null ? null : askperson.trim();
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization == null ? null : organization.trim();
    }

    public String getMedicalhistory() {
        return medicalhistory;
    }

    public void setMedicalhistory(String medicalhistory) {
        this.medicalhistory = medicalhistory == null ? null : medicalhistory.trim();
    }

    public String getDrugllergy() {
        return drugllergy;
    }

    public void setDrugllergy(String drugllergy) {
        this.drugllergy = drugllergy == null ? null : drugllergy.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience == null ? null : experience.trim();
    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit == null ? null : habit.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getXueli() {
        return xueli;
    }

    public void setXueli(String xueli) {
        this.xueli = xueli == null ? null : xueli.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor == null ? null : doctor.trim();
    }

    public String getImportant() {
        return important;
    }

    public void setImportant(String important) {
        this.important = important == null ? null : important.trim();
    }

    public String getNexttype() {
        return nexttype;
    }

    public void setNexttype(String nexttype) {
        this.nexttype = nexttype == null ? null : nexttype.trim();
    }

    public BigDecimal getTotalpay() {
        return totalpay;
    }

    public void setTotalpay(BigDecimal totalpay) {
        this.totalpay = totalpay;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer == null ? null : developer.trim();
    }

    public Integer getDoorstatus() {
        return doorstatus;
    }

    public void setDoorstatus(Integer doorstatus) {
        this.doorstatus = doorstatus;
    }

    public String getGlr() {
        return glr;
    }

    public void setGlr(String glr) {
        this.glr = glr == null ? null : glr.trim();
    }

    public String getGlrremark() {
        return glrremark;
    }

    public void setGlrremark(String glrremark) {
        this.glrremark = glrremark == null ? null : glrremark.trim();
    }

    public String getXgr() {
        return xgr;
    }

    public void setXgr(String xgr) {
        this.xgr = xgr == null ? null : xgr.trim();
    }

    public String getXgtime() {
        return xgtime;
    }

    public void setXgtime(String xgtime) {
        this.xgtime = xgtime == null ? null : xgtime.trim();
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town == null ? null : town.trim();
    }

    public String getKefu() {
        return kefu;
    }

    public void setKefu(String kefu) {
        this.kefu = kefu == null ? null : kefu.trim();
    }

    public String getUsersort() {
        return usersort;
    }

    public void setUsersort(String usersort) {
        this.usersort = usersort == null ? null : usersort.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getBindstatus() {
        return bindstatus;
    }

    public void setBindstatus(String bindstatus) {
        this.bindstatus = bindstatus == null ? null : bindstatus.trim();
    }

    public String getPym() {
        return pym;
    }

    public void setPym(String pym) {
        this.pym = pym == null ? null : pym.trim();
    }

    public BigDecimal getIntegral() {
        return integral;
    }

    public void setIntegral(BigDecimal integral) {
        this.integral = integral;
    }

    public String getBlcode() {
        return blcode;
    }

    public void setBlcode(String blcode) {
        this.blcode = blcode == null ? null : blcode.trim();
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }


    public Integer getInvoice() {
        return invoice;
    }

    public void setInvoice(Integer invoice) {
        this.invoice = invoice;
    }

    public String getAccepttool() {
        return accepttool;
    }

    public void setAccepttool(String accepttool) {
        this.accepttool = accepttool;
    }

    @Override
    public String toString() {
        return "KqdsUserdocument [seqId=" + seqId + ", usercode=" + usercode + ", username=" + username + ", sex=" + sex
                + ", birthday=" + birthday + ", age=" + age + ", profession=" + profession + ", idcardno=" + idcardno
                + ", phonenumber1=" + phonenumber1 + ", phonenumber2=" + phonenumber2 + ", guider=" + guider
                + ", userimpress=" + userimpress + ", introducer=" + introducer + ", platenumber=" + platenumber
                + ", costlevel=" + costlevel + ", dress=" + dress + ", firstword=" + firstword + ", province="
                + province + ", city=" + city + ", address=" + address + ", devchannel=" + devchannel + ", createtime="
                + createtime + ", createuser=" + createuser + ", askperson=" + askperson + ", isdelete=" + isdelete
                + ", organization=" + organization + ", medicalhistory=" + medicalhistory + ", drugllergy=" + drugllergy
                + ", remark=" + remark + ", experience=" + experience + ", habit=" + habit + ", email=" + email
                + ", type=" + type + ", xueli=" + xueli + ", qq=" + qq + ", doctor=" + doctor + ", important="
                + important + ", nexttype=" + nexttype + ", totalpay=" + totalpay + ", developer=" + developer
                + ", doorstatus=" + doorstatus + ", glr=" + glr + ", glrremark=" + glrremark + ", xgr=" + xgr
                + ", xgtime=" + xgtime + ", town=" + town + ", area=" + area + ", invoice=" + invoice + ", kefu=" + kefu
                + ", usersort=" + usersort + ", openid=" + openid + ", bindstatus=" + bindstatus + ", pym=" + pym
                + ", integral=" + integral + ", blcode=" + blcode + ", barcode=" + barcode + ", iscreateLclj="
                + iscreateLclj + ", accepttool=" + accepttool + ", familyship=" + familyship + ", hobby=" + hobby
                + ", activity=" + activity + ", introduce=" + introduce + ", clipAddress=" + clipAddress + ", nation="
                + nation + ", certOrg=" + certOrg + ", effDate=" + effDate + ", expDate=" + expDate + "]";
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public BigDecimal getArrearage() {
        return arrearage;
    }

    public void setArrearage(BigDecimal arrearage) {
        this.arrearage = arrearage;
    }
}