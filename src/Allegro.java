import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Allegro {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "D:\\Projects\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, 5);

		searchForBlackIphones(driver, wait);
		System.out.println("Ilosc wynikow na pierwszej stronie: " + findAmountOfResults(driver));
		float hpFromPage = findHighestPriceOnPage(driver);
		System.out.println("Najwyzsza cena na liscie: " + hpFromPage + " zl");
		System.out.println("Cena po zwiekszeniu o 23%: " + addedPercentage(driver, hpFromPage) + " zl");
	}

	public static void searchForBlackIphones(WebDriver driver, WebDriverWait wait) {
		driver.get("https://allegro.pl/");
		driver.findElement(By.xpath("//button[@class='_13q9y _8hkto munh_56_m m7er_k4 m7er_56_m']")).click();
		driver.findElement(By.name("string")).sendKeys("iphone 11");
		driver.findElement(By.xpath("//button[@class='_13q9y _8tsq7 _1q2ua mr3m_0 mli2_0 mh85_0 mh85_56_m msbw_0 mtag_0']")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html[1]/body[1]/div[2]/div[4]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[3]/div[1]/div[1]/div[1]/div[1]/div[1]/fieldset[10]/div[1]/ul[1]/li[1]/label[1]")));
		driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[4]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[3]/div[1]/div[1]/div[1]/div[1]/div[1]/fieldset[10]/div[1]/ul[1]/li[1]/label[1]")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//section[contains(@class, '_9c44d_3pyzl')]")));

	}

	public static int findAmountOfResults(WebDriver driver) {
		WebElement resultsColumnDriver = driver.findElement(By.xpath("//section[contains(@class, '_9c44d_3pyzl')]"));
		return resultsColumnDriver.findElements(By.tagName("article")).size();
	}

	public static float findHighestPriceOnPage(WebDriver driver) {
		WebElement resultsColumnDriver = driver.findElement(By.xpath("//section[contains(@class, '_9c44d_3pyzl')]"));
		List<WebElement> pricesList = resultsColumnDriver.findElements(By.xpath("//div[contains(@class, 'msa3_z4 _9c44d_2K6FN')]"));
		ArrayList<Float> pricesArray = new ArrayList<Float>();
		for (int i = 0; i < pricesList.size(); i++) {
			String productPrice = pricesList.get(i).getText().replaceAll("[^\\,.0123456789]", "");
			productPrice = productPrice.replace(',', '.');
			float priceConverted = Float.parseFloat(productPrice);
			pricesArray.add(priceConverted);
		}
		Float highestPrice = Collections.max(pricesArray);
		return highestPrice;
	}

	public static float addedPercentage(WebDriver driver, float highestPrice) {
		return (float) (highestPrice * 1.23);

	}

}
