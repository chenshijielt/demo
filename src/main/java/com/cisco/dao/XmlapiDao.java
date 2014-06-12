package com.cisco.dao;

import com.cisco.vo.DailyView;
import com.cisco.vo.XMLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by chenshijie on 6/11/14.
 */
@Repository
public class XmlapiDao {

    @Autowired //自动注入JdbcTemplate的bean
    private JdbcTemplate jdbcTemplate;

    //报表的插入
    public int[] insertXmlapi(final List<XMLApi> list) {
        String sql = "INSERT INTO t_xmlapi (id,api_type,request_count,distinct_site,distinct_user,target_date) VALUES(null,?,?,?,?,?)";
        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int index) throws SQLException {
                XMLApi a = list.get(index);
                ps.setString(1, a.getApiType());
                ps.setLong(2, a.getRequestCount());
                ps.setLong(3, a.getDistinctSite());
                ps.setLong(4, a.getDistinctUser());
                ps.setDate(5, (Date) a.getTargetDate());
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
    }
    //报表的导出
    public List<XMLApi> getXmlapi() {
        String sql = "SELECT * FROM t_xmlapi";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<XMLApi>(
                XMLApi.class));

    }

    public List<DailyView> getDailyCallTotal(){
        String sql = "SELECT * FROM daily_viem";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<DailyView>(
                DailyView.class));
    }

    public List<XMLApi> getDailyCallAddCall(){
        String sql = "SELECT * FROM new_api_call_appear";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<XMLApi>(
                XMLApi.class));
    }

    public List<XMLApi> getDailyCallTotalALL(){
        String sql = "SELECT * FROM total_api_call";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<XMLApi>(
                XMLApi.class));
    }

}
