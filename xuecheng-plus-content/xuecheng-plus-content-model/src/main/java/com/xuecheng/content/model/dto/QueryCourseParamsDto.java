package com.xuecheng.content.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author centos7
 * @version 1.0
 * @description TODO 课程查询参数DTO
 * @date 2023/1/17 16:15
 */
@Data
@ToString
public class QueryCourseParamsDto {
    //审核状态
    @ApiModelProperty("审核状态")
    private String auditStatus;
    //课程名称
    @ApiModelProperty("课程名称")
    private String courseName;
    //发布状态
    @ApiModelProperty("发布状态")
    private String publishStatus;
}
