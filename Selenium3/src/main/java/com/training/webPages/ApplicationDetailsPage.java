package com.training.webPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationDetailsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ApplicationDetailsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//span[starts-with(@id , 'MainContent_') and contains(@id , '_txtAppNr')]")
    private WebElement applicationNumber;

    @FindBy(xpath = "//table[starts-with(@id , 'MainContent_') and contains(@id , '_ctrlApplicant_ctrlApplicant')]//tr[@class='alt1']/td[1]/span")
    private WebElement applicantID;

    @FindBy(xpath = "//table[starts-with(@id , 'MainContent_') and contains(@id , '_ctrlApplicant_ctrlApplicant')]//tr[@class='alt1']/td[2]")
    private WebElement applicantName;

    @FindBy(xpath = "//table[starts-with(@id , 'MainContent_') and contains(@id , '_ctrlApplicant_ctrlApplicant')]//tr[@class='alt1']/td[3]")
    private WebElement applicantAddress;

    @FindBy(xpath = "//div[starts-with(@id , 'MainContent_') and contains(@id , '_tblClass')]//table//td[@class='label break-word']")
    private WebElement classType;

    @FindBy(xpath = "//div[starts-with(@id , 'MainContent_') and contains(@id , '_tblClass')]//table//table//tr[2]/td")
    private WebElement classValue;

    @FindBy(xpath = "//tr[starts-with(@id , 'MainContent_') and contains(@id , '_trTMType')]/td[@class ='data break-word']")
    private WebElement markType;

    @FindBy(xpath = "//tr[starts-with(@id , 'MainContent_') and contains(@id , '_trTMName')]/td[@class ='data break-word']")
    private WebElement markName;

    @FindBy(xpath = "//div[starts-with(@id , 'MainContent_') and contains(@id , '_ctrlPictureList_upDocGrid')]//div[@class='device']/a")
    private List<WebElement> pictures;

    @FindBy(xpath = "//table[@id='MainContent_ctrlProcedureList_gvwIPCases']")
    private WebElement mainContent;

    @FindBy(xpath = "//ul[@role='tablist']//a[@id='ui-id-2']")
    private WebElement history;


    public String getApplicationNumber() {
        wait.until(ExpectedConditions.visibilityOf(applicationNumber));
        return applicationNumber.getText();
    }

    public String getApplicantId() {
        return applicantID.getText();
    }

    public String getClassType() {
        return classType.getText();
    }

    public String getClassValue() {
        return classValue.getText();
    }

    public String getApplicantName() {
        return applicantName.getText();
    }

    public String getApplicantAddress() {
        return applicantAddress.getText();
    }

    public String getMarkName() {
        return markName.getText();
    }

    public String getMarkType() {
        return markType.getText();
    }

    public List<String> getPictures() {
        List<String> imageUrls = new ArrayList<>();
        for(WebElement image : pictures){
            imageUrls.add(image.getDomAttribute("href"));
        }
        return imageUrls;
    }

    public List<String> getRedPartyOpponent(){

        wait.until(ExpectedConditions.visibilityOf(mainContent));
        List<WebElement> headers = driver.findElements(By.xpath("//table[@id='MainContent_ctrlProcedureList_gvwIPCases']/tbody/tr/th"));
        Map<String, Integer> headerIndexMap = new HashMap<>();
        List<String> output = new ArrayList<>();

        for (int i = 0; i < headers.size(); i++) {
            headerIndexMap.put(headers.get(i).getText().trim(), i);
        }

        int caseTypeIndex = headerIndexMap.get("Case Type");
        int ownerIndex = headerIndexMap.get("Owner");

        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='MainContent_ctrlProcedureList_gvwIPCases']/tbody/tr"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));

            if (cells.size() <= Math.max(caseTypeIndex, ownerIndex)) {
                continue;
            }

            String caseTypeValue = cells.get(caseTypeIndex).getText().trim();
            if (caseTypeValue.equalsIgnoreCase("Proceedings - TM Opposition")) {
                 output.add(cells.get(ownerIndex).getText().trim());
            }
        }
        return output;
    }

    public void clickHistoryTab() {
        history.click();
    }

}
