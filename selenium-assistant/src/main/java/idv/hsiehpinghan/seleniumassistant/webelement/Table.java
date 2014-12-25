package idv.hsiehpinghan.seleniumassistant.webelement;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Table extends WebElementWithBy {
	public Table(WebDriver webDriver, By by) {
		super(webDriver, by);
	}

	/**
	 * Convert row text to string list.
	 * 
	 * @param index
	 * @return
	 */
	public List<String> getRowAsStringList(int index) {
		List<WebElement> cols = getColumns(index);
		List<String> texts = new ArrayList<String>(cols.size());
		for (WebElement ele : cols) {
			texts.add(ele.getText());
		}
		return texts;
	}

	/**
	 * Get row size.
	 * 
	 * @return
	 */
	public int getRowSize() {
		return getRows().size();
	}

	public void clickButtonCell(int rowIndex, int columnIndex) {
		getButton(rowIndex, columnIndex).click();
	}

	public String getButtonValueOrText(int rowIndex, int columnIndex) {
		WebElement button = getButton(rowIndex, columnIndex);
		String value = button.getAttribute("value");
		if("".equals(value)) {
			return button.getText();
		}
		return value;
	}
	
	private WebElement getButton(int rowIndex, int columnIndex) {
		WebElement cell = getCell(rowIndex, columnIndex);
		return cell.findElement(By.cssSelector("button,input[type='button']"));
	}
	
	private WebElement getRow(int index) {
		return getRows().get(index);
	}

	private List<WebElement> getRows() {
		return getSeleniumWebElement().findElements(By.cssSelector("tr"));
	}

	private List<WebElement> getColumns(int rowIndex) {
		WebElement row = getRow(rowIndex);
		return row.findElements(By.cssSelector("th, td"));
	}

	private WebElement getCell(int rowIndex, int columnIndex) {
		return getColumns(rowIndex).get(columnIndex);
	}
}
