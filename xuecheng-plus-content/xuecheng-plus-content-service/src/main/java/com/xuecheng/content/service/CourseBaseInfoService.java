package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;

/**
 * @author centos7
 * @version 1.0
 * @description TODO 课程基本信息管理业务接口
 * @date 2023/1/17 22:04
 */
public interface CourseBaseInfoService {
    /***
     * @description TODO 课程查询接口
     * @param params 分页参数
     * @param queryCourseParamsDto 查询条件
     * @return com.xuecheng.base.model.PageResult<com.xuecheng.content.model.po.CourseBase>
     * @author centos7
     * @date 2023/1/17 22:07
    */
    public PageResult<CourseBase> queryCourseBaseList(PageParams params, QueryCourseParamsDto queryCourseParamsDto);

    /***
     * @description TODO 课程新增基本信息
     * @param addCourseDto
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto
     * @author centos7
     * @date 2023/1/18 14:34
    */
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

    /**
     * @description TODO 根据id查询课程信息
     * @param courseId
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto
     * @author centos7
     * @date 2023/1/30 11:20
    */
    public CourseBaseInfoDto getCourseBaseInfo(long courseId);

    /***
     * @description TODO 修改课程
     * @param companyId 用于校验
     * @param editCourseDto
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto
     * @author centos7
     * @date 2023/1/30 11:26
    */
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto);
}
