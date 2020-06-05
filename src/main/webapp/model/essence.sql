delete from sys_func where menu_id in ('601325','601326','600110','600310','600319','600306','600615');
delete from sys_button where seq_id in ('5d3e511f-4156-4377-802c-8aa2809a7a69','94c13123-a1a3-4156-87be-e350684f37bc','90867272-a993-4dc4-b94b-a68392108e90','4fa2e9d7-f7c4-49d3-9d08-f4d9c78150aa','khbb_bbxg-459a-b102-4bb0dfb87d83','khbb_jdbb-459a-b102-4bb0dfb87d83','khbb_mbxz-459a-b102-4bb0dfb87d83','khbb_plbb-459a-b102-4bb0dfb87d83');
delete from sys_func where menu_id like '%6008%';
delete from sys_button where parentid like '%6008%';
delete from sys_func where menu_id like '%6009%';
delete from sys_button where parentid like '%6009%';
delete from sys_func where menu_id like '%6015%';
delete from sys_button where parentid like '%6015%';
delete from sys_func where menu_id like '%6018%';
delete from sys_button where parentid like '%6018%';


INSERT INTO [dbo].[sys_para] ([SEQ_ID], [PARA_NAME], [PARA_VALUE], [remark], [createtime], [createuser]) VALUES (N'essence', N'essence', N'1', N'精华版', N'2017-09-12 11:46:48', N'1');
