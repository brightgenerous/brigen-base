package com.brightgenerous.orm.mapper;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Date;

class DerbyTypeComparator implements TypeComparator {

    @Override
    public boolean compare(Class<?> fieldType, String columnType) {
        boolean ret = false;
        if (fieldType.isPrimitive()) {
            if (fieldType.equals(Boolean.TYPE)) {
                if (columnType.toLowerCase().matches("^(bit|boolean)$")) {
                    ret = true;
                }
            } else if (fieldType.equals(Byte.TYPE)) {
                if (columnType.toLowerCase().matches("^tinyint$")) {
                    ret = true;
                }
            } else if (fieldType.equals(Short.TYPE)) {
                if (columnType.toLowerCase().matches("^smallint$")) {
                    ret = true;
                }
            } else if (fieldType.equals(Character.TYPE)) {
                // unsupported
            } else if (fieldType.equals(Integer.TYPE)) {
                if (columnType.toLowerCase().matches("^integer$")) {
                    ret = true;
                }
            } else if (fieldType.equals(Long.TYPE)) {
                if (columnType.toLowerCase().matches("^bigint$")) {
                    ret = true;
                }
            } else if (fieldType.equals(Float.TYPE)) {
                if (columnType.toLowerCase().matches("^real$")) {
                    ret = true;
                }
            } else if (fieldType.equals(Double.TYPE)) {
                if (columnType.toLowerCase().matches("^(float|double)$")) {
                    ret = true;
                }
            }
        } else {
            if (Boolean.class.isAssignableFrom(fieldType)) {
                if (columnType.toLowerCase().matches("^(bit|boolean)$")) {
                    ret = true;
                }
            } else if (Number.class.isAssignableFrom(fieldType)) {
                if (Byte.class.isAssignableFrom(fieldType)) {
                    if (columnType.toLowerCase().matches("^tinyint$")) {
                        ret = true;
                    }
                } else if (Short.class.isAssignableFrom(fieldType)) {
                    if (columnType.toLowerCase().matches("^smallint$")) {
                        ret = true;
                    }
                } else if (Integer.class.isAssignableFrom(fieldType)) {
                    if (columnType.toLowerCase().matches("^integer$")) {
                        ret = true;
                    }
                } else if (Long.class.isAssignableFrom(fieldType)) {
                    if (columnType.toLowerCase().matches("^bigint$")) {
                        ret = true;
                    }
                } else if (Float.class.isAssignableFrom(fieldType)) {
                    if (columnType.toLowerCase().matches("^real$")) {
                        ret = true;
                    }
                } else if (Double.class.isAssignableFrom(fieldType)) {
                    if (columnType.toLowerCase().matches("^(float|double)$")) {
                        ret = true;
                    }
                } else if (BigDecimal.class.isAssignableFrom(fieldType)) {
                    if (columnType.toLowerCase().matches("^(numeric|decimal)$")) {
                        ret = true;
                    }
                }
            } else if (Character.class.isAssignableFrom(fieldType)) {
                // unsupported
            } else if (String.class.isAssignableFrom(fieldType)) {
                if (columnType.toLowerCase().matches("^(char|varchar|longvarchar|text|clob)$")) {
                    ret = true;
                }
            } else if (Date.class.isAssignableFrom(fieldType)) {
                if (java.sql.Timestamp.class.isAssignableFrom(fieldType)) {
                    if (columnType.toLowerCase().matches("^timestamp$")) {
                        ret = true;
                    }
                } else if (java.sql.Time.class.isAssignableFrom(fieldType)) {
                    if (columnType.toLowerCase().matches("^time$")) {
                        ret = true;
                    }
                } else if (java.sql.Date.class.isAssignableFrom(fieldType)) {
                    if (columnType.toLowerCase().matches("^date$")) {
                        ret = true;
                    }
                } else {
                    if (columnType.toLowerCase().matches("^(date|time|timestamp)$")) {
                        ret = true;
                    }
                }
            } else if (fieldType.isArray()) {
                if (fieldType.getComponentType().equals(Byte.TYPE)) {
                    if (columnType.toLowerCase().matches("^(binary|varbinary|longvarbinary)$")) {
                        ret = true;
                    }
                }
            } else if (Blob.class.isAssignableFrom(fieldType)) {
                if (columnType.toLowerCase().matches("^blob$")) {
                    ret = true;
                }
            } else if (Clob.class.isAssignableFrom(fieldType)) {
                if (columnType.toLowerCase().matches("^clob$")) {
                    ret = true;
                }
            }
        }
        return ret;
    }
}
