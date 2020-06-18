/**

 */
USE [kqds]
GO

SET ANSI_PADDING ON
GO

/****** Object:  Index [NonClusteredIndex-20200618-134930]    Script Date: 2020/6/18 14:50:18 ******/
CREATE NONCLUSTERED INDEX [NonClusteredIndex-20200618-134930] ON [dbo].[KQDS_HOSPITAL_ORDER]
(
	[askperson] ASC,
	[starttime] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/**

 */