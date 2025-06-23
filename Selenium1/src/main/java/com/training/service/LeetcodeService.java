package com.training.service;


import com.training.exception.CodeExecutionException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class LeetcodeService {
    public String submitCode(String code) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        String result;

        try {
            driver.get("https://leetcode.com/");

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            WebElement codeFrame = driver.findElement(By.xpath(
                    "//div[@class = 'playground-iframe']"));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView(true);",codeFrame);
            driver.switchTo().frame(driver.findElement(By.xpath(
                    "//div[@class = 'playground-iframe']//iframe")));

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement javaTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                    "//div[@class = 'lang-btn-set']//button[text() = 'Java']")));

            javaTab.click();

            WebElement editorArea = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".CodeMirror")));

            editorArea.click();
            Thread.sleep(300);

            ((JavascriptExecutor) driver).executeScript(
                    "navigator.clipboard.writeText(arguments[0]);", code
            );

            Actions actions = new Actions(driver);
            actions
                    .keyDown(Keys.CONTROL)
                    .sendKeys("a")
                    .sendKeys("v")
                    .keyUp(Keys.CONTROL)
                    .perform();

            Thread.sleep(300);

            WebElement runBtn = driver.findElement(By.xpath(
                    "//button[@class = 'btn btn-success run-code-btn']"));

            runBtn.click();

            WebElement outputElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("#output div.output")));

            result = outputElement.getText();

        } catch (Exception e) {
            throw new CodeExecutionException("Execution Failed : "+e.getMessage());
        } finally {
          driver.quit();
        }
        return result;
    }
}
