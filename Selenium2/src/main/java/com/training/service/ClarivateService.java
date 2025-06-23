package com.training.service;


import com.training.model.MenuCategory;
import com.training.model.MenuItems;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClarivateService {

    public List<MenuCategory> scrapeMenu() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        List<MenuCategory> categoryList = new ArrayList<>();

        try{
            driver.get("https://clarivate.com/");

            driver.manage().window().maximize();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                    "#onetrust-close-btn-container > button")));

            driver.findElement(By.cssSelector("#onetrust-close-btn-container > button")).click();

            List<WebElement> categories = driver.findElements(By.cssSelector("ul#menu-main-menu>li"));


            for (int i=0; i<3; i++){
                try {
                    String header = categories.get(i).findElement(By.cssSelector(
                            "div.nav-link-wrapper>span")).getText().trim();

                    wait.until(ExpectedConditions.elementToBeClickable(categories.get(i)));
                    categories.get(i).click();
                    wait.until(ExpectedConditions.visibilityOfAllElements(categories.get(i).findElements(
                            By.cssSelector("li li.nav-item span.mega-title"))));

                    List<MenuItems> items = new ArrayList<>();
                    List<WebElement> nav_menu = categories.get(i).findElements(By.cssSelector(
                            "li li.nav-item span.mega-title"));

                    for (WebElement menu : nav_menu){
                        String item = menu.getText().trim();
                        if(!item.isEmpty()){
                            items.add(new MenuItems(item));
                        }
                    }

                    if (!items.isEmpty()){
                        categoryList.add(new MenuCategory(header, items));
                    }
                }catch (Exception e){
                    System.out.println("Error parsing one category: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            driver.quit();
        }

        return categoryList;
    }
}
