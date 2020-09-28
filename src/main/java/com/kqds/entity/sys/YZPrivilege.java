package com.kqds.entity.sys;

import lombok.Data;

@Data
public class YZPrivilege {
    private Long id;
    private String belongsTo;
    private String userSeqId;
    private String belongsToName;
    private String userName;
}
