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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@Transactional
@Configurable
@RooIntegrationTest(entity = Supplier.class)
public class SupplierIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private SupplierDataOnDemand dod;

	@Autowired
    SupplierRepository supplierRepository;

	@Test
    public void testCount() {
        Assert.assertNotNull("Data on demand for 'Supplier' failed to initialize correctly", dod.getRandomSupplier());
        long count = supplierRepository.count();
        Assert.assertTrue("Counter for 'Supplier' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFind() {
        Supplier obj = dod.getRandomSupplier();
        Assert.assertNotNull("Data on demand for 'Supplier' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Supplier' failed to provide an identifier", id);
        obj = supplierRepository.findOne(id);
        Assert.assertNotNull("Find method for 'Supplier' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Supplier' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAll() {
        Assert.assertNotNull("Data on demand for 'Supplier' failed to initialize correctly", dod.getRandomSupplier());
        long count = supplierRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'Supplier', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Supplier> result = supplierRepository.findAll();
        Assert.assertNotNull("Find all method for 'Supplier' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Supplier' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindEntries() {
        Assert.assertNotNull("Data on demand for 'Supplier' failed to initialize correctly", dod.getRandomSupplier());
        long count = supplierRepository.count();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Supplier> result = supplierRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
        Assert.assertNotNull("Find entries method for 'Supplier' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Supplier' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Supplier obj = dod.getRandomSupplier();
        Assert.assertNotNull("Data on demand for 'Supplier' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Supplier' failed to provide an identifier", id);
        obj = supplierRepository.findOne(id);
        Assert.assertNotNull("Find method for 'Supplier' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifySupplier(obj);
        Integer currentVersion = obj.getVersion();
        supplierRepository.flush();
        Assert.assertTrue("Version for 'Supplier' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveUpdate() {
        Supplier obj = dod.getRandomSupplier();
        Assert.assertNotNull("Data on demand for 'Supplier' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Supplier' failed to provide an identifier", id);
        obj = supplierRepository.findOne(id);
        boolean modified =  dod.modifySupplier(obj);
        Integer currentVersion = obj.getVersion();
        Supplier merged = supplierRepository.save(obj);
        supplierRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Supplier' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSave() {
        Assert.assertNotNull("Data on demand for 'Supplier' failed to initialize correctly", dod.getRandomSupplier());
        Supplier obj = dod.getNewTransientSupplier(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Supplier' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Supplier' identifier to be null", obj.getId());
        supplierRepository.save(obj);
        supplierRepository.flush();
        Assert.assertNotNull("Expected 'Supplier' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDelete() {
        Supplier obj = dod.getRandomSupplier();
        Assert.assertNotNull("Data on demand for 'Supplier' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Supplier' failed to provide an identifier", id);
        obj = supplierRepository.findOne(id);
        supplierRepository.delete(obj);
        supplierRepository.flush();
        Assert.assertNull("Failed to remove 'Supplier' with identifier '" + id + "'", supplierRepository.findOne(id));
    }
}
