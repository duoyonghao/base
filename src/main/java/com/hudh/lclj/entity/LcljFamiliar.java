/**
 * @Fields 种植牙术后知情书 : (描述这个文件表示什么)
 */
package com.hudh.lclj.entity;

import java.io.Serializable;

/**
 * @version V1.0
 * @ClassName: LcljFamiliar
 * @Package com.hudh.lclj.entity
 * @author: dyh
 * @date: 2020/6/15 14:15
 */
public class LcljFamiliar implements Serializable {

    private String seqId;

    private String id;

    private String orderNumber;

    private String userCode;

    private String doctorSignature;

    private String patientSignature;

    private String patientTime;

    private String doctorTime;

    private String createUser;

    private String createTime;

    private String organization;

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId == null ? null : seqId.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getDoctorSignature() {
        return doctorSignature;
    }

    public void setDoctorSignature(String doctorSignature) {
        this.doctorSignature = doctorSignature == null ? null : doctorSignature.trim();
    }

    public String getPatientSignature() {
        return patientSignature;
    }

    public void setPatientSignature(String patientSignature) {
        this.patientSignature = patientSignature == null ? null : patientSignature.trim();
    }

    public String getPatientTime() {
        return patientTime;
    }

    public void setPatientTime(String patientTime) {
        this.patientTime = patientTime == null ? null : patientTime.trim();
    }

    public String getDoctorTime() {
        return doctorTime;
    }

    public void setDoctorTime(String doctorTime) {
        this.doctorTime = doctorTime == null ? null : doctorTime.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization == null ? null : organization.trim();
    }

    @Override
    public String toString() {
        return "LcljFamiliar{" +
                "seqId='" + seqId + '\'' +
                ", id='" + id + '\'' +
                ", doctorSignature='" + doctorSignature + '\'' +
                ", patientSignature='" + patientSignature + '\'' +
                ", patientTime='" + patientTime + '\'' +
                ", doctorTime='" + doctorTime + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime='" + createTime + '\'' +
                ", organization='" + organization + '\'' +
                '}';
    }
}