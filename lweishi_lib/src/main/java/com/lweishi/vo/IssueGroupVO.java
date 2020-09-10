package com.lweishi.vo;

import com.lweishi.model.Issue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName IssueGroupVO
 * @Description 返回问题组数据
 * @Author zzm
 * @Data 2020/9/10 11:12
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueGroupVO {
    private String id;
    private String name;
    private String icon;
    private List<Issue> issues;
}
