package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.pojo.Clazz;
import com.example.pojo.Student;
import com.example.service.StudentService;
import com.example.util.MD5;
import com.example.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "学生控制器")
@RestController
@RequestMapping("sms/studentController")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @ApiOperation("删除单个或多个学生信息")
    @DeleteMapping("/delStudentById")
    public Result delStudentById(
            @ApiParam("要删除的多个学生的JSON数组") @RequestBody List<Integer> ids){
        studentService.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("新增或修改学生信息")
    @PostMapping("addOrUpdateStudent")
    public Result addOrUpdateStudent(
            @ApiParam("JSON格式的学生信息") @RequestBody Student student){

        Integer id = student.getId();
        if(id == null || id == 0){
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        studentService.saveOrUpdate(student);
            return Result.ok();
    }

    @ApiOperation("分页带条件查询学生信息")
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(
            @ApiParam("分页查询的页码数") @PathVariable Integer pageNo,
            @ApiParam("分页查询的页大小") @PathVariable Integer pageSize,
            @ApiParam("分页查询的查询条件") Student student){
        //分页信息封装Page对象
        Page<Student> page = new Page<>(pageNo,pageSize);
        //进行查询
        IPage<Student> iPage = studentService.getStudentByOpr(page,student);
        //封装Result对象返回
        return Result.ok(iPage);
    }
}
