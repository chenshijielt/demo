package com.cisco.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cisco.vo.Academy;

@Repository
public class PoiDao {

    @Autowired //自动注入JdbcTemplate的bean
    private JdbcTemplate jdbcTemplate;

    //报表的导出
    public List<Academy> getAcademy() {
        String sql = "SELECT * FROM t_academy";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Academy>(
                Academy.class));

    }

    //报表的插入
    public int[] insertAcademy(final List<Academy> list) {
        String sql = "INSERT INTO t_academy (academy_id,academy) VALUES(null,?)";
        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int index) throws SQLException {
                Academy a = list.get(index);
                ps.setString(1, a.getAcademy());
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
    }

}