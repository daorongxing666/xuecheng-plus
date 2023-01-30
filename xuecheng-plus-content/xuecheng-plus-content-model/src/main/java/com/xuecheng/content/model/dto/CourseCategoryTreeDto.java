package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author centos7
 * @version 1.0
 * @description TODO 分类树型结构dto
 * @date 2023/1/18 11:16
 */
@Data
public class CourseCategoryTreeDto extends CourseCategory implements Serializable {
    List childrenTreeNodes;
}
