package org.smart4j.chapter2.service;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smart4j.chapter2.helper.DatabaseHelper;
import org.smart4j.chapter2.model.Customer;
import org.xml.sax.InputSource;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wuqinghua on 18/2/10.
 */
public class CustomerServiceTest {

    private CustomerService customerService;

    IDatabaseConnection conn = null;

    @Before
    public void setUp() throws Exception {
        customerService = new CustomerService();
        //备份数据库
        conn = new DatabaseDataSourceConnection(DatabaseHelper.getDataSource());
        IDataSet dataSet = conn.createDataSet();
        FlatXmlDataSet.write(dataSet, new FileOutputStream("backend.xml"));

        //清空数据库
        DatabaseOperation.DELETE_ALL.execute(conn,dataSet);
    }

    @After
    public void tearDown() throws Exception {
        //还原数据库
        IDataSet dataSet = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new
                FileInputStream("backend.xml"))));
        DatabaseOperation.CLEAN_INSERT.execute(conn, dataSet);
        if (conn != null) {
            conn.close();
        }
    }


    @Test
    public void queryCustomers01Test() throws Exception {
        //插入测试数据
        IDataSet dataSet = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(Thread
                .currentThread().getContextClassLoader().getResourceAsStream
                        ("org/smart4j/chapter2/service/customer.xml"))));
        DatabaseOperation.CLEAN_INSERT.execute(conn, dataSet);

        //测试查询所有
        List<Customer> customers = customerService.queryCustomers(new HashMap<>());
        Assert.assertTrue(customers.size() == 2);
    }

    @Test
    public void queryCustomers02Test() throws Exception {
        //插入测试数据
        IDataSet dataSet = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(Thread
                .currentThread().getContextClassLoader().getResourceAsStream
                        ("org/smart4j/chapter2/service/customer.xml"))));
        DatabaseOperation.CLEAN_INSERT.execute(conn, dataSet);

        //测试条件
        Map<String, Object> params = new HashMap<>();
        params.put("name", "customer1");
        List<Customer> customers = customerService.queryCustomers(params);
        Assert.assertTrue(customers.size() == 1);
        Assert.assertEquals("13512345678", customers.get(0).getTelephone());
    }


    @Test
    public void createCustomerTest() throws Exception{

        Map<String, Object> customer = new HashMap<>();
        customer.put("id", 1);
        customer.put("name", "customer1");
        customer.put("contact", "jack");
        customer.put("telephone", "1234567890");
        customerService.createCustomer(customer);

        List<Customer> customers = customerService.queryCustomers(new HashMap<>());
        Assert.assertTrue(customers.size() == 1);
        Assert.assertEquals("1234567890", customers.get(0).getTelephone());

    }


    @Test
    public void updateCustomerTest() throws Exception{
        //插入测试数据
        IDataSet dataSet = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(Thread
                .currentThread().getContextClassLoader().getResourceAsStream
                        ("org/smart4j/chapter2/service/customer.xml"))));
        DatabaseOperation.CLEAN_INSERT.execute(conn, dataSet);


        Map<String, Object> params = new HashMap<>();
        params.put("telephone","1234567890");
        customerService.updateCustomer(1,params);
        customerService.updateCustomer(2,params);

        List<Customer> customers = customerService.queryCustomers(new HashMap<>());
        Assert.assertTrue(customers.size() == 2);
        Assert.assertEquals("1234567890", customers.get(0).getTelephone());
    }


    @Test
    public void deleteCustomerTest() throws Exception{
        //插入测试数据
        IDataSet dataSet = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(Thread
                .currentThread().getContextClassLoader().getResourceAsStream
                        ("org/smart4j/chapter2/service/customer.xml"))));
        DatabaseOperation.CLEAN_INSERT.execute(conn, dataSet);
        List<Customer> customers = customerService.queryCustomers(new HashMap<>());
        Assert.assertTrue(customers.size() == 2);

        customerService.deleteCustomer(1);

        customers = customerService.queryCustomers(new HashMap<>());
        Assert.assertTrue(customers.size() == 1);

        Assert.assertTrue(customers.get(0).getId()==2);

    }
}
