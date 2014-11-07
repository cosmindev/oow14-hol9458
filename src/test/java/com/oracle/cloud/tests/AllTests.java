package com.oracle.cloud.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
//change
@RunWith(Suite.class)
@SuiteClasses({ TestCustomersBean.class, TestOrdersBean.class })
public class AllTests {

}
