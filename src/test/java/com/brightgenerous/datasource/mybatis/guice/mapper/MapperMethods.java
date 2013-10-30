package com.brightgenerous.datasource.mybatis.guice.mapper;

import static com.brightgenerous.orm.mapper.MapperUtils.*;

import java.util.Collections;
import java.util.Map;

import com.brightgenerous.bean.BeanUtils;
import com.brightgenerous.datasource.mybatis.guice.bean.Header;
import com.brightgenerous.datasource.mybatis.guice.bean.MultiKeyDetail;
import com.brightgenerous.datasource.mybatis.guice.bean.SimpleKeyDetail;
import com.brightgenerous.orm.mapper.MapperUtils.FieldColumn;
import com.brightgenerous.orm.mapper.MapperUtils.PropertyReference;
import com.brightgenerous.orm.mapper.Register;
import com.brightgenerous.orm.mapper.TableDefines;

public class MapperMethods extends com.brightgenerous.orm.mapper.MapperMethods {

    @Override
    protected TableDefines getDefines() {
        return defines;
    }

    @Override
    public Map<Class<?>, String> getTargetTables() {
        return targetTables;
    }

    private static final Map<Class<?>, String> targetTables;

    private static final TableDefines defines;

    // @formatter:off
    static {
        Register register = new Register();
        {
            //
            // load(Register (Register), Bean Class (Class<?>), Table Name (String),
            //         Alias Table Names (String[], option),
            //         Primary Properties (Set<Entry<String[], Class<?>>>, option),
            //         Property-Column Mappings (FieldColumn, option));
            //
            // by load method, creates TableMapper and adds to Register.
            //     "TableMapper" means the relationship between Bean Class and Database Table.
            //         in other words, ORM.
            //     "Primary Properties" means primary key mapping "Database Table Columns" to "Bean Class Properties" each.
            //     "Property-Column" means mapping "Database Table Columns" to "Nested Bean Class Properties" each.
            //
            //     here, "Database Table Columns" are build from Logical Bean Class Properties.
            //         in brief, "Database Table Columns" are not reflected "Physical Database Table Columns".
            //
            load(register, Header.class,
                    "t_header",
                    BeanUtils.getPrimaryClassMap(Header.class));

            load(register, MultiKeyDetail.class,
                    "t_multi_key_detail",
                    new FieldColumn[] { FieldColumn.create("header.headerNo", "header_no") },
                    BeanUtils.getPrimaryClassMap(MultiKeyDetail.class));

            load(register, SimpleKeyDetail.class,
                    "t_simple_key_detail",
                    new FieldColumn[] { FieldColumn.create("header.headerNo", "header_no") },
                    BeanUtils.getPrimaryClassMap(SimpleKeyDetail.class));
        }

        {
            targetTables = Collections.unmodifiableMap(register.getTargetTables());
        }

        defines = new TableDefines();
        {
            //
            // define(TableDefines (TableDefines), Table Name (String),
            //         Property-Table Mappings (PropertyReference, option));
            //
            // by define method, creates TatbleDefine and adds to TableDefines.
            //     "TatbleDefine" means the relationship between "Target Table" and "Referenced Tables".
            //         "Target Table" means table Mapped to Bean Class.
            //             here, already loaded ORM, so it is obvious which Bean Class.
            //         "Referenced Tables" mean tables Mapped to Properties. Bean Class has the Properties.
            //
            define(defines, register,
                    "t_header");

            define(defines, register,
                    "t_multi_key_detail",
                    PropertyReference.create("header", "t_header"));

            define(defines, register,
                    "t_simple_key_detail",
                    PropertyReference.create("header", "t_header"));
        }
    }
    // @formatter:on
}
