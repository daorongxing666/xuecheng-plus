package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.execption.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.service.CourseBaseInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author centos7
 * @version 1.0
 * @description TODO 课程信息管理业务接口实现类
 * @date 2023/1/17 22:06
 */
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    @Autowired
    CourseBaseMapper courseBaseMapper;
    @Autowired
    CourseCategoryMapper courseCategoryMapper;
    @Autowired
    CourseMarketMapper courseMarketMapper;

    @Autowired
    CourseMarketServiceImpl courseMarketService;

    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams params, QueryCourseParamsDto queryCourseParamsDto) {

        // 构建查询条件
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();

        // 根据课程名称查询
        queryWrapper.like(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()), CourseBase::getName, queryCourseParamsDto.getCourseName());
        // 根据课程审核状态查询
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus()), CourseBase::getAuditStatus, queryCourseParamsDto.getAuditStatus());
        // 根据课程发布状态查询
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getPublishStatus()), CourseBase::getStatus, queryCourseParamsDto.getPublishStatus());

        // 分页查询
        Page<CourseBase> page = new Page<>(params.getPageNo(), params.getPageSize());

        // 获取结果
        Page<CourseBase> selectPage = courseBaseMapper.selectPage(page, queryWrapper);

        // 获取总条数
        long counts = selectPage.getTotal();

        // 获取数据集合
        List<CourseBase> items = selectPage.getRecords();

        PageResult<CourseBase> pageResult = new PageResult<CourseBase>(items, counts, params.getPageNo(), params.getPageSize());

        return pageResult;
    }

    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto) {
        //合法性校验
//        if (StringUtils.isBlank(dto.getName())) {
//            throw new RuntimeException("课程名称为空");
//        }
//
//        if (StringUtils.isBlank(dto.getMt())) {
//            throw new RuntimeException("课程分类为空");
//        }
//
//        if (StringUtils.isBlank(dto.getSt())) {
//            throw new RuntimeException("课程分类为空");
//        }
//
//        if (StringUtils.isBlank(dto.getGrade())) {
//            throw new RuntimeException("课程等级为空");
//        }
//
//        if (StringUtils.isBlank(dto.getTeachmode())) {
//            throw new RuntimeException("教育模式为空");
//        }
//
//        if (StringUtils.isBlank(dto.getUsers())) {
//            throw new RuntimeException("适应人群为空");
//        }
//
//        if (StringUtils.isBlank(dto.getCharge())) {
//            throw new RuntimeException("收费规则为空");
//        }

        // 新增对象
        CourseBase courseBase = new CourseBase();
        // 将填写的课程信息赋值给新增对象
        BeanUtils.copyProperties(dto, courseBase);
        // 设置审核状态
        courseBase.setAuditStatus("202002");
        // 设置发布状态
        courseBase.setStatus("203001");
        // 设置机构id
        courseBase.setCompanyId(companyId);
        // 设置发布时间
        courseBase.setCreateDate(LocalDateTime.now());
        // 插入课程基本信息表
        int baseInsert = courseBaseMapper.insert(courseBase);
        Long courseId = courseBase.getId();
        // 课程营销信息
        // 先根据课程id查询营销信息
        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(dto, courseMarket);
        courseMarket.setId(courseId);
        // 收费
        String charge = courseMarket.getCharge();
        // 收费课程必须写价格且价格大于0
        if (charge.equals("201001")) {
            Float price = dto.getPrice();
            if (price == null || price.floatValue() <= 0) {
                throw new RuntimeException("课程设置了收费价格不能为空且必须大于0");
            }
        }
        // 插入课程营销信息
        int marketInsert = courseMarketMapper.insert(courseMarket);
        if (baseInsert <= 0 || marketInsert <= 0) {
            throw new RuntimeException("新增课程基本信息失败");
        }
            //添加成功
            //返回添加的课程信息
            return getCourseBaseInfo(courseId);
    }


    @Transactional
    @Override
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto) {
        //业务规则校验，本机构只允许修改本机构的课程
        //课程id
        Long courseId = dto.getId();
        CourseBase courseBase_u = courseBaseMapper.selectById(courseId);
        if(courseBase_u==null){
            XueChengPlusException.cast("课程信息不存在");
        }
        if(!companyId.equals(courseBase_u.getCompanyId())){
            XueChengPlusException.cast("本机构只允许修改本机构的课程");
        }

        //封装数据
        //将请求参数拷贝到待修改对象中
        BeanUtils.copyProperties(dto,courseBase_u);
        courseBase_u.setChangeDate(LocalDateTime.now());
        //更新到数据库
        int i = courseBaseMapper.updateById(courseBase_u);

        //查询课程营销信息
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        if(courseMarket==null){
            courseMarket = new CourseMarket();
        }

        //判断是否收费
        String charge = dto.getCharge();
        if(charge.equals("201001")){
            Float price = dto.getPrice();
            if(price == null || price.floatValue()<=0){
                XueChengPlusException.cast("课程设置了收费价格不能为空且必须大于0");
            }
        }

        //将dto中的课程营销信息拷贝至courseMarket对象中
        BeanUtils.copyProperties(dto,courseMarket);

        boolean save = courseMarketService.saveOrUpdate(courseMarket);

        return getCourseBaseInfo(courseId);
    }

    //根据课程id查询课程基本信息，包括基本信息和营销信息
    public CourseBaseInfoDto getCourseBaseInfo(long courseId) {

        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        // 建立响应对象
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        if (courseBase == null || courseMarket == null) {
            throw new RuntimeException("无法获取新增信息");
        }
            // 响应对象传参
            BeanUtils.copyProperties(courseBase, courseBaseInfoDto);
            BeanUtils.copyProperties(courseMarket, courseBaseInfoDto);

        //查询分类名称
        CourseCategory courseCategoryBySt = courseCategoryMapper.selectById(courseBase.getSt());
        courseBaseInfoDto.setStName(courseCategoryBySt.getName());
        CourseCategory courseCategoryByMt = courseCategoryMapper.selectById(courseBase.getMt());
        courseBaseInfoDto.setMtName(courseCategoryByMt.getName());

        return courseBaseInfoDto;
    }
}
