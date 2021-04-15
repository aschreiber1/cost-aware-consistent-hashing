package cost_aware_consistent_hashing;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ControllerTest {
    private Controller controller = new Controller();
    private DataGenerator generator = new DataGenerator();
    
    @Test
    public void uniformTest() throws InterruptedException{
        DataSet dataSet = generator.getDataset(DataSetType.UNIFORM);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.MODULO);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void normalTest() throws InterruptedException{
        DataSet dataSet = generator.getDataset(DataSetType.NORMAL);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.MODULO);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void normalConsitentTest() throws InterruptedException{
        DataSet dataSet = generator.getDataset(DataSetType.NORMAL);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.CONSISTENT);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void normalBoundedLoadTest() throws InterruptedException{
        DataSet dataSet = generator.getDataset(DataSetType.NORMAL);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.BOUNDED_LOAD);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void normalRehashTest() throws InterruptedException{
        DataSet dataSet = generator.getDataset(DataSetType.NORMAL);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.REHASH);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void zipfConsistentTest() throws InterruptedException{
        DataSet dataSet = generator.getDataset(DataSetType.ZIPF);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.CONSISTENT);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void zipfBoundedTest() throws InterruptedException{
        DataSet dataSet = generator.getDataset(DataSetType.ZIPF);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.BOUNDED_LOAD);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void zipfRehashTest() throws InterruptedException{
        DataSet dataSet = generator.getDataset(DataSetType.ZIPF);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.REHASH);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void constantConsistentTest() throws InterruptedException{
        DataSet dataSet = generator.getDataset(DataSetType.CONSTANT);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.CONSISTENT);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void constantBoundedLoadTest() throws InterruptedException{
        DataSet dataSet = generator.getDataset(DataSetType.CONSTANT);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.BOUNDED_LOAD);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void constantModuloTest() throws InterruptedException{
        DataSet dataSet = generator.getDataset(DataSetType.CONSTANT);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.MODULO);
        assertTrue(experimentResults.getTotalTime() > 0);
    }
}
