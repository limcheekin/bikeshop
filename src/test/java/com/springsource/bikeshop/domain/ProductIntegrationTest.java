package com.springsource.bikeshop.domain;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@Transactional
@RooIntegrationTest(entity = Product.class)
public class ProductIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private ProductDataOnDemand dod;

	@Autowired
    ProductRepository productRepository;

	@Test
    public void testCount() {
        Assert.assertNotNull("Data on demand for 'Product' failed to initialize correctly", dod.getRandomProduct());
        long count = productRepository.count();
        Assert.assertTrue("Counter for 'Product' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFind() {
        Product obj = dod.getRandomProduct();
        Assert.assertNotNull("Data on demand for 'Product' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Product' failed to provide an identifier", id);
        obj = productRepository.findOne(id);
        Assert.assertNotNull("Find method for 'Product' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Product' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAll() {
        Assert.assertNotNull("Data on demand for 'Product' failed to initialize correctly", dod.getRandomProduct());
        long count = productRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'Product', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Product> result = productRepository.findAll();
        Assert.assertNotNull("Find all method for 'Product' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Product' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindEntries() {
        Assert.assertNotNull("Data on demand for 'Product' failed to initialize correctly", dod.getRandomProduct());
        long count = productRepository.count();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Product> result = productRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
        Assert.assertNotNull("Find entries method for 'Product' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Product' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Product obj = dod.getRandomProduct();
        Assert.assertNotNull("Data on demand for 'Product' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Product' failed to provide an identifier", id);
        obj = productRepository.findOne(id);
        Assert.assertNotNull("Find method for 'Product' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyProduct(obj);
        Integer currentVersion = obj.getVersion();
        productRepository.flush();
        Assert.assertTrue("Version for 'Product' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveUpdate() {
        Product obj = dod.getRandomProduct();
        Assert.assertNotNull("Data on demand for 'Product' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Product' failed to provide an identifier", id);
        obj = productRepository.findOne(id);
        boolean modified =  dod.modifyProduct(obj);
        Integer currentVersion = obj.getVersion();
        Product merged = productRepository.save(obj);
        productRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Product' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSave() {
        Assert.assertNotNull("Data on demand for 'Product' failed to initialize correctly", dod.getRandomProduct());
        Product obj = dod.getNewTransientProduct(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Product' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Product' identifier to be null", obj.getId());
        productRepository.save(obj);
        productRepository.flush();
        Assert.assertNotNull("Expected 'Product' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDelete() {
        Product obj = dod.getRandomProduct();
        Assert.assertNotNull("Data on demand for 'Product' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Product' failed to provide an identifier", id);
        obj = productRepository.findOne(id);
        productRepository.delete(obj);
        productRepository.flush();
        Assert.assertNull("Failed to remove 'Product' with identifier '" + id + "'", productRepository.findOne(id));
    }
}
