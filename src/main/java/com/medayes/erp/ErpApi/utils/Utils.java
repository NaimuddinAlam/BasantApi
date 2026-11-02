package com.medayes.erp.ErpApi.utils;

import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

import java.util.Map;
import java.util.Objects;

public class Utils {
    public static void registerInParam(StoredProcedureQuery query, String name, Class<?> type, Object value) {
        query.registerStoredProcedureParameter(name, type, ParameterMode.IN);
        query.setParameter(name, value);
    }

    public  static String safe(String value) {
        return Objects.toString(value, "").trim();
    }

    public  static String safeUpper(String value) {
        return safe(value).toUpperCase();
    }

    public static String buildDebugSql(Map<String, Object> params, String procedure) {
        StringBuilder sql = new StringBuilder(procedure);
        boolean first = true;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (!first) sql.append(", ");
            sql.append("@").append(entry.getKey())
                    .append("='").append(entry.getValue() == null ? "" : entry.getValue()).append("'");
            first = false;
        }
        return sql.toString();
    }
}
