package com.devops;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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
            driver.findElement(By.id("searchInput"))
                    .sendKeys("Backpack");

            driver.findElement(By.id("searchButton")).click();

            // Verify search result
            String searchResult =
                    driver.findElement(By.tagName("body")).getText();

            if (searchResult.contains("Black Backpack")) {
                System.out.println("Search test passed.");
            } else {
                throw new Exception("Search test failed.");
            }

            // Open Report Item page
            driver.findElement(By.linkText("Report Item")).click();

            // Fill report form
            driver.findElement(By.id("itemName"))
                    .sendKeys("Blue Water Bottle");

            Select category =
                    new Select(driver.findElement(By.id("itemCategory")));

            category.selectByVisibleText("Other");

            driver.findElement(By.id("location"))
                    .sendKeys("Library");

            Select status =
                    new Select(driver.findElement(By.id("status")));

            status.selectByVisibleText("Lost");

            driver.findElement(By.id("description"))
                    .sendKeys("Blue water bottle found near the library.");

            // Submit report
            driver.findElement(
                    By.cssSelector("#report button[type='submit']")
            ).click();

            // Wait briefly for the page to update
            WebDriverWait wait =
                    new WebDriverWait(driver, Duration.ofSeconds(5));

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("itemsList")
            ));

            // Clear the previous Backpack search
            driver.findElement(By.id("searchInput")).clear();
            driver.findElement(By.id("searchButton")).click();

            // Wait for the newly reported item to appear
            wait.until(
                    ExpectedConditions.textToBePresentInElementLocated(
                            By.id("itemsList"),
                            "Blue Water Bottle"
                    )
            );

            System.out.println("Report submission test passed.");

            System.out.println("--------------------------------");
            System.out.println("SELENIUM TEST PASSED SUCCESSFULLY");
            System.out.println("--------------------------------");

        } catch (Exception e) {

            System.out.println("SELENIUM TEST FAILED");
            e.printStackTrace();

            // Make Jenkins fail if Selenium fails
            System.exit(1);

        } finally {
            driver.quit();
        }
    }
}