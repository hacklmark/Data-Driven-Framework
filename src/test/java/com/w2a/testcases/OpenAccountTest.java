package com.w2a.testcases;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class OpenAccountTest extends TestBase {

    @Test(dataProvider = "getData")
    public void openAccountTest(String customer, String currency) {


    }

    @Test(dataProviderClass = TestUtil.class,dataProvider = "dp")
    public Object[][] getData(){

        String sheetName = "OpenAccountTest";
        int rows = excel.getRowCount(sheetName);
        int cols = excel.getColumnCount(sheetName);

        Object[][] data = new Object[rows-1][cols];

        for (int rowNum = 2; rowNum <= rows; rowNum++){ //2
            for (int colNum = 0; colNum < cols; colNum++){

                //data[0][0]
                data[rowNum - 2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
                // -2
            }
        }

        return data;
    }
}
