package com.zuoyue.weiyang.handler;

import com.zuoyue.weiyang.enums.RoleType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleTypeTypeHandler extends BaseTypeHandler<RoleType> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, RoleType parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public RoleType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return RoleType.valueOf(rs.getInt(columnName));
    }

    @Override
    public RoleType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return RoleType.valueOf(rs.getInt(columnIndex));
    }

    @Override
    public RoleType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return RoleType.valueOf(cs.getInt(columnIndex));
    }
}
