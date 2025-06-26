package com.training.service;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.config.DateFormatConverter;
import com.training.model.*;
import com.training.webPages.ApplicationDetailsPage;
import com.training.webPages.DocumentTab;
import com.training.webPages.HistoryTab;
import com.training.webPages.HomePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NzipotmService {

    @Autowired
    private Binder binder;
    @Autowired
    private Classification classification;
    @Autowired
    private Decision decision;
    @Autowired
    private Docket docket;
    @Autowired
    private Party party;
    @Autowired
    private Right right;

    private String firstActionDate;
    private String firstAction;
    private String applicationNumber;
    private String applicantID;
    private String applicantName;
    private String applicantAddress;
    private String markName;
    private String markType;
    private String classType;
    private String classValue;
    private List<String> redParty;
    private List<String> imagesURL;

    DateFormatConverter dateFormatConverter = new DateFormatConverter();

    private final String HomeURL = "https://app.iponz.govt.nz/app/Extra/IP/TM/Qbe.aspx?sid=638863042474261251&op=EXTRA_tm_qbe&fcoOp=EXTRA__Default&directAccess=true";

    public Binder runComplaintsRobot() {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(10));

        try {
            openHomePage(driver,wait);

            return binder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            driver.quit();
        }
    }

    private void openHomePage(WebDriver driver , WebDriverWait wait) throws InterruptedException {

        HomePage homePage = new HomePage(driver,wait);

        homePage.open(HomeURL);
        homePage.selectClassifications();
        homePage.searchApplications();
        homePage.openApplication();

        goToApplicationDetailsPage(driver,wait);
    }

    private void goToApplicationDetailsPage(WebDriver driver , WebDriverWait wait){

        ApplicationDetailsPage applicationDetailsPage = new ApplicationDetailsPage(driver,wait);
        applicationNumber = applicationDetailsPage.getApplicationNumber();
        applicantID = applicationDetailsPage.getApplicantId();
        applicantName = applicationDetailsPage.getApplicantName();
        applicantAddress = applicationDetailsPage.getApplicantAddress();
        classType = applicationDetailsPage.getClassType();
        classValue = applicationDetailsPage.getClassValue();
        markType = applicationDetailsPage.getMarkType();
        markName = applicationDetailsPage.getMarkName();
        imagesURL = applicationDetailsPage.getPictures();
        redParty = applicationDetailsPage.getRedPartyOpponent();

        applicationDetailsPage.clickHistoryTab();

        goToHistoryTab(driver,wait);
    }

    private void goToHistoryTab(WebDriver driver, WebDriverWait wait) {

        HistoryTab historyTab = new HistoryTab(driver, wait);
        historyTab.extractHistory();
        firstAction = historyTab.getFirstAction();
        firstActionDate = historyTab.getFirstActionDate();

        historyTab.clickDocumentTab();

        goTODocumentTab(driver,wait);
    }

    private void goTODocumentTab(WebDriver driver, WebDriverWait wait) {

        DocumentTab documentTab = new DocumentTab(driver,wait);
        documentTab.downloadFirstDocument();

        setBinder();
    }

    private void setBinder() {
        binder.setId(UUID.randomUUID().toString());
        binder.setFirstAction(firstAction);
        binder.setFirstActionDate(firstActionDate);
        binder.setDomains(List.of("TM","CR","DM","PT"));

        String docketRef = "nz-nzipotm-op-" + applicationNumber + "_" + dateFormatConverter.formatToYYYYMMDD(firstActionDate);
        docket.setId(UUID.randomUUID().toString());
        docket.setReference(docketRef);
        docket.setCourtId("XYZ");
        docket.setJudge("Unknown");
        binder.setDockets(List.of(docket));

        party.setName(redParty);
        party.setType("Opponent");
        party.setRepresentatives(List.of("Unknown"));
        binder.setParties(List.of(party));

        classification.setName(applicantName);
        classification.setType("Goods & Services Specification");
        classification.setClassName("27");
        classification.setImages(imagesURL);

        right.setId(UUID.randomUUID().toString());
        right.setOpponent(true);
        right.setName(markName);
        right.setType(markType);
        right.setReference("Ref-"+applicationNumber);
        right.setClassification(classification);
        binder.setRights(List.of(right));

        exportJsonAndFiles(binder, applicationNumber, firstActionDate);
    }

    private void exportJsonAndFiles(Binder binder, String applicationNumber, String firstActionDate) {
        try {
            String date = dateFormatConverter.formatToYYYYMMDD(firstActionDate);
            String decisionRef = "nz-nzipotm-op-" + applicationNumber + "_" + date + "_Complaint_IS";


            Path outputDir = Paths.get("src", "main", "resources", "util");
            if (!Files.exists(outputDir)) {
                Files.createDirectories(outputDir);
            }

            // Create full file paths
            Path jsonPath = outputDir.resolve(decisionRef + ".json");
            Path pdfPath = outputDir.resolve(decisionRef + ".pdf");

            // Save JSON
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(jsonPath.toFile(), binder);

            // Move PDF from Downloads
            Path downloadFolder = Paths.get(System.getProperty("user.home"), "Downloads");

            Optional<Path> pdfFile = waitForPdfDownload(downloadFolder);

            if (pdfFile.isPresent()) {
                Files.move(pdfFile.get(), pdfPath, StandardCopyOption.REPLACE_EXISTING);
            } else {
                System.out.println("No PDF found in Downloads to move.");
            }

            System.out.println("Files saved to: " + outputDir.toAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException("Failed to export decision files", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<Path> waitForPdfDownload(Path downloadFolder) throws IOException, InterruptedException {
        int waited = 0;
        while (waited < 15 * 1000) {
            Optional<Path> latestPdf = Files.list(downloadFolder)
                    .filter(f -> f.toString().endsWith(".pdf"))
                    .filter(f -> !Files.exists(Paths.get(f.toString() + ".crdownload")))
                    .sorted(Comparator.comparingLong((Path p) -> p.toFile().lastModified()).reversed())
                    .findFirst();

            if (latestPdf.isPresent()) {
                return latestPdf;
            }

            Thread.sleep(1000);
            waited += 1000;
        }

        return Optional.empty();
    }


}
