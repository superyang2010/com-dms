/**
 * Copyright (C), 2015-2018, 浩鲸科技有限公司
 * FileName: BaseTest
 * Author:   zhouyl5
 * Date:     2018/9/14/014 14:40
 * Description: 单测基础类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.dms.admin.test;

import com.dms.admin.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * 〈单测基础类〉
 * @author yangchao
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
@Slf4j
public class BaseTest {


}