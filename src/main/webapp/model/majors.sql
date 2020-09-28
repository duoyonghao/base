INSERT INTO sys_func(SEQ_ID,MENU_ID,FUNC_NAME,FUNC_CODE,ICON,ORDERNO)values('1qaz2','601325','手术室','/kqdsFront/yyzx/room/room.jsp','edit.gif',null);
INSERT INTO sys_func(SEQ_ID,MENU_ID,FUNC_NAME,FUNC_CODE,ICON,ORDERNO)values('1qaz3','601326','手术查询','/kqdsFront/yyzx/room/roomSearch.jsp','edit.gif',null);

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) 
VALUES (N'5d3e511f-4156-4377-802c-8aa2809a7a69', N'手术查询_生成报表', N'roomcx_scbb', N'生成报表', N'2017-09-24 12:33:23', N'1', N'601326', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) 
VALUES (N'94c13123-a1a3-4156-87be-e350684f37bc', N'今日手术', N'jrroom', N'今日手术', N'2017-09-24 12:33:23', N'1', N'6010', N'0');


INSERT INTO sys_func(SEQ_ID,MENU_ID,FUNC_NAME,FUNC_CODE,ICON,ORDERNO)values('216','600110','预约挂号','/kqdsFront/yyzx/netOrderSearch.jsp','edit.gif',0);
INSERT INTO sys_func(SEQ_ID,MENU_ID,FUNC_NAME,FUNC_CODE,ICON,ORDERNO)values('133','600310','就诊情况','/kqdsFront/reg/jzqk.jsp','edit.gif',null);

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) 
VALUES (N'90867272-a993-4dc4-b94b-a68392108e90', N'保存', N'bc', N'保存', N'2017-09-24 12:33:23', N'1', N'600310', N'0');

INSERT INTO sys_func(SEQ_ID,MENU_ID,FUNC_NAME,FUNC_CODE,ICON,ORDERNO)values('219','600319','病程提醒','/kqdsFront/reg/jzqk_tx.jsp','edit.gif',19);

INSERT INTO sys_func(SEQ_ID,MENU_ID,FUNC_NAME,FUNC_CODE,ICON,ORDERNO)values('200','600306','推广计划','/kqdsFront/tgjh/extension_visit.jsp','edit.gif',null);

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) 
VALUES (N'4fa2e9d7-f7c4-49d3-9d08-f4d9c78150aa', N'推广计划_生成报表', N'tgjh_scbb', N'生成报表', N'2017-09-24 12:33:23', N'1', N'600306', N'0');


INSERT INTO [dbo].[sys_func] ([SEQ_ID], [MENU_ID], [FUNC_NAME], [FUNC_CODE], [ICON], [ORDERNO], [createtime], [createuser]) VALUES (N'100', N'6008', N'营销中心', N'/kqdsFront/jdzx/frame/reception_center.jsp', N'marketing-icon', N'9', N'', N'');

INSERT INTO [dbo].[sys_func] ([SEQ_ID], [MENU_ID], [FUNC_NAME], [FUNC_CODE], [ICON], [ORDERNO], [createtime], [createuser]) VALUES (N'140', N'600801', N'客户管理', N'/kqdsFront/index/kfzx/user_manager_yxzx.jsp', N'', N'0', N'', N'');

INSERT INTO [dbo].[sys_func] ([SEQ_ID], [MENU_ID], [FUNC_NAME], [FUNC_CODE], [ICON], [ORDERNO], [createtime], [createuser]) VALUES (N'141', N'600805', N'活动记录', N'/kqdsFront/index/yxzx/active.jsp', N'edit.gif', N'1', N'', N'');

INSERT INTO [dbo].[sys_func] ([SEQ_ID], [MENU_ID], [FUNC_NAME], [FUNC_CODE], [ICON], [ORDERNO], [createtime], [createuser]) VALUES (N'4c7b1d99-9129-41f2-a3ca-92f2a39c3651', N'600803', N'回访管理', N'/kqdsFront/visit/user_visit.jsp', N'edit.gif', N'2', N'', N'');

INSERT INTO [dbo].[sys_func] ([SEQ_ID], [MENU_ID], [FUNC_NAME], [FUNC_CODE], [ICON], [ORDERNO], [createtime], [createuser]) VALUES (N'9e0d0cf5-5033-48a1-a0e2-ba2dc497095d', N'600804', N'今日患者', N'/kqdsFront/index/jdzx/jrhz_center.jsp?isyx=1', N'edit.gif', N'2', N'', N'');

INSERT INTO [dbo].[sys_func] ([SEQ_ID], [MENU_ID], [FUNC_NAME], [FUNC_CODE], [ICON], [ORDERNO], [createtime], [createuser]) VALUES (N'7e79b403-a9a1-42dd-90a0-f59f33ee9044', N'600810', N'工作量统计', N'/kqdsFront/index/bbzx/bb_gzl_index.jsp?isyx=1', N'edit.gif', N'10', N'', N'');

INSERT INTO [dbo].[sys_func] ([SEQ_ID], [MENU_ID], [FUNC_NAME], [FUNC_CODE], [ICON], [ORDERNO], [createtime], [createuser]) VALUES (N'53301b1a-3eec-4176-b83d-4117584f8aa8', N'600806', N'异业记录', N'/kqdsFront/index/yxzx/deindustry.jsp', N'edit.gif', N'1', N'', N'');

INSERT INTO [dbo].[sys_func] ([SEQ_ID], [MENU_ID], [FUNC_NAME], [FUNC_CODE], [ICON], [ORDERNO], [createtime], [createuser]) VALUES (N'83a0f090-2d51-4045-b92c-5b364b881131', N'600802', N'推广计划', N'/kqdsFront/tgjh/extension_visit.jsp', N'edit.gif', N'2', N'', N'');

INSERT INTO [dbo].[sys_func] ([SEQ_ID], [MENU_ID], [FUNC_NAME], [FUNC_CODE], [ICON], [ORDERNO], [createtime], [createuser]) VALUES (N'qaa01b1a-3eec-4176-b83d-4117584f8aa8', N'600807', N'媒体记录', N'/kqdsFront/index/yxzx/media.jsp', N'edit.gif', N'1', N'', N'');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'0b6e2dc3-33f5-46e6-9b06-f4f5b129ed1e', N'活动记录_收款明细_生成报表', N'hdjl_skmx_scbb', N'生成报表', N'2017-09-24 12:32:39', N'1', N'600805', N'1');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'0ee92db6-c3c1-449e-8146-13227e907bee', N'保存', N'bc', N'', N'2017-01-19 11:48:30', N'', N'600810', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'1qa9f48f-1ef1-451e-a553-da3def1d66cf', N'日收款明细_网电', N'xfmx_all_wd', N'日收款明细', N'2017-07-07 12:35:15', N'1', N'600804', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'1qac383c-ca84-4c7a-82c8-5840827ef6d0', N'日收款查询_网电', N'rskcx_wd', N'日收款查询', N'2017-07-07 12:35:15', N'1', N'600804', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'1qacd7ca-41f6-43fb-8633-816188b99f4d', N'日收款查询网电_生成报表', N'rskcx_scbb', N'生成报表', N'2017-07-07 12:35:15', N'1', N'600804', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'1qafbdde-4422-4c52-99c3-f5c763916698', N'日收款明细网电_生成报表', N'xfmx_all_wd_scbb', N'生成报表', N'2017-07-07 12:35:15', N'1', N'600804', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'2d3531cb-b7d2-4a0b-9355-5c84ed0131bf', N'营销中心_生成报表', N'wd_jrhz_scbb', N'生成报表', N'2017-09-01 12:52:39', N'', N'600804', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'6f006999-518d-483c-8101-e736a1b0094f', N'网电-新增', N'kf_wd_xz', N'网电-新增', N'2017-03-21 17:49:11', N'', N'600801', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'84109215-e148-4380-8337-102672131400', N'活动记录_生成报表', N'hdjl_scbb', N'生成报表', N'2017-09-20 09:56:22', N'1', N'600805', N'4');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'8b7bb98d-19d3-466a-b4e9-9090bea0ee57', N'保存', N'bc', N'保存', N'2017-07-25 09:31:27', N'1', N'600807', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'906457e5-6801-4f89-b515-ea84e1bcc0ad', N'保存', N'bc', N'', N'2017-01-19 11:48:09', N'', N'600805', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'a6eb8ee1-c337-493c-bec9-6784d657b91a', N'收款明细', N'hdjl_skmx', N'收款明细', N'2017-09-20 09:53:56', N'1', N'600805', N'2');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'ae064499-f5a7-4f48-9b7a-272b5676bfed', N'活动记录_咨询情况_生成报表', N'hdjl_zxqk_scbb', N'生成报表', N'2017-09-24 12:33:23', N'1', N'600805', N'1');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'c4c4b867-551d-497d-845d-ec2a461c462d', N'咨询情况', N'hdjl_zxqk', N'咨询情况', N'2017-09-20 09:53:17', N'1', N'600805', N'1');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'da73cbf3-1362-4e44-95b8-f0b24fb61bf6', N'网电-保存', N'kf_wd_bc', N'网电-保存', N'2017-03-21 17:49:39', N'', N'600801', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'e7e29c68-f47c-496f-97b3-77a11c762ac1', N'保存', N'bc', N'保存', N'2017-01-19 11:48:21', N'', N'600806', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'faa38626-2b36-459f-9928-6e95003e28c4', N'预约统计营销_生成报表', N'yytj_scbb', N'生成报表', N'2017-07-07 12:35:15', N'1', N'600810', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'hdjl4499-f5a7-4f48-9b7a-272b5676bfe1', N'活动记录_患者明细', N'hdjl_user', N'活动记录_患者明细', N'2017-09-24 12:33:23', N'1', N'600805', N'1');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'mt064499-f5a7-4f48-9b7a-272b5676bfed', N'媒体记录_咨询情况_生成报表', N'hdjl_zxqk_scbb', N'生成报表', N'2017-09-24 12:33:23', N'1', N'600807', N'1');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'mt6e2dc3-33f5-46e6-9b06-f4f5b129ed1e', N'媒体记录_收款明细_生成报表', N'hdjl_skmx_scbb', N'生成报表', N'2017-09-24 12:32:39', N'1', N'600807', N'1');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'mtjl4499-f5a7-4f48-9b7a-272b5676bfe2', N'媒体记录_患者明细', N'mtjl_user', N'媒体记录_患者明细', N'2017-09-24 12:33:23', N'1', N'600807', N'1');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'qaz09215-e148-4380-8337-102672131400', N'异业记录_生成报表', N'yyjl_scbb', N'生成报表', N'2017-09-20 09:56:22', N'1', N'600806', N'4');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'qaz4b867-551d-497d-845d-ec2a461c462d', N'咨询情况', N'yyjl_zxqk', N'咨询情况', N'2017-09-20 09:53:17', N'1', N'600806', N'1');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'qazb8ee1-c337-493c-bec9-6784d657b91a', N'收款明细', N'yyjl_skmx', N'收款明细', N'2017-09-20 09:53:56', N'1', N'600806', N'2');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'user4499-f5a7-4f48-9b7a-272b5676bfe1', N'活动记录_患者明细_生成报表', N'hdjl_user_scbb', N'生成报表', N'2017-09-24 12:33:23', N'1', N'600805', N'1');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'user4499-f5a7-4f48-9b7a-272b5676bfe2', N'媒体记录_患者明细_生成报表', N'hdjl_user_scbb', N'生成报表', N'2017-09-24 12:33:23', N'1', N'600807', N'1');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'user4499-f5a7-4f48-9b7a-272b5676bfe3', N'异业记录_患者明细_生成报表', N'hdjl_user_scbb', N'生成报表', N'2017-09-24 12:33:23', N'1', N'600806', N'1');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'wsx09215-e148-4380-8337-102672131400', N'媒体记录_生成报表', N'mtjl_scbb', N'生成报表', N'2017-09-20 09:56:22', N'1', N'600807', N'4');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'wsx4b867-551d-497d-845d-ec2a461c462d', N'咨询情况', N'mtjl_zxqk', N'咨询情况', N'2017-09-20 09:53:17', N'1', N'600807', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'wsxb8ee1-c337-493c-bec9-6784d657b91a', N'收款明细', N'mtjl_skmx', N'收款明细', N'2017-09-20 09:53:56', N'1', N'600807', N'2');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'yy064499-f5a7-4f48-9b7a-272b5676bfed', N'异业记录_咨询情况_生成报表', N'hdjl_zxqk_scbb', N'生成报表', N'2017-09-24 12:33:23', N'1', N'600806', N'1');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'yy6e2dc3-33f5-46e6-9b06-f4f5b129ed1e', N'异业记录_收款明细_生成报表', N'hdjl_skmx_scbb', N'生成报表', N'2017-09-24 12:32:39', N'1', N'600806', N'1');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'yyjl4499-f5a7-4f48-9b7a-272b5676bfe3', N'异业记录_患者明细', N'yyjl_user', N'异业记录_患者明细', N'2017-09-24 12:33:23', N'1', N'600806', N'1');


INSERT INTO [dbo].[sys_func] ([SEQ_ID], [MENU_ID], [FUNC_NAME], [FUNC_CODE], [ICON], [ORDERNO], [createtime], [createuser]) VALUES (N'198', N'600903', N'推广计划', N'/kqdsFront/tgjh/extension_visit.jsp', N'edit.gif', null, N'', N'');

INSERT INTO [dbo].[sys_func] ([SEQ_ID], [MENU_ID], [FUNC_NAME], [FUNC_CODE], [ICON], [ORDERNO], [createtime], [createuser]) VALUES (N'320027d5-4568-4d38-a21b-49519201fe3c', N'600930', N'精确查询', N'/kqdsFront/index/kfzx/user_manager_jq.jsp', N'edit.gif', N'30', N'', N'');

INSERT INTO [dbo].[sys_func] ([SEQ_ID], [MENU_ID], [FUNC_NAME], [FUNC_CODE], [ICON], [ORDERNO], [createtime], [createuser]) VALUES (N'101', N'6009', N'网电中心', N'/kqdsFront/jdzx/frame/yyzx.jsp', N'order-icon', N'10', N'', N'');

INSERT INTO [dbo].[sys_func] ([SEQ_ID], [MENU_ID], [FUNC_NAME], [FUNC_CODE], [ICON], [ORDERNO], [createtime], [createuser]) VALUES (N'143', N'600901', N'客户管理', N'/kqdsFront/index/kfzx/user_manager_wdzx.jsp', N'', N'0', N'', N'');

INSERT INTO [dbo].[sys_func] ([SEQ_ID], [MENU_ID], [FUNC_NAME], [FUNC_CODE], [ICON], [ORDERNO], [createtime], [createuser]) VALUES (N'144', N'600905', N'回访管理', N'/kqdsFront/visit/user_visit.jsp', N'edit.gif', null, N'', N'');

INSERT INTO [dbo].[sys_func] ([SEQ_ID], [MENU_ID], [FUNC_NAME], [FUNC_CODE], [ICON], [ORDERNO], [createtime], [createuser]) VALUES (N'10425968-5ef3-4310-a354-9b736bb60c60', N'600915', N'今日预约', N'/kqdsFront/index/kfzx/wdyySearch.jsp', N'edit.gif', null, N'', N'');

INSERT INTO [dbo].[sys_func] ([SEQ_ID], [MENU_ID], [FUNC_NAME], [FUNC_CODE], [ICON], [ORDERNO], [createtime], [createuser]) VALUES (N'217', N'600925', N'今日患者', N'/kqdsFront/index/jdzx/jrhz_center.jsp', N'edit.gif', N'4', N'', N'');

INSERT INTO [dbo].[sys_func] ([SEQ_ID], [MENU_ID], [FUNC_NAME], [FUNC_CODE], [ICON], [ORDERNO], [createtime], [createuser]) VALUES (N'faa38626-2b36-459f-9928-6e95003e28c4', N'600910', N'工作量统计', N'/kqdsFront/index/bbzx/bb_gzl_index.jsp', N'edit.gif', N'10', N'', N'');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'14bb9429-e893-40b2-8fba-c2f9bc9457d2', N'修改关联人', N'xgglr', N'修改关联人', N'2017-05-04 20:50:14', N'', N'600901', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'156120bd-3706-48c2-a018-2a0758d747d7', N'网电-新增', N'kf_wd_xz', N'网电-新增', N'2017-03-21 17:49:50', N'', N'600901', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'4e7a793c-51a2-4998-8914-06fb71b46e9b', N'网电-保存', N'kf_wd_bc', N'网电-保存', N'2017-03-21 17:50:01', N'', N'600901', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'5a8d9897-b6f4-4142-88f0-d9756f8b3027', N'日收款查询_网电', N'rskcx_wd', N'日收款查询', N'2017-07-02 21:37:17', N'', N'600925', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'7e79b403-a9a1-42dd-90a0-f59f33ee9044', N'预约统计网电_生成报表', N'yytj_scbb', N'生成报表', N'2017-07-07 12:35:15', N'1', N'600915', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'823c383c-ca84-4c7a-82c8-5840827ef6d0', N'日收款明细_网电', N'xfmx_all_wd', N'日收款明细', N'2017-07-07 12:35:15', N'1', N'600925', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'a4147e74-be79-40a0-90e5-36ec5957ca4a', N'生成报表', N'scbb', N'生成报表', N'2017-05-04 20:50:28', N'', N'600901', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'b8c09f76-d23f-4dd0-b3ce-690390c9caff', N'网电中心_生成报表', N'wd_jrhz_scbb', N'生成报表', N'2017-09-01 12:53:12', N'', N'600925', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'ba13df6d-02b8-424e-8128-4db831a6e982', N'回访管理_生成报表', N'hfzx_scbb', N'生成报表', N'2017-06-25 17:17:15', N'', N'600905', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'bc5fbdde-4422-4c52-99c3-f5c763916698', N'日收款明细网电_生成报表', N'xfmx_all_wd_scbb', N'生成报表', N'2017-07-07 12:35:15', N'1', N'600925', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'cea93719-d97b-4f69-9198-e99fb1da5982', N'关联人_生成报表', N'glr_scbb', N'生成报表', N'2017-06-25 16:49:43', N'', N'600901', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'e73a3b41-a0c0-45b3-ace1-1920fad3b171', N'推广计划_生成报表', N'tgjh_scbb', N'生成报表', N'2017-06-25 17:11:35', N'', N'600903', N'0');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'f19cd7ca-41f6-43fb-8633-816188b99f4d', N'日收款查询网电_生成报表', N'rskcx_scbb', N'生成报表', N'2017-07-07 12:35:15', N'1', N'600925', N'0');

INSERT INTO [dbo].[sys_func] ([SEQ_ID], [MENU_ID], [FUNC_NAME], [FUNC_CODE], [ICON], [ORDERNO], [createtime], [createuser]) VALUES (N'215', N'600615', N'客户报备', N'/kqdsFront/index/jdzx/xxbb_center.jsp', N'edit.gif', N'5', N'', N'');


INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'khbb_bbxg-459a-b102-4bb0dfb87d83', N'客户报备_报备修改', N'khbb_bbxg', N'客户报备_报备修改', N'2017-07-07 12:35:15', N'1', N'600615', N'2');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'khbb_jdbb-459a-b102-4bb0dfb87d83', N'客户报备_建档报备', N'khbb_jdbb', N'客户报备_建档报备', N'2017-07-07 12:35:15', N'1', N'600615', N'1');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'khbb_mbxz-459a-b102-4bb0dfb87d83', N'客户报备_模板下载', N'khbb_mbxz', N'客户报备_模板下载', N'2017-07-07 12:35:15', N'1', N'600615', N'3');

INSERT INTO [dbo].[sys_button] ([SEQ_ID], [NAME], [QX_NAME], [BZ], [createtime], [createuser], [parentid], [sortno]) VALUES (N'khbb_plbb-459a-b102-4bb0dfb87d83', N'客户报备_批量报备', N'khbb_plbb', N'客户报备_批量报备', N'2017-07-07 12:35:15', N'1', N'600615', N'4');


INSERT INTO [dbo].[sys_para] ([SEQ_ID], [PARA_NAME], [PARA_VALUE], [remark], [createtime], [createuser]) VALUES (N'majors', N'majors', N'1', N'专业版', N'2017-09-12 11:46:48', N'1');
