package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * @author centos7
 * @version 1.0
 * @description TODO 课程分类树型结构查询
 * @date 2023/1/18 11:40
 */
public interface CourseCategoryService {
    public List<CourseCategoryTreeDto> queryTreeNodes(String id);
}
