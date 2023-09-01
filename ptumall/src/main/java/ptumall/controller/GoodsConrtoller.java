package ptumall.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ptumall.model.Goods;
import ptumall.service.GoodsService;
import ptumall.utils.Result;

@RestController
@RequestMapping("/goods")
public class GoodsConrtoller {
    @Autowired
    GoodsService goodsService;

    @GetMapping("/all")
    public Result getAllgoods(@RequestParam(name = "pageNum") Integer pageNum,
                              @RequestParam(name = "pageSize") Integer pageSize)
    {
        PageInfo<Goods> pageInfo = goodsService.getAllgoods(pageNum,pageSize);
        return Result.success(pageInfo);
    }
    @GetMapping("/detail/{gid}")
    public Result detail( @PathVariable(name = "gid")Integer gid)
    {
        Goods goods = goodsService.getGoodById(gid);
        return Result.success(goods);
    }

    @GetMapping("/search")
    public Result search( @RequestParam(name = "gname") String gname,
                          @RequestParam(name = "pageNum") Integer pageNum,
                          @RequestParam(name = "pageSize") Integer pageSize)
    {
        PageInfo<Goods> pageInfo = goodsService.searchGoodsByName(gname,pageNum,pageSize);
        return Result.success(pageInfo);
    }

}
