package idv.hsiehpinghan.hibernatesearchormassistant.repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;

import org.apache.lucene.search.Sort;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import idv.hsiehpinghan.hibernatesearchormassistant.entity.BasicTypeEntity;
import idv.hsiehpinghan.hibernatesearchormassistant.utility.InputStreamUtility;
import idv.hsiehpinghan.hibernatesearchormassistant.utility.ReaderUtility;
import idv.hsiehpinghan.hibernatesearchormassistant.vo.ProjectionVo;

@Repository
public class BasicTypeRepository {
	private final int BATCH_SIZE = 10;
	@Autowired
	private SessionFactory sessionFactory;

	public void save(BasicTypeEntity entity) {
		Session session = sessionFactory.getCurrentSession();
		session.save(entity);
	}

	public void remove(BasicTypeEntity entity) {
		Session session = sessionFactory.getCurrentSession();
		session.remove(entity);
	}

	public void saveOrUpdate(BasicTypeEntity entity) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(entity);
	}

	public BasicTypeEntity findOne(int id) {
		Session session = sessionFactory.getCurrentSession();
		return (BasicTypeEntity) session.get(BasicTypeEntity.class, id);
	}

	public String findClobAsString(int id) throws SQLException, IOException {
		Session session = sessionFactory.getCurrentSession();
		Query<Clob> query = session.createQuery("select clob from BasicTypeEntity bte where bte.id = :id ", Clob.class);
		query.setParameter("id", id);
		Clob clob = query.uniqueResult();
		return convertToString(clob);
	}

	public String findBlobAsString(int id) throws SQLException, IOException {
		Session session = sessionFactory.getCurrentSession();
		Query<Blob> query = session.createQuery("select blob from BasicTypeEntity bte where bte.id = :id ", Blob.class);
		query.setParameter("id", id);
		Blob blob = (Blob) query.uniqueResult();
		return convertToString(blob);
	}

	public void reindex(BasicTypeEntity entity) {
		Session session = sessionFactory.getCurrentSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		fullTextSession.index(entity);
	}

	public void reindexAll() throws InterruptedException {
		Session session = sessionFactory.getCurrentSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		fullTextSession.createIndexer(BasicTypeEntity.class).startAndWait();
	}

	public void manualReindexAll() throws InterruptedException {
		Session session = sessionFactory.getCurrentSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		fullTextSession.setHibernateFlushMode(FlushMode.MANUAL);
		fullTextSession.setCacheMode(CacheMode.IGNORE);
		Query<BasicTypeEntity> query = session.createQuery("from BasicTypeEntity ", BasicTypeEntity.class);
		try (ScrollableResults scrollableResults = query.scroll(ScrollMode.FORWARD_ONLY)) {
			int i = 0;
			while (scrollableResults.next()) {
				++i;
				fullTextSession.index(scrollableResults.get(0));
				if (i % BATCH_SIZE == 0) {
					fullTextSession.flushToIndexes();
					fullTextSession.clear();
				}
			}
		}
	}

	public void unindex(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		fullTextSession.purge(BasicTypeEntity.class, id);
	}

	public void unindexAll() throws InterruptedException {
		Session session = sessionFactory.getCurrentSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		fullTextSession.purgeAll(BasicTypeEntity.class);
	}

	@SuppressWarnings("unchecked")
	public List<BasicTypeEntity> luceneQuery(org.apache.lucene.search.Query query) {
		Session session = sessionFactory.getCurrentSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(query, BasicTypeEntity.class);
		return fullTextQuery.list();
	}

	@SuppressWarnings("unchecked")
	public List<ProjectionVo> projection(org.apache.lucene.search.Query query, String... projections) {
		Session session = sessionFactory.getCurrentSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(query, BasicTypeEntity.class);
		ResultTransformer resultTransformer = Transformers.aliasToBean(ProjectionVo.class);
		return fullTextQuery.setProjection(projections).setResultTransformer(resultTransformer).list();
	}

	@SuppressWarnings("unchecked")
	public List<BasicTypeEntity> sort(org.apache.lucene.search.Query query, Sort sort) {
		Session session = sessionFactory.getCurrentSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(query, BasicTypeEntity.class);
		fullTextQuery.setSort(sort);
		return fullTextQuery.list();
	}

	private String convertToString(java.sql.Clob clob) throws SQLException, IOException {
		Reader reader = clob.getCharacterStream();
		return ReaderUtility.readAsString(reader);
	}

	private String convertToString(java.sql.Blob blob) throws SQLException, IOException {
		InputStream inputStream = blob.getBinaryStream();
		return InputStreamUtility.readAsString(inputStream);
	}
}
