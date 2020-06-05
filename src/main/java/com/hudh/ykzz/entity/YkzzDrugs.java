package com.hudh.ykzz.entity;

import java.math.BigDecimal;

public class YkzzDrugs {
	private String id;
	private String order_no; // 编号
	private String good_name; // 药品名称（药品商品名）
	private String charge_typeone; // 收费项目一级分类
	private String change_typetwo; // 收费项目二级分类
	private String company; // 单位
	private BigDecimal nuit_price; // 单价
	private String discount; // 折扣
	private String special; // 特殊项目
	private String picking; // 领料拆分（默认否）
	private String good_type; // 项目分类
	private String comments; // 药品介绍（该字段现改为保存条件）
	private String discount_info; // 优惠信息
	private String chemistry_name; // 药品化学名
	private String specifica_id; // 规格ID
	private String drugs_typeone; // 一级分类（药品类型对应类型表id）
	private String drugs_typetwo; // 二级分类（药品子类型对应类型表id）
	private String packing_num; // 包装数量
	private String company_min; // 最小单位
	private String content_coe; // 含量系数
	private String content_unit; // 含量单位
	private String sta_item; // 统计大项目
	private String contry_code; // 国家编码
	private String pharm_class; // 药理分类
	private String pharm_dos; // 药品剂型
	private String pharm_spec; // 药品规格
	private String ant_gra; // 抗生素级别
	private String psy_drugs; // 精神药品
	private String drug_identify; // 药品标识
	private String manu_id;// 供应商(对应供应商表id)
	private String manufac_id;// 生产商(对应生产商表id)
	private String userflag;// 药品是否禁用
	
	public String getUserflag() {
		return userflag;
	}
	public void setUserflag(String userflag) {
		this.userflag = userflag;
	}
	public String getManufac_id() {
		return manufac_id;
	}
	public void setManufac_id(String manufac_id) {
		this.manufac_id = manufac_id;
	}
	private String createtime;
	private String creator;
	private String organization;//添加字段 
	
	private String drugs_type;//添加字段 （药品分类）
	private String classify;//1：高危药品、2：抗菌素、3：其他
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public String getDrugs_type() {
		return drugs_type;
	}
	public void setDrugs_type(String drugs_type) {
		this.drugs_type = drugs_type;
	}
	public Integer getDrug_total_num() {
		return drug_total_num;
	}
	public void setDrug_total_num(Integer drug_total_num) {
		this.drug_total_num = drug_total_num;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	private String minnums;// 添加字段 上限
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getGood_name() {
		return good_name;
	}
	public void setGood_name(String good_name) {
		this.good_name = good_name;
	}
	public String getCharge_typeone() {
		return charge_typeone;
	}
	public void setCharge_typeone(String charge_typeone) {
		this.charge_typeone = charge_typeone;
	}
	public String getChange_typetwo() {
		return change_typetwo;
	}
	public void setChange_typetwo(String change_typetwo) {
		this.change_typetwo = change_typetwo;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public BigDecimal getNuit_price() {
		return nuit_price;
	}
	public void setNuit_price(BigDecimal nuit_price) {
		this.nuit_price = nuit_price;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getSpecial() {
		return special;
	}
	public void setSpecial(String special) {
		this.special = special;
	}
	public String getPicking() {
		return picking;
	}
	public void setPicking(String picking) {
		this.picking = picking;
	}
	public String getGood_type() {
		return good_type;
	}
	public void setGood_type(String good_type) {
		this.good_type = good_type;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getDiscount_info() {
		return discount_info;
	}
	public void setDiscount_info(String discount_info) {
		this.discount_info = discount_info;
	}
	public String getChemistry_name() {
		return chemistry_name;
	}
	public void setChemistry_name(String chemistry_name) {
		this.chemistry_name = chemistry_name;
	}
	public String getSpecifica_id() {
		return specifica_id;
	}
	public void setSpecifica_id(String specifica_id) {
		this.specifica_id = specifica_id;
	}
	public String getDrugs_typeone() {
		return drugs_typeone;
	}
	public void setDrugs_typeone(String drugs_typeone) {
		this.drugs_typeone = drugs_typeone;
	}
	public String getDrugs_typetwo() {
		return drugs_typetwo;
	}
	public void setDrugs_typetwo(String drugs_typetwo) {
		this.drugs_typetwo = drugs_typetwo;
	}
	public String getPacking_num() {
		return packing_num;
	}
	public void setPacking_num(String packing_num) {
		this.packing_num = packing_num;
	}
	public String getCompany_min() {
		return company_min;
	}
	public void setCompany_min(String company_min) {
		this.company_min = company_min;
	}
	public String getContent_coe() {
		return content_coe;
	}
	public void setContent_coe(String content_coe) {
		this.content_coe = content_coe;
	}
	public String getContent_unit() {
		return content_unit;
	}
	public void setContent_unit(String content_unit) {
		this.content_unit = content_unit;
	}
	public String getSta_item() {
		return sta_item;
	}
	public void setSta_item(String sta_item) {
		this.sta_item = sta_item;
	}
	public String getContry_code() {
		return contry_code;
	}
	public void setContry_code(String contry_code) {
		this.contry_code = contry_code;
	}
	public String getPharm_class() {
		return pharm_class;
	}
	public void setPharm_class(String pharm_class) {
		this.pharm_class = pharm_class;
	}
	public String getPharm_dos() {
		return pharm_dos;
	}
	public void setPharm_dos(String pharm_dos) {
		this.pharm_dos = pharm_dos;
	}
	public String getPharm_spec() {
		return pharm_spec;
	}
	public void setPharm_spec(String pharm_spec) {
		this.pharm_spec = pharm_spec;
	}
	public String getAnt_gra() {
		return ant_gra;
	}
	public void setAnt_gra(String ant_gra) {
		this.ant_gra = ant_gra;
	}
	public String getPsy_drugs() {
		return psy_drugs;
	}
	public void setPsy_drugs(String psy_drugs) {
		this.psy_drugs = psy_drugs;
	}
	public String getDrug_identify() {
		return drug_identify;
	}
	public void setDrug_identify(String drug_identify) {
		this.drug_identify = drug_identify;
	}
	public String getManu_id() {
		return manu_id;
	}
	public void setManu_id(String manu_id) {
		this.manu_id = manu_id;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getMinnums() {
		return minnums;
	}
	public void setMinnums(String minnums) {
		this.minnums = minnums;
	}
	public String getMingj() {
		return mingj;
	}
	public void setMingj(String mingj) {
		this.mingj = mingj;
	}
	public String getMaxnums() {
		return maxnums;
	}
	public void setMaxnums(String maxnums) {
		this.maxnums = maxnums;
	}
	public String getMaxgj() {
		return maxgj;
	}
	public void setMaxgj(String maxgj) {
		this.maxgj = maxgj;
	}
	
	public BigDecimal getDrgs_total_money() {
		return drgs_total_money;
	}
	public void setDrgs_total_money(BigDecimal drgs_total_money) {
		this.drgs_total_money = drgs_total_money;
	}
	
	public String getPacking_unit() {
		return packing_unit;
	}
	public void setPacking_unit(String packing_unit) {
		this.packing_unit = packing_unit;
	}
	private String mingj;// 添加字段
	private String maxnums;// 添加字段 下限
	private String maxgj;// 添加字段
	private Integer drug_total_num;// 药品总数量 // 添加字段
	private BigDecimal drgs_total_money;// 药品总金额 // 添加字段
	private BigDecimal drug_retail_price;// 药品零售价 // 添加字段
	public BigDecimal getDrug_retail_price() {
		return drug_retail_price;
	}
	public void setDrug_retail_price(BigDecimal drug_retail_price) {
		this.drug_retail_price = drug_retail_price;
	}
	private String packing_unit;// 添加字段
	
	

}
