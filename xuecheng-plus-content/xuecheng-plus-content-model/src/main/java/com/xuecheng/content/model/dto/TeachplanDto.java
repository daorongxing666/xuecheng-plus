package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author centos7
 * @version 1.0
 * @description TODO 课程计划树型结构dto
 * @date 2023/1/30 14:25
 */
@Data
@ToString
public class TeachplanDto extends Teachplan {
    // 相关联媒资信息
    //课程计划关联的媒资信息
    TeachplanMedia teachplanMedia;

    //子结点
    List<TeachplanDto> teachPlanTreeNodes;
}
