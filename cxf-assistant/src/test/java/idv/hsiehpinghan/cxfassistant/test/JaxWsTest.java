package idv.hsiehpinghan.cxfassistant.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import idv.hsiehpinghan.cxfassistant.client.JaxWsClient;
import idv.hsiehpinghan.cxfassistant.configuration.JaxWsSpringConfiguration;
import idv.hsiehpinghan.cxfassistant.server.JaxWsServer;

@ContextConfiguration(classes = { JaxWsSpringConfiguration.class })
public class JaxWsTest extends AbstractTestNGSpringContextTests {
	private final String TEXT = "text";
	@Autowired
	private JaxWsServer server;
	@Autowired
	private JaxWsClient client;

	@BeforeClass
	public void beforeClass() {
		server.start();
	}

	@Test
	public void execute() {
		Assert.assertEquals(client.request(TEXT), TEXT);
	}

}
