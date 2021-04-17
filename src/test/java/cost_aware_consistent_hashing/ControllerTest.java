package cost_aware_consistent_hashing;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

public class ControllerTest {
    private Controller controller = new Controller();
    private DataGenerator generator = new DataGenerator();
    private final Double cacheEffectivness = .9;
    
    @Test
    public void uniformTest() throws InterruptedException, IOException{
        DataSet dataSet = generator.getDataset(DataSetType.UNIFORM);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.MODULO, cacheEffectivness);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void normalTest() throws InterruptedException, IOException{
        DataSet dataSet = generator.getDataset(DataSetType.NORMAL);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.MODULO, cacheEffectivness);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void normalConsitentTest() throws InterruptedException, IOException{
        DataSet dataSet = generator.getDataset(DataSetType.NORMAL);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.CONSISTENT, cacheEffectivness);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void normalBoundedLoadTest() throws InterruptedException, IOException{
        DataSet dataSet = generator.getDataset(DataSetType.NORMAL);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.BOUNDED_LOAD, cacheEffectivness);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void normalRehashTest() throws InterruptedException, IOException{
        DataSet dataSet = generator.getDataset(DataSetType.NORMAL);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.REHASH, cacheEffectivness);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    // @Test
    // public void normalBoundedElapsed() throws InterruptedException{
    //     DataSet dataSet = generator.getDataset(DataSetType.NORMAL);
    //     ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.BOUNDED_ELAPSED, cacheEffectivness);
    //     assertTrue(experimentResults.getTotalTime() > 0);
    // }

    @Test
    public void zipfConsistentTest() throws InterruptedException, IOException{
        DataSet dataSet = generator.getDataset(DataSetType.ZIPF);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.CONSISTENT, cacheEffectivness);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void zipfBoundedTest() throws InterruptedException, IOException{
        DataSet dataSet = generator.getDataset(DataSetType.ZIPF);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.BOUNDED_LOAD, cacheEffectivness);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void zipfRehashTest() throws InterruptedException, IOException{
        DataSet dataSet = generator.getDataset(DataSetType.ZIPF);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.REHASH, cacheEffectivness);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void constantConsistentTest() throws InterruptedException, IOException{
        DataSet dataSet = generator.getDataset(DataSetType.CONSTANT);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.CONSISTENT, cacheEffectivness);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void constantBoundedLoadTest() throws InterruptedException, IOException{
        DataSet dataSet = generator.getDataset(DataSetType.CONSTANT);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.BOUNDED_LOAD, cacheEffectivness);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void constantModuloTest() throws InterruptedException, IOException{
        DataSet dataSet = generator.getDataset(DataSetType.CONSTANT);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.MODULO, cacheEffectivness);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void cryptoModuloTest() throws InterruptedException, IOException{
        DataSet dataSet = generator.getDataset(DataSetType.CRYPTO);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.MODULO, cacheEffectivness);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void cryptoBoundedLoadTest() throws InterruptedException, IOException{
        DataSet dataSet = generator.getDataset(DataSetType.CRYPTO);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.BOUNDED_LOAD, cacheEffectivness);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void facebookModuloTest() throws InterruptedException, IOException{
        DataSet dataSet = generator.getDataset(DataSetType.FACEBOOK);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.MODULO, cacheEffectivness);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void facebookConsistentTest() throws InterruptedException, IOException{
        DataSet dataSet = generator.getDataset(DataSetType.FACEBOOK);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.CONSISTENT, cacheEffectivness);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void facebookBoundedLoadTest() throws InterruptedException, IOException{
        DataSet dataSet = generator.getDataset(DataSetType.FACEBOOK);
        ExperimentResults experimentResults = controller.runExperiment(dataSet, AlgorithmType.BOUNDED_LOAD, cacheEffectivness);
        assertTrue(experimentResults.getTotalTime() > 0);
    }
}
