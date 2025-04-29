package ru.liga.restaurant.kitchen.custom;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Кастомный TypeHandler для преобразования между ZonedDateTime и OffsetDateTime в JDBC
 * Используется MyBatis для корректной работы с временными зонами в БД
 */
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