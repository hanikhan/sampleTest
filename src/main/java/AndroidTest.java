import java.net.URL;
import java.net.MalformedURLException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AndroidTest {

    public static void main(String args[]) throws MalformedURLException, InterruptedException {
        String accessKey = System.getenv("BROWSERSTACK_USERNAME");
        String userName = System.getenv("BROWSERSTACK_KEY");
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("device", "Google Pixel");
        caps.setCapability("build", "Test Build v1.0");
        caps.setCapability("browserstack.useChromeDriver", "true");
        caps.setCapability("browserstack.captureCrash", "true");
        caps.setCapability("app", "bs://<app-hash-id>");
        caps.setCapability("browserstack.debug", "true");

        AndroidDriver driver = new AndroidDriver(new URL("https://"+userName+":"+accessKey+"@hub-cloud.browserstack.com/wd/hub"), caps);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        //added sleep for better video logs
        Thread.sleep(4000);
        Set<String> contextNames = driver.getContextHandles();
        //Iterate through the set
        for (String contextName : contextNames) {
            System.out.println(contextName); //prints out something like NATIVE_APP \n WEBVIEW_1
        }
        try {
            driver.context((String) contextNames.toArray()[1]); // set context to WEBVIEW_1 based on the index supplied (1 in this case)
        }catch (Exception e){}
        WebDriverWait wait = new WebDriverWait(driver, 120);
        wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//input[@name='username']")));
        driver.findElement(By.xpath("//input[@name='username']")).clear();
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("test@test.com");
        driver.findElement(By.xpath("//input[@name='password']")).clear();
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("sample");
        //added sleep for better video logs before terminating the session
        Thread.sleep(4000);
        driver.quit();
    }
}
