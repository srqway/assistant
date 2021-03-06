package idv.hsiehpinghan.springbatchassistant.configuration;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mongodb.MongoClient;

@EnableScheduling
@EnableBatchProcessing
@EnableTransactionManagement
@Import(value = { JavaconfigJobConfiguration.class })
@Configuration("springBatchAssistantSpringConfiguration")
@PropertySource("classpath:/spring_batch_assistant.property")
@ImportResource(value = { "classpath:/applicationContext.xml" })
@ComponentScan(basePackages = { "idv.hsiehpinghan.springbatchassistant" })
public class SpringConfiguration {
	@Autowired
	private Environment environment;
	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private DataSource dataSource;
	// @Autowired
	// private JobRepository jobRepository;
	// @Autowired
	// private JobLauncher jobLauncher;
	// @Autowired
	// private JobRegistry jobRegistry;
	// @Autowired
	// private JobExplorer jobExplorer;
	// @Autowired
	// private PlatformTransactionManager transactionManager;
	// @Autowired
	// private JobBuilderFactory jobBuilders;
	// @Autowired
	// private StepBuilderFactory stepBuilders;

	@Bean
	public MongoClient mongoClient() throws IOException {
		String host = environment.getRequiredProperty("mongodb.host");
		int port = Integer.valueOf(environment.getRequiredProperty("mongodb.port"));
		MongoClient mongoClient = new MongoClient(host, port);
		return mongoClient;
	}

	@Bean
	public MongoTemplate mongoTemplate(MongoClient mongoClient) {
		String databaseName = environment.getRequiredProperty("mongodb.databaseName");
		MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, databaseName);
		return mongoTemplate;
	}

	@PostConstruct
	public void postConstruct() throws ScriptException, SQLException {
		Connection connection = dataSource.getConnection();
		Resource schemaDropPostgresqlSql = resourceLoader
				.getResource("classpath:/org/springframework/batch/core/schema-drop-postgresql.sql");
		ScriptUtils.executeSqlScript(connection, schemaDropPostgresqlSql);
		Resource schemaPostgresqlSql = resourceLoader
				.getResource("classpath:/org/springframework/batch/core/schema-postgresql.sql");
		ScriptUtils.executeSqlScript(connection, schemaPostgresqlSql);
	}

	@Bean
	public DataSource dataSource(Environment environment) throws PropertyVetoException {
		String driverClass = environment.getRequiredProperty("postgresql.driverClass");
		String jdbcUrl = environment.getRequiredProperty("postgresql.jdbcUrl");
		String user = environment.getRequiredProperty("postgresql.user");
		String password = environment.getRequiredProperty("postgresql.password");
		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
		comboPooledDataSource.setDriverClass(driverClass);
		comboPooledDataSource.setJdbcUrl(jdbcUrl);
		comboPooledDataSource.setUser(user);
		comboPooledDataSource.setPassword(password);
		return comboPooledDataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) throws PropertyVetoException {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setPackagesToScan(new String[] { environment.getRequiredProperty("hibernate.packagesToScan") });
		sessionFactory.setHibernateProperties(getHibernateProperties());
		return sessionFactory;
	}

	@Bean
	public HibernateTransactionManager hibernateTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		return txManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost("smtp.gmail.com");
		javaMailSender.setPort(587);
		javaMailSender.setUsername(getUsername());
		javaMailSender.setPassword(getPassword());
		javaMailSender.setJavaMailProperties(getProperties());
		return javaMailSender;
	}

	private String getUsername() {
		String usernameProp = "spring_batch_assistant_username";
		String username = environment.getRequiredProperty(usernameProp);
		return username;
	}

	private String getPassword() {
		String passwordProp = "spring_batch_assistant_password";
		String password = environment.getRequiredProperty(passwordProp);
		return password;
	}

	private Properties getProperties() {
		Properties prop = new Properties();
		prop.setProperty("mail.smtp.auth", "true");
		prop.setProperty("mail.smtp.starttls.enable", "true");
		return prop;
	}

	private Properties getHibernateProperties() {
		Properties prop = new Properties();
		prop.put("hibernate.default_schema", environment.getRequiredProperty("hibernate.default_schema"));
		prop.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		prop.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
		prop.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
		prop.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		prop.put("hibernate.generate_statistics", environment.getRequiredProperty("hibernate.generate_statistics"));
		prop.put("hibernate.use_sql_comments", environment.getRequiredProperty("hibernate.use_sql_comments"));
		prop.put("hibernate.connection.isolation", environment.getRequiredProperty("hibernate.connection.isolation"));
		prop.put("hibernate.jdbc.fetch_size", environment.getRequiredProperty("hibernate.jdbc.fetch_size"));
		prop.put("hibernate.jdbc.batch_size", environment.getRequiredProperty("hibernate.jdbc.batch_size"));
		return prop;
	}

	//
	// @Bean
	// public JobExplorer jobExplorer(DataSource dataSource) throws Exception {
	// JobExplorerFactoryBean jobExplorerFactoryBean = new
	// JobExplorerFactoryBean();
	// jobExplorerFactoryBean.setDataSource(dataSource);
	// jobExplorerFactoryBean.afterPropertiesSet();
	// return jobExplorerFactoryBean.getObject();
	// }
	//
	// @Bean
	// public JobRegistry jobRegistry() {
	// JobRegistry jobRegistry = new MapJobRegistry();
	// return jobRegistry;
	// }
	//
	// @Bean
	// public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(
	// JobRegistry jobRegistry) {
	// JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new
	// JobRegistryBeanPostProcessor();
	// jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
	// return jobRegistryBeanPostProcessor;
	// }
	//
	// @Bean
	// public JobOperator jobOperator(JobExplorer jobExplorer,
	// JobRegistry jobRegistry) {
	// SimpleJobOperator simpleJobOperator = new SimpleJobOperator();
	// simpleJobOperator.setJobExplorer(jobExplorer);
	// simpleJobOperator.setJobRepository(jobRepository);
	// simpleJobOperator.setJobRegistry(jobRegistry);
	// simpleJobOperator.setJobLauncher(jobLauncher);
	// return simpleJobOperator;
	// }
	//
	// @Bean
	// public JobLauncher jobLauncher() {
	// SimpleJobLauncher JobLauncher = new SimpleJobLauncher();
	// JobLauncher.setJobRepository(jobRepository);
	// }
	//
	// @Bean
	// public PlatformTransactionManager transactionManager(DataSource
	// dataSource) {
	// DataSourceTransactionManager transactionManager = new
	// DataSourceTransactionManager(dataSource);
	// return transactionManager;
	// }
	//
	// @Bean
	// public JobRepository jobRepository(PlatformTransactionManager
	// transactionManager)
	// throws Exception {
	// MapJobRepositoryFactoryBean mapJobRepositoryFactoryBean = new
	// MapJobRepositoryFactoryBean(transactionManager);
	// return mapJobRepositoryFactoryBean.getObject();
	// }

}
