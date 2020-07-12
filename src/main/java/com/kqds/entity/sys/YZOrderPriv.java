package com.kqds.entity.sys;

import lombok.Data;

@Data
public class YZOrderPriv {
    private Long id;
    private String orderBelongsTo;
    private String orderBelongsToName;
    private String orderUserSeqId;
    private String orderUserName;
}
