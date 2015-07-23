package idv.hsiehpinghan.springdatajpaassistatnt.assistant;

import idv.hsiehpinghan.springdatajpaassistatnt.entity.ElementCollectionEntity;
import idv.hsiehpinghan.springdatajpaassistatnt.entity.OneToManyBidirectionManyEntity_1;
import idv.hsiehpinghan.springdatajpaassistatnt.entity.OneToManyBidirectionManyEntity_2;
import idv.hsiehpinghan.springdatajpaassistatnt.entity.OneToManyBidirectionOneEntity;
import idv.hsiehpinghan.springdatajpaassistatnt.service.IElementCollectionService;
import idv.hsiehpinghan.springdatajpaassistatnt.service.IOneToManyBidirectionService;
import idv.hsiehpinghan.springdatajpaassistatnt.suit.TestngSuitSetting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AssociationTest {
	ApplicationContext applicationContext;

	@BeforeClass
	public void beforeClass() throws IOException {
		applicationContext = TestngSuitSetting.getApplicationContext();
	}

//	@Test
	public void oneToOne() {
		IElementCollectionService service = applicationContext
				.getBean(IElementCollectionService.class);
		ElementCollectionEntity entity = generateElementCollectionEntity();
		service.save(entity);
		int id = entity.getId();
		ElementCollectionEntity returnEntity = service.findOne(id);
		Assert.assertNotNull(returnEntity);
		Assert.assertEquals(returnEntity.getElements().size(), 3);
	}

	@Test
	public void oneToManyBidirection() {
		IOneToManyBidirectionService service = applicationContext
				.getBean(IOneToManyBidirectionService.class);
		OneToManyBidirectionOneEntity one = generateOneToManyBidirectionOneEntity();
		service.save(one);
		System.err.println(one.getId());
		
//		ElementCollectionEntity entity = generateElementCollectionEntity();
//		service.save(entity);
//		int id = entity.getId();
//		ElementCollectionEntity returnEntity = service.findOne(id);
//		Assert.assertNotNull(returnEntity);
//		Assert.assertEquals(returnEntity.getElements().size(), 3);
	}
	
	private OneToManyBidirectionOneEntity generateOneToManyBidirectionOneEntity() {
		OneToManyBidirectionOneEntity one = new OneToManyBidirectionOneEntity();
		one.setName("ttt");
		one.setMany_1(generateOneToManyBidirectionManyEntity_1s(one));
//		one.setMany_2(generateOneToManyBidirectionManyEntity_2s(one));
		return one;
	}
	
	private Collection<OneToManyBidirectionManyEntity_1> generateOneToManyBidirectionManyEntity_1s(OneToManyBidirectionOneEntity one) {
		Collection<OneToManyBidirectionManyEntity_1> entities = new ArrayList<OneToManyBidirectionManyEntity_1>();
		for(int i = 0; i < 3; ++i) {
			entities.add(generateOneToManyBidirectionManyEntity_1(one));
		}
		return entities;
	}
	
	private OneToManyBidirectionManyEntity_1 generateOneToManyBidirectionManyEntity_1(OneToManyBidirectionOneEntity one) {
		OneToManyBidirectionManyEntity_1 many = new OneToManyBidirectionManyEntity_1();
		many.setOne(one);
		return many;
	}
	
	private Collection<OneToManyBidirectionManyEntity_2> generateOneToManyBidirectionManyEntity_2s(OneToManyBidirectionOneEntity one) {
		Collection<OneToManyBidirectionManyEntity_2> entities = new ArrayList<OneToManyBidirectionManyEntity_2>();
		for(int i = 0; i < 3; ++i) {
			entities.add(generateOneToManyBidirectionManyEntity_2(one));
		}
		return entities;
	}
	
	private OneToManyBidirectionManyEntity_2 generateOneToManyBidirectionManyEntity_2(OneToManyBidirectionOneEntity one) {
		OneToManyBidirectionManyEntity_2 many = new OneToManyBidirectionManyEntity_2();
		many.setOne(one);
		return many;
	}
	
	private ElementCollectionEntity generateElementCollectionEntity() {
		ElementCollectionEntity entity = new ElementCollectionEntity();
		Set<String> elements = generateStringElements();
		entity.setElements(elements);
		return entity;
	}

	private Set<String> generateStringElements() {
		Set<String> elements = new HashSet<String>(3);
		elements.add("item 1");
		elements.add("item 2");
		elements.add("item 3");
		return elements;
	}
}
