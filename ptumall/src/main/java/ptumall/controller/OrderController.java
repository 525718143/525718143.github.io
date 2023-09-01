package ptumall.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ptumall.model.Userorder;
import ptumall.service.OrderService;
import ptumall.utils.Result;
import ptumall.utils.ResultCodeEnum;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/all")
    public Result getAllOrder(@RequestParam(name = "uid") Integer uid,
                              @RequestParam(name = "pageNum") Integer pageNum,
                              @RequestParam(name = "pageSize") Integer pageSize)
    {
        PageInfo<Userorder> pageInfo = orderService.getAllOrder(uid,pageNum,pageSize);
        return Result.success(pageInfo);
    }

    @DeleteMapping("/deleteone/{id}")
    public Result deldeteOne(@PathVariable Integer id)
    {
        int res = orderService.deleteOneOrder(id);
        if(res >= 1)
        {
            return Result.success();
        }
        else
        {
            return Result.failure(ResultCodeEnum.FAIL,"删除失败！");
        }
    }

    @DeleteMapping("/deleteall/{uid}")
    public Result deldeteAll(@PathVariable Integer uid)
    {
        int res = orderService.deleteAllOrder(uid);
        if(res >= 1)
        {
            return Result.success();
        }
        else
        {
            return Result.failure(ResultCodeEnum.FAIL,"清空失败！");
        }
    }
}
