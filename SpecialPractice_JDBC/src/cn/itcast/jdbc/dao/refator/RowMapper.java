package cn.itcast.jdbc.dao.refator;

import java.sql.ResultSet;

public interface RowMapper {

	public Object mapRow(ResultSet rs) throws Exception;
}
