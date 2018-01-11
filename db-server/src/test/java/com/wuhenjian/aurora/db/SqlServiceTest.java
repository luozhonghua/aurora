package com.wuhenjian.aurora.db;

import com.wuhenjian.aurora.db.mapper.sql.PhpProjectMapper;
import com.wuhenjian.aurora.utils.entity.dao.PhpProject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author 無痕剑
 * @date 2018/1/11 16:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SqlServiceTest {

	@Autowired
	private PhpProjectMapper phpProjectMapper;

	@Test
	public void phpProjectTest() {
		PhpProject p = new PhpProject();
		p.setAcctountName("asd");
		p.setRepoName("rt");
		p.setUpdateTime(new Date());
		phpProjectMapper.insertSelective(p);
	}
}
