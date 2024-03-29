package ptumall.service;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import ptumall.model.Goods;

@Service
public interface GoodsService {
    PageInfo<Goods> getAllgoods(Integer pageNum, Integer pageSize);

    Goods getGoodById(Integer gid);

    PageInfo<Goods> searchGoodsByName(String gname, Integer pageNum, Integer pageSize);

}
