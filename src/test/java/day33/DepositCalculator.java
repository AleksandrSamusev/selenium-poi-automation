package day33;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class DepositCalculator {
    public static void main(String[] args) throws IOException {

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        driver.get("https://www.hdfcbank.com/personal/fd-test-calculator");

        driver.manage().window().maximize();


        String filePath = System.getProperty("user.dir") + "\\testdata\\caldata.xlsx";
        int rows = ExcelUtils.getRowCount(filePath, "Sheet1");

        for (int i = 1; i <= rows; i++) {

            //Read data from excel

            String amount = ExcelUtils.getCellData(filePath, "Sheet1", i, 0);
            String type = ExcelUtils.getCellData(filePath, "Sheet1", i, 1);
            String days = ExcelUtils.getCellData(filePath, "Sheet1", i, 2);
            String isSenior = ExcelUtils.getCellData(filePath, "Sheet1", i, 3);
            String date = ExcelUtils.getCellData(filePath, "Sheet1", i, 4);
            String expected = ExcelUtils.getCellData(filePath, "Sheet1", i, 5);

            //Pass data to the Application

            driver.findElement(By.id("loanamt")).clear();
            driver.findElement(By.id("loanamt")).sendKeys(amount); //set amount

            Select typeDrop = new Select(driver
                    .findElement(By.xpath("//div[@class='form-group']/select")));
            typeDrop.selectByVisibleText(type); // set type

            driver.findElement(By.xpath("//div[@class='form-group days']//input[@id='days']")).clear();
            driver.findElement(By.xpath("//div[@class='form-group days']//input[@id='days']"))
                    .sendKeys(days); // set days

            if (isSenior.equals("Yes")) {
                driver.findElement(By.id("radio-senior-yes")).click();
            } else {
                driver.findElement(By.id("radio-senior-no")).click();
            } // set isSitizen


            //set start date
            String[] splittedDate = date.split("-");
            String day = splittedDate[0];
            String month = splittedDate[1];
            String year = splittedDate[2];

            driver.findElement(By.xpath("//span[@class='calender-image']")).click();
            driver.findElement(By.xpath("//button[normalize-space()='OK']")).click();

            Select monthDrop = new Select(driver.findElement(By
                    .xpath("//select[@class='ui-datepicker-month']")));
            monthDrop.selectByVisibleText(month);

            Select yearDrop = new Select(driver.findElement(By
                    .xpath("//select[@class='ui-datepicker-year']")));
            yearDrop.selectByVisibleText(year);

            List<WebElement> list = driver.findElements(By
                    .xpath("//table[@class='ui-datepicker-calendar']/tbody//td"));
            for (WebElement elem : list) {
                if (elem.getText().equals(day)) {
                    elem.click();
                    break;
                }
            }

            driver.findElement(By.xpath("//a[@title='Calculate FD Online']")).click();

            String result = driver.findElement(By
                    .xpath("//div[@class='fdCont ng-pristine " +
                            "ng-untouched ng-valid ng-not-empty']/span[2]")).getText();
            System.out.println(result);

            ExcelUtils.setCellData(filePath, "Sheet1", i, 6, result);

            double fact = Double.parseDouble(result.substring(2));

            if (fact == Double.parseDouble(expected)) {
                ExcelUtils.setCellData(filePath, "Sheet1", i, 7, "Passed");
                ExcelUtils.fillGreenColor(filePath, "Sheet1", i, 7);
            } else {
                ExcelUtils.setCellData(filePath, "Sheet1", i, 7, "Failed");
                ExcelUtils.fillRedColor(filePath, "Sheet1", i, 7);
            }
        }
    }
}
