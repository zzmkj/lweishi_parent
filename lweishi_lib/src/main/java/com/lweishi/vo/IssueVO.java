package com.lweishi.vo;

import com.lweishi.model.Issue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName IssueVO
 * @Description 返回前端问题列表
 * @Author zzm
 * @Data 2020/9/10 10:12
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueVO {

    private List<Issue> others;
    private List<IssueGroupVO> issueGroups;
}
