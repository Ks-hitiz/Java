package com.training.webPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryTab {
    private WebDriver driver;
    private WebDriverWait wait;

    public HistoryTab(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//table[@id='MainContent_ctrlHistoryList_gvHistory']")
    private WebElement historyList;

    @FindBy(xpath = "//ul[@role='tablist']//a[@id='ui-id-3']")
    private WebElement document;

    private String firstActionDate;
    private String firstActionType;

    public void extractHistory(){
        wait.until(ExpectedConditions.visibilityOf(historyList));

        List<WebElement> headers = driver.findElements(By.xpath("//table[@id='MainContent_ctrlHistoryList_gvHistory']/tbody/tr/th"));
        Map<String, Integer> headerIndexMap = new HashMap<>();

        for (int i = 0; i < headers.size(); i++) {
            headerIndexMap.put(headers.get(i).getText().trim(), i);
        }

        int historyTypeIndex = headerIndexMap.get("History Type");
        int dateIndex = headerIndexMap.get("Creation date");

        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='MainContent_ctrlHistoryList_gvHistory']/tbody/tr"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));

            if (cells.size() <= Math.max(historyTypeIndex, dateIndex)) {
                continue;
            }

            String caseTypeValue = cells.get(historyTypeIndex).getText().trim();
            if (caseTypeValue.equalsIgnoreCase("Opposition proceeding commenced")) {
                firstActionType = cells.get(historyTypeIndex).getText().trim();
                firstActionDate = cells.get(dateIndex).getText().trim();
            }
        }
    }

    public String getFirstAction() {
        return firstActionType;
    }

    public String getFirstActionDate() {
        return firstActionDate;
    }

    public void clickDocumentTab() {
        document.click();
    }
}
