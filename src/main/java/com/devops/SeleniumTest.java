package com.devops;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class SeleniumTest {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();

        try {
            // Open CampusFind
            driver.get("http://localhost:8081");

            // Verify homepage
            String pageText = driver.findElement(By.tagName("body")).getText();

            if (pageText.contains("Find What You Lost")) {
                System.out.println("Homepage loaded successfully.");
            } else {
                throw new Exception("Homepage verification failed.");
            }

            // Open Find Item page
            driver.findElement(By.linkText("Find Item")).click();

            // Search for Backpack
            driver.findElement(By.id("searchInput")).sendKeys("Backpack");
            driver.findElement(By.id("searchButton")).click();

            // Verify search result
            String searchResult = driver.findElement(By.tagName("body")).getText();

            if (searchResult.contains("Black Backpack")) {
                System.out.println("Search test passed.");
            } else {
                throw new Exception("Search test failed.");
            }

            // Open Report Item page
            driver.findElement(By.linkText("Report Item")).click();

            // Fill report form
            driver.findElement(By.id("itemName")).sendKeys("Blue Water Bottle");

            Select category = new Select(driver.findElement(By.id("itemCategory")));
            category.selectByVisibleText("Other");

            driver.findElement(By.id("location")).sendKeys("Library");

            Select status = new Select(driver.findElement(By.id("status")));
            status.selectByVisibleText("Lost");

            driver.findElement(By.id("description"))
                    .sendKeys("Blue water bottle found near the library.");

            // Submit report
            driver.findElement(By.id("submitReportButton")).click();

            // Verify successful submission
            String message = driver.findElement(By.id("message")).getText();

            if (message.contains("Report submitted successfully")) {
                System.out.println("Report submission test passed.");
            } else {
                throw new Exception("Report submission test failed.");
            }

            System.out.println("--------------------------------");
            System.out.println("SELENIUM TEST PASSED SUCCESSFULLY");
            System.out.println("--------------------------------");

        } catch (Exception e) {
            System.out.println("SELENIUM TEST FAILED");
            e.printStackTrace();

        } finally {
            driver.quit();
        }
    }
}
