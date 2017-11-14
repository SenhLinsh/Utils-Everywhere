package com.linsh.lshutils.tools;

import android.database.DatabaseUtils;

import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/14
 *    desc   : 构建 SQL 查询条件筛选语句的帮助类
 *
 *           注: 该类直接参考或使用 https://github.com/tamir7/Contacts 中 Where 类里面的方法
 * </pre>
 */
public class LshWhereBuilder {

    private Where where;

    public LshWhereBuilder() {
        this.where = new Where();
    }

    public Where in(String key, List<?> objects) {
        return where.operate(key, objects, Operator.In);
    }

    public Where in(String key, String statement) {
        return where.operate(key, statement, Operator.In);
    }

    public Where notIn(String key, List<?> objects) {
        return where.operate(key, objects, Operator.NotIn);
    }

    public Where equalTo(String key, Object value) {
        return where.operate(key, toSafeString(value), Operator.Equal);
    }

    public Where startsWith(String key, Object prefix) {
        return where.operate(key, String.format("\'%s%%\'", new Object[]{prefix.toString()}), Operator.Like);
    }

    public Where endsWith(String key, Object suffix) {
        return where.operate(key, String.format("\'%%%s\'", new Object[]{suffix.toString()}), Operator.Like);
    }

    public Where contains(String key, Object substring) {
        return where.operate(key, String.format("\'%%%s%%\'", new Object[]{substring.toString()}), Operator.Like);
    }

    public Where doesNotStartWith(String key, Object prefix) {
        return where.operate(key, String.format("\'%s%%\'", new Object[]{prefix.toString()}), Operator.NotLike);
    }

    public Where notEqualTo(String key, Object value) {
        return where.operate(key, toSafeString(value), Operator.NotEqual);
    }

    public Where greaterThan(String key, Object value) {
        return where.operate(key, toSafeString(value), Operator.GreaterThan);
    }

    public Where greaterThanOrEqual(String key, Object value) {
        return where.operate(key, toSafeString(value), Operator.GreaterThanOrEqual);
    }

    public Where lessThan(String key, Object value) {
        return where.operate(key, toSafeString(value), Operator.LessThan);
    }

    public Where lessThanOrEqual(String key, Object value) {
        return where.operate(key, toSafeString(value), Operator.LessThanOrEqual);
    }

    public Where is(String key, Object value) {
        return where.operate(key, toSafeString(value), Operator.Is);
    }

    public Where isNot(String key, Object value) {
        return where.operate(key, toSafeString(value), Operator.IsNot);
    }

    public final class Where {

        private StringBuilder stringBuilder;
        private boolean or;

        private Where operate(String key, String value, Operator operator) {
            addWhere(getWhereStr(key, value, operator));
            return Where.this;
        }

        private Where operate(String key, List<?> objects, Operator operator) {
            addWhere(getWhereStr(key, objects, operator));
            return Where.this;
        }

        private void addWhere(StringBuilder where) {
            if (stringBuilder == null) {
                stringBuilder = where;
            } else if (or) {
                stringBuilder = or(where);
            } else {
                stringBuilder = and(where);
            }
        }

        private StringBuilder getWhereStr(String key, String value, Operator operator) {
            return (new StringBuilder(key)).append(operator.toString()).append(value);
        }

        private StringBuilder getWhereStr(String key, List<?> objects, Operator operator) {
            StringBuilder stringBuilder = (new StringBuilder(key)).append(operator).append("(");
            boolean first = true;

            Object o;
            for (Iterator var5 = objects.iterator(); var5.hasNext(); stringBuilder.append(toSafeString(o))) {
                o = var5.next();
                if (first) {
                    first = false;
                } else {
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append(")");
            return stringBuilder;
        }

        public LshWhereBuilder and() {
            or = false;
            return LshWhereBuilder.this;
        }

        public LshWhereBuilder or() {
            or = true;
            return LshWhereBuilder.this;
        }

        private StringBuilder and(StringBuilder andWhere) {
            return new StringBuilder(String.format("( %s AND %s )", new Object[]{this.stringBuilder.toString(), andWhere.toString()}));
        }

        private StringBuilder or(StringBuilder orWhere) {
            return new StringBuilder(String.format("( %s OR %s )", new Object[]{this.stringBuilder.toString(), orWhere.toString()}));
        }

        public String toString() {
            return this.stringBuilder.toString();
        }
    }

    private static String toSafeString(Object o) {
        return o instanceof String ? DatabaseUtils.sqlEscapeString(o.toString()) : o.toString();
    }

    private enum Operator {
        Equal("="),
        NotEqual("!="),
        GreaterThan(">"),
        GreaterThanOrEqual(">="),
        LessThan("<"),
        LessThanOrEqual("<="),
        Like(" LIKE "),
        NotLike(" NOT LIKE "),
        Is(" IS "),
        IsNot(" IS NOT "),
        In(" IN "),
        NotIn(" NOT IN ");

        private final String value;

        private Operator(String value) {
            this.value = value;
        }

        public String toString() {
            return this.value;
        }
    }
}
