package idv.hsiehpinghan.xbrlassistant.assistant;

import idv.hsiehpinghan.testutility.utility.SystemResourceUtility;
import idv.hsiehpinghan.xbrlassistant.suit.TestngSuitSetting;
import idv.hsiehpinghan.xbrlassistant.xbrl.Instance;
import idv.hsiehpinghan.xbrlassistant.xbrl.Presentation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.context.ApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class InstanceAssistantTest {
	private InstanceAssistant instanceAssistant;
	private File instanceFile;
	private ObjectMapper objectMapper;

	@BeforeClass
	public void beforeClass() {
		ApplicationContext applicationContext = TestngSuitSetting
				.getApplicationContext();
		instanceAssistant = applicationContext.getBean(InstanceAssistant.class);
		instanceFile = SystemResourceUtility
				.getFileResource("xbrl-instance/2013-01-sii-01-C/tifrs-fr0-m1-ci-cr-1101-2013Q1.xml");
		objectMapper = applicationContext.getBean(ObjectMapper.class);
	}

	@Test
	public void getInstanceJson() throws Exception {
		List<String> ids = new ArrayList<String>(4);
		ids.add(Presentation.Id.BalanceSheet);
		ids.add(Presentation.Id.StatementOfComprehensiveIncome);
		ids.add(Presentation.Id.StatementOfCashFlows);
		ids.add(Presentation.Id.StatementOfChangesInEquity);
		ObjectNode actual = instanceAssistant
				.getInstanceJson(instanceFile, ids);
		JsonNode expected = objectMapper
				.readTree(SystemResourceUtility
						.getFileResource("sample/instance/tifrs-fr0-m1-ci-cr-1101-2013Q1_Instance.json"));
		Assert.assertEquals(expected.toString().length(), actual.toString().length());
	}

//	@Test
	public void getContexts() throws Exception {
		// BalanceSheet
		Map<String, Set<String>> balanceSheetMap = instanceAssistant
				.getContexts(instanceFile, Presentation.Id.BalanceSheet);
		Assert.assertEquals(getBalanceSheetSet(),
				balanceSheetMap.get(Instance.Attribute.INSTANT));

		// StatementOfComprehensiveIncome
		Map<String, Set<String>> statementOfComprehensiveIncomeMap = instanceAssistant
				.getContexts(instanceFile,
						Presentation.Id.StatementOfComprehensiveIncome);
		Assert.assertEquals(getStatementOfComprehensiveIncomeSet(),
				statementOfComprehensiveIncomeMap
						.get(Instance.Attribute.DURATION));

		// StatementOfCashFlows
		Map<String, Set<String>> statementOfCashFlowsMap = instanceAssistant
				.getContexts(instanceFile, Presentation.Id.StatementOfCashFlows);
		Assert.assertEquals(getStatementOfCashFlowsSet(),
				statementOfCashFlowsMap.get(Instance.Attribute.DURATION));

		// StatementOfChangesInEquity
		Map<String, Set<String>> statementOfChangesInEquityMap = instanceAssistant
				.getContexts(instanceFile,
						Presentation.Id.StatementOfChangesInEquity);
		Assert.assertEquals(getStatementOfChangesInEquitySet(),
				statementOfChangesInEquityMap.get(Instance.Attribute.DURATION));
	}

	private SortedSet<String> getBalanceSheetSet() {
		SortedSet<String> set = new TreeSet<String>();
		set.add("20120101");
		set.add("20120331");
		set.add("20121231");
		set.add("20130331");
		return set;
	}

	private SortedSet<String> getStatementOfComprehensiveIncomeSet() {
		SortedSet<String> set = new TreeSet<String>();
		set.add("20120101~20120331");
		set.add("20130101~20130331");
		return set;
	}

	private SortedSet<String> getStatementOfCashFlowsSet() {
		SortedSet<String> set = new TreeSet<String>();
		set.add("20120101~20120331");
		set.add("20130101~20130331");
		return set;
	}

	private SortedSet<String> getStatementOfChangesInEquitySet() {
		SortedSet<String> set = new TreeSet<String>();
		set.add("20120101~20120331");
		set.add("20130101~20130331");
		return set;
	}
}
