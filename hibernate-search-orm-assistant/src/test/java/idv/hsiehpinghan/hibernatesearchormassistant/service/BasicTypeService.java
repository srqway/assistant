package idv.hsiehpinghan.hibernatesearchormassistant.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import idv.hsiehpinghan.hibernatesearchormassistant.entity.BasicTypeEntity;
import idv.hsiehpinghan.hibernatesearchormassistant.repository.BasicTypeRepository;

@Service
@Transactional
public class BasicTypeService {
	@Autowired
	private BasicTypeRepository repository;

	public void save(BasicTypeEntity entity) {
		repository.save(entity);
	}

	public void saveOrUpdate(BasicTypeEntity entity) {
		repository.saveOrUpdate(entity);
	}

	public void remove(BasicTypeEntity entity) {
		repository.remove(entity);
	}

	public void reindex(BasicTypeEntity entity) {
		repository.reindex(entity);
	}

	public void reindexAll() throws InterruptedException {
		repository.reindexAll();
	}

	public void unindex(Integer id) {
		repository.unindex(id);
	}

	public void unindexAll() throws InterruptedException {
		repository.unindexAll();
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public BasicTypeEntity findOne(int id) {
		return repository.findOne(id);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public String findClobAsString(int id) throws SQLException, IOException {
		return repository.findClobAsString(id);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public String findBlobAsString(int id) throws SQLException, IOException {
		String s = repository.findBlobAsString(id);
		return s;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<BasicTypeEntity> luceneQuery(org.apache.lucene.search.Query query) {
		return repository.luceneQuery(query);
	}
}
