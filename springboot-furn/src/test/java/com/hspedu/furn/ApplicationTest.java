package com.hspedu.furn;

import com.hspedu.furn.bean.Furn;
import com.hspedu.furn.mapper.FurnMapper;
import com.hspedu.furn.service.FurnService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class ApplicationTest {

    @Resource
    private FurnMapper furnMapper;
    @Resource
    private FurnService furnService;

    @Test
    public void f1(){


    }
    @Test
    public void f2(){
        List<Furn> furns = furnService.list();
        for (Furn furn : furns) {
            System.out.println(furn);
        }




    }
}
