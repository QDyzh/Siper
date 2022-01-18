package com.jdbc;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.receive.Caipiao;

import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

/**
 * 	处理tkmybatis
 * @author sumhzehn
 */
public class MysqlUtil {
	private static SqlSessionFactory sqlSessionFactory;
	
	public static SqlSessionFactory getSqlSessionFactory() {
		if (sqlSessionFactory == null) {
			sqlSessionFactory = new SqlSessionFactoryBuilder()
					.build(Caipiao.class.getResourceAsStream("/mybatis-config.xml"));
		}
		return sqlSessionFactory;
	}
	
	/**
	 * 	获取 tkmybatis 处理后的 sqlSession
	 * @return
	 */
	public static SqlSession openSqlSession() {
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		MapperHelper mapperHelper = new MapperHelper();
		Config config = new Config();
		config.setIdentity("MYSQL");
		config.setEnableMethodAnnotation(true);
		config.setNotEmpty(true);
		config.setCheckExampleEntityClass(true);
		config.setUseSimpleType(true);
		config.setEnumAsSimpleType(true);
		config.setWrapKeyword("`{0}`");
		mapperHelper.setConfig(config);
		mapperHelper.registerMapper(Mapper.class);
		mapperHelper.processConfiguration(sqlSession.getConfiguration());
		return sqlSession;
	}
	
	public static void closeSqlSession(SqlSession sqlSession){
        sqlSession.commit();
        sqlSession.close();
        sqlSession=null;
    }
}
