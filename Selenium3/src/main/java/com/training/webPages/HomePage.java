package com.training.webPages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public HomePage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//table[@class='layout']//span[text() = 'Classification/Status Search']")
    private WebElement classificationSearch;

    @FindBy(xpath = "//div[@class='table_tools']//a[text() = 'Select status']")
    private WebElement selectStatus;

    @FindBy(xpath = "//table[@class='alternated']//label[text() = 'Under Opposition']/parent::td/preceding-sibling::td")
    private List<WebElement> selectItems;

    @FindBy(id =  "MainContent_ctrlTMSearch_ctrlCaseStatusSearchDialog_lnkBtnSelect")
    private WebElement selectBtn;

    @FindBy(id = "MainContent_ctrlTMSearch_lnkbtnSearch")
    private WebElement searchBtn;

    @FindBy(xpath = "//table[@id='MainContent_ctrlTMSearch_ctrlProcList_gvwIPCases']//th/a[text()='Case Number']")
    private WebElement caseNumber;

    @FindBy(xpath = "//table[@id='MainContent_ctrlTMSearch_ctrlProcList_gvwIPCases']//tr[3]/td[2]/a")
    private WebElement sampleApplication;

    public void open(String homeURL) {
        driver.get(homeURL);
        driver.manage().window().maximize();
    }

    public void selectClassifications() {

        wait.until(ExpectedConditions.elementToBeClickable(classificationSearch));
        classificationSearch.click();
        selectStatus.click();
        for (WebElement select : selectItems){
            select.click();
        }
        selectBtn.click();
    }

    public void searchApplications() throws InterruptedException {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                "#MainContent_ctrlTMSearch_ctrlCaseStatusSearchDialog_UpPnlCaseStatusListPicker>div>table")));

        searchBtn.click();

        wait.until(ExpectedConditions.visibilityOf(caseNumber));
//        ((JavascriptExecutor) driver).executeScript(
//                "arguments[0].scrollIntoView(true);",caseNumber);
        wait.until(ExpectedConditions.elementToBeClickable(caseNumber));
        Thread.sleep(5000);
        caseNumber.click();

//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", caseNumber);
//        Thread.sleep(300); // optional
//        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", caseNumber);

    }

    public void openApplication() throws InterruptedException {
        Thread.sleep(6000);
        sampleApplication.click();
    }
}
