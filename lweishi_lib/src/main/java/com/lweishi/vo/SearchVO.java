package com.lweishi.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchVO {

    private Long total; //总个数
    private Integer count; //每页显示条数
    private Integer page; //当前页
    private Integer total_page; //总页数
    private List<SearchItemVO> items; //当前页数据
}
