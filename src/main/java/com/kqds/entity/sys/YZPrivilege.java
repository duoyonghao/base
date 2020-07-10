package com.kqds.entity.sys;

import lombok.Data;

@Data
public class YZPrivilege {
    private Long id;
    private String belongs_to;
    private String user_seq_id;
    private String belongs_to_name;
    private String user_name;
}
