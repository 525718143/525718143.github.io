package com.hspedu.furn.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hspedu.furn.bean.Furn;
import com.hspedu.furn.service.FurnService;
import com.hspedu.furn.util.Result;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
public class FurnController {

    @Resource
    private FurnService furnService;



    @PostMapping("/save")
    public Result save(@Validated @RequestBody Furn furn, Errors errors){
        HashMap<String, Object> map = new HashMap<>();
        List<FieldError> fieldErrors = errors.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            map.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        if (map.isEmpty()){
            furnService.save(furn);
            return Result.success();
        }else {
            return Result.error("400","后端校验失败",map);
        }


    }

    @RequestMapping("/furns")
    public Result listFurns(){
        List<Furn> furns = furnService.list();
        return Result.success(furns);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Furn furn){
        furnService.updateById(furn);
        return Result.success();
    }


    @DeleteMapping ("/del/{id}")
    public Result del(@PathVariable Integer id){
        furnService.removeById(id);
        return Result.success();
    }

    @RequestMapping("/select/{id}")
    public Result select(@PathVariable Integer id){
        Furn furn = furnService.getById(id);
        return Result.success(furn);
    }


    @RequestMapping("/furnsByPage")
    public Result listFurnsByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "3") Integer  pageSize){


        Page<Furn> page = furnService.page(new Page<>(pageNum, pageSize));
        return Result.success(page);
    }

//    @RequestMapping("/furnsBySearchPage")
//    public Result listFurnsByConditionPage(@RequestParam(defaultValue = "1") Integer pageNum,
//                                           @RequestParam(defaultValue = "3") Integer  pageSize,
//                                           @RequestParam(defaultValue = "") String search){
//        QueryWrapper<Furn> query = Wrappers.query();
//        if (StringUtils.hasText(search)){
//            query.like("name",search);
//        }
//        Page<Furn> page = furnService.page(new Page<>(pageNum, pageSize), query);
//        return Result.success(page);
//    }
        @RequestMapping("/furnsBySearchPage")
        public Result listFurnsByConditionPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "3") Integer  pageSize,
                                               @RequestParam(defaultValue = "") String search){

            LambdaQueryWrapper<Furn> lambdaQuery = Wrappers.<Furn>lambdaQuery();
            if (StringUtils.hasText(search)){
                lambdaQuery.like(Furn::getName,search);
            }

            Page<Furn> page = furnService.page(new Page<>(pageNum, pageSize), lambdaQuery);
            return Result.success(page);
        }


}
