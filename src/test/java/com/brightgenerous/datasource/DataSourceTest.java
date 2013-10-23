package com.brightgenerous.datasource;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.brightgenerous.datasource.mybatis.guice.bean.Header;
import com.brightgenerous.datasource.mybatis.guice.bean.MultiKeyDetail;
import com.brightgenerous.datasource.mybatis.guice.bean.SimpleKeyDetail;
import com.brightgenerous.datasource.mybatis.guice.mapper.Key;
import com.brightgenerous.datasource.mybatis.guice.transaction.BrigenTransaction;
import com.brightgenerous.orm.Condition;

public class DataSourceTest {

    @BeforeClass
    public static void before() throws Exception {
        DataSource.set(DerbyDataSource.class);
        DataSource.get().initialize();
    }

    @AfterClass
    public static void after() throws Exception {
        DataSource.get().destroy();
    }

    @Test
    public void test() throws Exception {
        BrigenTransaction bgt = DataSource.get().instance(BrigenTransaction.class);

        assertTrue(bgt.existsMapper());

        final int HEADERS = 15;
        final int MULTI_DETAILS = 5;
        final int SIMPLE_DETAILS = 20;

        for (int i = 0; i < HEADERS; i++) {
            Header header = new Header();

            // test Fill annotation
            assertNotNull(header.getMultiKeyDetails());
            assertNotNull(header.getSimpleKeyDetails());

            header.setHeaderValue(String.format("header-%02d", i));

            for (int j = 0; j < MULTI_DETAILS; j++) {
                MultiKeyDetail mkd = new MultiKeyDetail();
                mkd.setHeader(header);
                mkd.setDetailValue(String.format("multi-detail-%02d", j));
                header.getMultiKeyDetails().add(mkd);
            }
            for (int j = 0; j < SIMPLE_DETAILS; j++) {
                SimpleKeyDetail skd = new SimpleKeyDetail();
                skd.setHeader(header);
                skd.setDetailValue(String.format("simple-detail-%02d", j));
                header.getSimpleKeyDetails().add(skd);
            }

            bgt.add(header);
            Header stored = bgt.getHeaderDetails(header);

            assertFalse(header == stored);
            assertEquals(header.getHeaderNo(), stored.getHeaderNo());
            assertEquals(header.getHeaderValue(), stored.getHeaderValue());
        }

        // test header_no, detail_no
        for (int i = 0; i < HEADERS; i++) {
            Header header = new Header();
            header.setHeaderNo(Long.valueOf(i + 1));
            Header stored = bgt.getHeaderDetails(header);

            assertNotNull(stored);
            assertEquals(MULTI_DETAILS, stored.getMultiKeyDetails().size());
            assertEquals(SIMPLE_DETAILS, stored.getSimpleKeyDetails().size());
            for (int j = 0; j < MULTI_DETAILS; j++) {
                assertEquals(Long.valueOf(j + 1), stored.getMultiKeyDetails().get(j).getDetailNo());
            }
            for (int j = 0; j < SIMPLE_DETAILS; j++) {
                assertEquals(Long.valueOf((i * SIMPLE_DETAILS) + j + 1), stored
                        .getSimpleKeyDetails().get(j).getDetailNo());
            }
        }

        // test condition
        {
            Condition condition = new Condition();
            condition.getFields().get(Key.HEADER_VALUE).setSuffixValue("03"); // where header_value like '%' || '03'
            List<Header> headers = bgt.getHeaders(condition);

            assertEquals(1, headers.size());
            assertEquals("header-03", headers.get(0).getHeaderValue());
        }

        {
            Condition condition = new Condition();
            condition.getFields().get(Key.HEADER_VALUE).setPrefixValue("header-0"); // where header_value like 'header-0' || '%'
            condition.getSorts().prependAsc(Key.HEADER_NO);
            List<Header> headers = bgt.getHeaders(condition);

            assertEquals(10, headers.size());
            assertEquals("header-00", headers.get(0).getHeaderValue());
        }

        {
            Condition condition = new Condition();
            condition.getFields().get(Key.DETAIL_VALUE).setBroadValue("03"); // where detail_value like '%' || '03' || '%'
            condition.getSorts().prependAsc(Key.HEADER_NO, Key.DETAIL_NO); // order by header_no, detail_no
            List<MultiKeyDetail> mds = bgt.getMultiKeyDetails(condition);

            assertEquals(HEADERS, mds.size());
            for (int i = 0; i < HEADERS; i++) {
                assertEquals(String.format("header-%02d", i), mds.get(i).getHeader()
                        .getHeaderValue());
            }

            List<SimpleKeyDetail> sds = bgt.getSimpleKeyDetails(condition);

            assertEquals(HEADERS, sds.size());
            for (int i = 0; i < HEADERS; i++) {
                assertEquals(String.format("header-%02d", i), sds.get(i).getHeader()
                        .getHeaderValue());
            }
        }

        // test transaction rollback
        {
            Header header = new Header();
            header.setHeaderNo(Long.valueOf(3));
            Header stored = bgt.getHeaderDetails(header);

            assertEquals(MULTI_DETAILS, stored.getMultiKeyDetails().size());
            assertEquals(SIMPLE_DETAILS, stored.getSimpleKeyDetails().size());

            stored.getMultiKeyDetails().remove(0);
            stored.getSimpleKeyDetails().remove(0);
            stored.getSimpleKeyDetails().remove(0);

            RuntimeException rex = new RuntimeException(Header.class.getName());
            try {
                bgt.editHeaderDetails(stored, rex);
                fail();
            } catch (RuntimeException e) {
                // rollback done
                assertEquals(Header.class.getName(), e.getMessage());
            }
        }

        {
            Header header = new Header();
            header.setHeaderNo(Long.valueOf(3));
            Header stored = bgt.getHeaderDetails(header);

            assertEquals(MULTI_DETAILS, stored.getMultiKeyDetails().size());
            assertEquals(SIMPLE_DETAILS, stored.getSimpleKeyDetails().size());

            stored.getMultiKeyDetails().remove(0);
            stored.getSimpleKeyDetails().remove(0);
            stored.getSimpleKeyDetails().remove(0);

            bgt.editHeaderDetails(stored);

            stored = bgt.getHeaderDetails(header);

            assertEquals(MULTI_DETAILS - 1, stored.getMultiKeyDetails().size());
            assertEquals(SIMPLE_DETAILS - 2, stored.getSimpleKeyDetails().size());
        }
    }
}
