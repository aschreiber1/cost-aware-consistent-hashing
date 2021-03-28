package cost_aware_consistent_hashing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import static cost_aware_consistent_hashing.Constants.*;

public class DataGeneratorTest {

    private DataGenerator dataGenerator = new DataGenerator();

    @Test
    public void testUniform(){
        DataSet dataSet = dataGenerator.getDataset(DataSetType.UNIFORM);
        assertEquals(NUM_TASKS, dataSet.getTasks().size());
    }

    @Test
    public void testNormal(){
        DataSet dataSet = dataGenerator.getDataset(DataSetType.NORMAL);
        assertEquals(NUM_TASKS, dataSet.getTasks().size()); 
    }

    @Test
    public void testCauchy(){
        DataSet dataSet = dataGenerator.getDataset(DataSetType.CAUCHY);
        assertEquals(NUM_TASKS, dataSet.getTasks().size());
    }
    
}
