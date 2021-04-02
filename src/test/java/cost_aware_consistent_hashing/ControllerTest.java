package cost_aware_consistent_hashing;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ControllerTest {
    private Controller controller = new Controller();
    
    @Test
    public void uniformTest() throws InterruptedException{
        ExperimentResults experimentResults = controller.runExperiment(DataSetType.UNIFORM, AlgorithmType.MODULO);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void normalTest() throws InterruptedException{
        ExperimentResults experimentResults = controller.runExperiment(DataSetType.NORMAL, AlgorithmType.MODULO);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void normalConsitentTest() throws InterruptedException{
        ExperimentResults experimentResults = controller.runExperiment(DataSetType.NORMAL, AlgorithmType.CONSISTENT);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void normalBoundedLoadTest() throws InterruptedException{
        ExperimentResults experimentResults = controller.runExperiment(DataSetType.NORMAL, AlgorithmType.BOUNDED_LOAD);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void normalRehashTest() throws InterruptedException{
        ExperimentResults experimentResults = controller.runExperiment(DataSetType.NORMAL, AlgorithmType.REHASH);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void zipfConsistentTest() throws InterruptedException{
        ExperimentResults experimentResults = controller.runExperiment(DataSetType.ZIPF, AlgorithmType.CONSISTENT);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void zipfBoundedTest() throws InterruptedException{
        ExperimentResults experimentResults = controller.runExperiment(DataSetType.ZIPF, AlgorithmType.BOUNDED_LOAD);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void zipfRehashTest() throws InterruptedException{
        ExperimentResults experimentResults = controller.runExperiment(DataSetType.ZIPF, AlgorithmType.REHASH);
        assertTrue(experimentResults.getTotalTime() > 0);
    }
}
