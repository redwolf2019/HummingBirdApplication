package com.wqh.hummingbird.server.common;

import com.alibaba.fastjson.JSONArray;
import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.Objects;

public class JsonBinding implements Binding<Object, JSONArray> {
    @Override
    public Converter<Object, JSONArray> converter() {
        return new Converter<Object, JSONArray>() {
            @Override
            public JSONArray from(Object databaseObject) {
                return databaseObject == null ? new JSONArray() : JSONArray.parseArray((String) databaseObject);
            }

            @Override
            public Object to(JSONArray userObject) {
                return userObject == null ? new JSONArray().toJSONString() : userObject.toJSONString();
            }

            @Override
            public Class<Object> fromType() {
                return Object.class;
            }

            @Override
            public Class<JSONArray> toType() {
                return JSONArray.class;
            }
        };
    }

    @Override
    public void sql(BindingSQLContext<JSONArray> ctx) throws SQLException {
        if (ctx.render().paramType() == ParamType.INLINED) {
            ctx.render().visit(DSL.inline(ctx.convert(converter()).value())).sql("::json");
        } else {
            ctx.render().sql("?");
        }
    }

    @Override
    public void register(BindingRegisterContext<JSONArray> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
    }

    @Override
    public void set(BindingSetStatementContext<JSONArray> ctx) throws SQLException {
        ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
    }

    @Override
    public void set(BindingSetSQLOutputContext<JSONArray> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void get(BindingGetResultSetContext<JSONArray> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.resultSet().getString(ctx.index()));
    }

    @Override
    public void get(BindingGetStatementContext<JSONArray> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.statement().getString(ctx.index()));
    }

    @Override
    public void get(BindingGetSQLInputContext<JSONArray> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
