package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class MyFirstTest {

    private WebDriver driver;
    private WebDriverWait wait;


    @Before
    public void start(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void myFirstTest() {
        driver.get("http://localhost/litecart/");
        wait.until(titleIs("Online Store | My Store"));
        int count = 0;
        while (count < 3) {
            int countBefore = Integer.parseInt(driver.findElement(By.xpath("//span[@class='quantity']")).getText());
            driver.findElement(By.xpath("//li[contains(@class,'product')]")).click();
            wait.until(titleContains("Rubber Ducks"));
            if (driver.findElements(By.xpath("//select[@name='options[Size]']")).size() != 0) {
                Select size = new Select(driver.findElement(By.xpath("//select")));
                size.selectByValue("Large");
                System.out.println("size");
            }
            driver.findElement(By.xpath("//button[@name='add_cart_product']")).click();
            wait.until(textToBe(By.xpath("//span[@class='quantity']"), String.valueOf(countBefore+1)));
            driver.get("http://localhost/litecart/");
            wait.until(titleIs("Online Store | My Store"));
            count++;
        }
        driver.get("http://localhost/litecart/");
        driver.findElement(By.xpath("//div[@id='cart']//a[@class='link']")).click();
        int duckCount = driver.findElements(By.xpath("//button[text()='Remove']")).size();
        while (duckCount > 0) {
            driver.findElement(By.xpath("//button[text()='Remove']")).click();
            wait.until(stalenessOf(driver.findElement(By.xpath("//table[contains(@class,'dataTable')]"))));
            duckCount--;
        }
    }

    @After
    public void stop(){
        driver.quit();
        driver= null;
    }
}