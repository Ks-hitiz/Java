package com.training.webPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class DocumentTab {
    private WebDriver driver;
    private WebDriverWait wait;

    public DocumentTab(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//table[@id='MainContent_ctrlDocumentList_gvDocuments']//a[@class='icon ui-icon-file-pdf']")
    private WebElement firstDocumentLink;

    public void downloadFirstDocument() {
        wait.until(ExpectedConditions.elementToBeClickable(firstDocumentLink));
        firstDocumentLink.click();
    }
}

