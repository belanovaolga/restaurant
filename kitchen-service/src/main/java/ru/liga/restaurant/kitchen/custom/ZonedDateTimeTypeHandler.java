package ru.liga.restaurant.kitchen.custom;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.time.ZonedDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class ZonedDateTimeTypeHandler extends BaseTypeHandler<ZonedDateTime> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
                                    ZonedDateTime parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.toOffsetDateTime());
    }

    @Override
    public ZonedDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        OffsetDateTime offsetDateTime = rs.getObject(columnName, OffsetDateTime.class);
        return offsetDateTime != null ? offsetDateTime.atZoneSameInstant(ZoneId.systemDefault()) : null;
    }

    @Override
    public ZonedDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        OffsetDateTime offsetDateTime = rs.getObject(columnIndex, OffsetDateTime.class);
        return offsetDateTime != null ? offsetDateTime.atZoneSameInstant(ZoneId.systemDefault()) : null;
    }

    @Override
    public ZonedDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        OffsetDateTime offsetDateTime = cs.getObject(columnIndex, OffsetDateTime.class);
        return offsetDateTime != null ? offsetDateTime.atZoneSameInstant(ZoneId.systemDefault()) : null;
    }
}